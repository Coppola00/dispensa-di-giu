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
import it.unisa.dispensadigiu.model.OrdineBean;
import it.unisa.dispensadigiu.model.OrdineDAO;

@WebServlet("/AdminProdotto")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1,  // 1 MB
        maxFileSize = 1024 * 1024 * 5,       // 5 MB
        maxRequestSize = 1024 * 1024 * 10    // 10 MB
)
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProdottoDAO prodottoDAO = new ProdottoDAO();
    private OrdineDAO ordineDAO = new OrdineDAO(); // Istanza del DAO Ordini

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forza anti-cache per evitare problemi con il tasto indietro del browser
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        String action = request.getParameter("action");

        try {
            // OPERAZIONE: ELIMINA PRODOTTO
            if ("elimina".equals(action)) {
                int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
                prodottoDAO.doDelete(idProdotto); 
                request.getSession().setAttribute("toastMsg", "Prodotto rimosso con successo dall'inventario.");
                response.sendRedirect(request.getContextPath() + "/AdminProdotto");
                return;
            }

            // 1. CARICAMENTO PRODOTTI
            List<ProdottoBean> prodotti = prodottoDAO.trovaTutti();
            request.setAttribute("listaProdottiAdmin", prodotti);
            
            // 2. CARICAMENTO E FILTRO ORDINI
            String dataInizio = request.getParameter("dataInizio");
            String dataFine = request.getParameter("dataFine");
            List<OrdineBean> ordini;

            if (dataInizio != null && !dataInizio.trim().isEmpty() && dataFine != null && !dataFine.trim().isEmpty()) {
                // Se sono presenti le date, effettua il filtraggio nel DB
                ordini = ordineDAO.doRetrieveByDate(dataInizio, dataFine);
                request.setAttribute("dataInizioSelezionata", dataInizio);
                request.setAttribute("dataFineSelezionata", dataFine);
            } else {
                // Altrimenti recupera lo storico completo
                ordini = ordineDAO.doRetrieveAll();
            }
            request.setAttribute("listaOrdiniAdmin", ordini);
            
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
                String nome = request.getParameter("nome");
                String categoria = request.getParameter("categoria");
                double prezzo = Double.parseDouble(request.getParameter("prezzo"));
                double iva = Double.parseDouble(request.getParameter("iva"));
                String descrizione = request.getParameter("descrizione");

                Part filePart = request.getPart("immagine");
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                
                String percorsoDestinazione = getServletContext().getRealPath("") + File.separator + "img";
                File cartella = new File(percorsoDestinazione);
                if (!cartella.exists()) {
                    cartella.mkdir();
                }

                File fileSalvato = new File(cartella, fileName);
                try (InputStream fileContent = filePart.getInputStream()) {
                    Files.copy(fileContent, fileSalvato.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                
                String percorsoRelativoDB = "img/" + fileName;
                
                ProdottoBean nuovoProdotto = new ProdottoBean();
                nuovoProdotto.setNome(nome);
                nuovoProdotto.setCategoria(categoria);
                nuovoProdotto.setPrezzoUnitario(prezzo);
                nuovoProdotto.setIva(iva);
                nuovoProdotto.setImmagineUrl(percorsoRelativoDB);
                nuovoProdotto.setDescrizione(descrizione);

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