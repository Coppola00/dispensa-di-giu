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
        String categoria = request.getParameter("categoria");
        List<ProdottoBean> prodotti;

        try {
            // Se l'utente ha cliccato su una categoria specifica, filtriamo
            if (categoria != null && !categoria.trim().isEmpty()) {
                prodotti = prodottoDAO.trovaByCategory(categoria);
            } else {
                // Altrimenti mostriamo tutto il catalogo
                prodotti = prodottoDAO.trovaTutti();
            }

            // Inseriamo la lista nella request
            request.setAttribute("listaProdotti", prodotti);

            // Deleghiamo la generazione dell'HTML esclusivamente alla JSP (View)
            RequestDispatcher dispatcher = request.getRequestDispatcher("/catalogo.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            // In caso di errore DB, rimandiamo alla pagina di errore 500 configurata nel web.xml
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel recupero del catalogo");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}