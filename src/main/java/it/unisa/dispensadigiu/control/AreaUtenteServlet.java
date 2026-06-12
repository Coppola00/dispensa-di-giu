package it.unisa.dispensadigiu.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unisa.dispensadigiu.model.OrdineBean;
import it.unisa.dispensadigiu.model.OrdineDAO;
import it.unisa.dispensadigiu.model.UtenteBean;

@WebServlet("/AreaUtente")
public class AreaUtenteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrdineDAO ordineDAO = new OrdineDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Controllo se l'utente è loggato
        UtenteBean utente = (UtenteBean) session.getAttribute("utente");
        if (utente == null) {
            session.setAttribute("toastMsg", "Accedi per visualizzare la tua area riservata.");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            List<OrdineBean> storicoOrdini = ordineDAO.storicoOrdiniUtente(utente.getIdutente());
            
            request.setAttribute("storicoOrdini", storicoOrdini);
            request.getRequestDispatcher("/area-utente.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Impossibile caricare lo storico ordini.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}