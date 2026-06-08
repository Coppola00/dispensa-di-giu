package it.unisa.dispensadigiu.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recuperiamo la sessione attuale, se esiste (false evita di crearne una nuova)
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // Elimina la sessione dal server e tutti i dati collegati (utente, carrello, ecc.)
            session.invalidate();
        }
        
        // Opzionale: Creiamo una nuova sessione pulita solo per passare il messaggio Toast di saluto
        HttpSession nuovaSessione = request.getSession(true);
        nuovaSessione.setAttribute("toastMsg", "Disconnessione effettuata. Torna presto a trovarci!");

        // Reindirizziamo l'utente alla pagina principale del sito
        response.sendRedirect(request.getContextPath() + "/home.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}