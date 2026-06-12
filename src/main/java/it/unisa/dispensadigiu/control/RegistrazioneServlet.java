package it.unisa.dispensadigiu.control;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unisa.dispensadigiu.model.UtenteBean;
import it.unisa.dispensadigiu.model.UtenteDAO;

@WebServlet("/RegistrazioneServlet")
public class RegistrazioneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome"); 
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // Controllo lato server 
        if (nome == null || email == null || password == null || nome.isEmpty() || email.isEmpty() || password.isEmpty()) {
            request.setAttribute("errorMsg", "Compila tutti i campi obbligatori.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("registrazione.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            
            UtenteBean nuovoUtente = new UtenteBean();
            nuovoUtente.setNome(nome);
            nuovoUtente.setCognome(cognome != null ? cognome : ""); 
            nuovoUtente.setEmail(email);
            nuovoUtente.setPassword(password);
            nuovoUtente.setRuolo("User"); // Ruolo di default

            UtenteDAO utenteDAO = new UtenteDAO();
            utenteDAO.registrazione(nuovoUtente);

            request.setAttribute("successMsg", "Registrazione completata con successo! Ora puoi effettuare il login.");

            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Errore durante la registrazione. Riprova più tardi.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("registrazione.jsp");
            dispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("registrazione.jsp");
    }
}