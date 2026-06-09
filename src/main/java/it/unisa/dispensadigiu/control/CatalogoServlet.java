package it.unisa.dispensadigiu.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unisa.dispensadigiu.model.ProdottoBean;
import it.unisa.dispensadigiu.model.ProdottoDAO;

@WebServlet("/Catalogo")
public class CatalogoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProdottoDAO prodottoDAO = new ProdottoDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("dettaglio".equals(action)) {
                int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
                
                ProdottoBean prodotto = prodottoDAO.trovaById(idProdotto); 
                
                request.setAttribute("prodottoDettaglio", prodotto);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/dettaglio-prodotto.jsp");
                dispatcher.forward(request, response);
                
                return; 
            }

            
            String categoria = request.getParameter("categoria");
            List<ProdottoBean> prodotti;

            // Se l'utente ha cliccato su una categoria specifica, filtriamo
            if (categoria != null && !categoria.trim().isEmpty()) {
                prodotti = prodottoDAO.trovaByCategory(categoria);
            } else {
                // Altrimenti mostriamo tutto il catalogo
                prodotti = prodottoDAO.trovaTutti();
            }

            
            request.setAttribute("listaProdotti", prodotti);

            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/catalogo.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            // In caso di errore DB, rimandiamo alla pagina di errore 500
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore di comunicazione con il database");
        } 
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
