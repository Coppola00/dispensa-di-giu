package it.unisa.dispensadigiu.control;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import it.unisa.dispensadigiu.model.ProdottoBean;
import it.unisa.dispensadigiu.model.ProdottoDAO;

@WebServlet("/AdminProdotto")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 1,  // 1 MB custodia temporanea in memoria
	    maxFileSize = 1024 * 1024 * 5,       // 5 MB dimensione massima per singolo file
	    maxRequestSize = 1024 * 1024 * 10    // 10 MB dimensione massima totale del form
	)
public class AdminProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProdottoDAO prodottoDAO = new ProdottoDAO();

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            // OPERAZIONE: ELIMINA PRODOTTO
            if ("elimina".equals(action)) {
                int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
                
                // Chiamata al metodo doDelete del DAO
                prodottoDAO.doDelete(idProdotto); 
                
                request.getSession().setAttribute("toastMsg", "Prodotto rimosso con successo dall'inventario.");
                response.sendRedirect(request.getContextPath() + "/AdminProdotto");
                return;
            }

           
            List<ProdottoBean> prodotti = prodottoDAO.trovaTutti();
            request.setAttribute("listaProdottiAdmin", prodotti);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nella gestione del database amministrativo.");
        }
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("inserisci".equals(action)) {
            try {
                // Recupero campi dal form
                String nome = request.getParameter("nome");
                String categoria = request.getParameter("categoria");
                double prezzo = Double.parseDouble(request.getParameter("prezzo"));
                double iva = Double.parseDouble(request.getParameter("iva"));
                String descrizione = request.getParameter("descrizione");

                Part filePart = request.getPart("immagine"); // Prende l'input file del form
                
                // Estrae il nome originale del file (es. "provola.jpg")
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                
                // Definiamo dove salvare il file sul server (cartella 'img' della webapp)
                String percorsoDestinazione = getServletContext().getRealPath("") + File.separator + "img";
                File cartella = new File(percorsoDestinazione);
                if (!cartella.exists()) {
                    cartella.mkdir(); // Crea la cartella img se non esiste
                }

                // Scrittura fisica del file sul disco del server
                File fileSalvato = new File(cartella, fileName);
                try (InputStream fileContent = filePart.getInputStream()) {
                    Files.copy(fileContent, fileSalvato.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                
                // Nel database salviamo solo il percorso relativo che servirà ai tag <img>
                String percorsoRelativoDB = "img/" + fileName;
                
                
                // Costruzione del Bean
                ProdottoBean nuovoProdotto = new ProdottoBean();
                nuovoProdotto.setNome(nome);
                nuovoProdotto.setCategoria(categoria);
                nuovoProdotto.setPrezzoUnitario(prezzo);
                nuovoProdotto.setIva(iva);
                nuovoProdotto.setImmagineUrl(percorsoRelativoDB);
                nuovoProdotto.setDescrizione(descrizione);

                // Chiamata al metodo doSave del DAO
                prodottoDAO.doSave(nuovoProdotto); 

                request.getSession().setAttribute("toastMsg", "Nuovo prodotto inserito con successo nella Dispensa!");
                response.sendRedirect(request.getContextPath() + "/AdminProdotto");

            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Impossibile salvare il prodotto.");
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato del prezzo non valido.");
            }
        }
    }
}