package it.unisa.dispensadigiu.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// Importa i tuoi Bean e DAO 
import it.unisa.dispensadigiu.model.UtenteBean;
import it.unisa.dispensadigiu.model.UtenteDAO;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Recupero parametri dal form di login
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        UtenteDAO utenteDAO = new UtenteDAO();
        
        try {
            // Utilizzo del metodo sicuro per il controllo login [
            UtenteBean utente = utenteDAO.controlloLogin(email, password);
            
            if (utente != null) {
                // Autenticazione riuscita: gestione sessione 
                HttpSession session = request.getSession(true);
                session.setAttribute("utente", utente);
                
                // Reindirizzamento in base al ruolo
                if ("ADMIN".equals(utente.getRuolo())) {
                    response.sendRedirect("DashboardAdminServlet");
                } else {
                	session.setAttribute("toastMsg", "Bentornato, " + utente.getNome() + "! Accesso effettuato con successo.");
                    response.sendRedirect("home.jsp");
                }
            } else {
                // Autenticazione fallita: messaggio di errore inline 
                request.setAttribute("errorMsg", "Email o password errati.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
            }
            
        } catch (SQLException e) {
            // Gestione errore server: configurazione in web.xml 
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }
}