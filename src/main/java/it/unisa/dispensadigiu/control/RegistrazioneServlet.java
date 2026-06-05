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
import it.unisa.dispensadigiu.model.PasswordUtils; 

@WebServlet("/RegistrazioneServlet")
public class RegistrazioneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 1. Recupero dei parametri dalla form JSP
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome"); 
        String email = request.getParameter("email");
        String passwordPiatta = request.getParameter("password");
        
        // Controllo lato server di base 
        if (nome == null || email == null || passwordPiatta == null || nome.isEmpty() || email.isEmpty() || passwordPiatta.isEmpty()) {
            request.setAttribute("errorMsg", "Compila tutti i campi obbligatori.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("registrazione.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            // 2. Cifratura della password 
            String passwordCifrata = PasswordUtils.hashPassword(passwordPiatta);

            // 3. Creazione del Java Bean (Model)
            UtenteBean nuovoUtente = new UtenteBean();
            nuovoUtente.setNome(nome);
            nuovoUtente.setCognome(cognome != null ? cognome : ""); 
            nuovoUtente.setEmail(email);
            nuovoUtente.setPassword(passwordCifrata);
            nuovoUtente.setRuolo("User"); 

            // 4. Salvataggio nel Database tramite DAO
            UtenteDAO utenteDAO = new UtenteDAO();
            utenteDAO.registrazione(nuovoUtente);

            // 5. Impostazione del messaggio di conferma 
            request.setAttribute("successMsg", "Registrazione completata con successo! Ora puoi effettuare il login.");

            // 6. Forward alla View 
            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            // Gestione generica per catturare sia eventuali eccezioni della PasswordUtility che SQLException del DAO
            e.printStackTrace();
            request.setAttribute("errorMsg", "Errore durante la registrazione. Riprova più tardi.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("registrazione.jsp");
            dispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Se qualcuno prova ad accedere tramite GET, lo rimandiamo al form
        response.sendRedirect("registrazione.jsp");
    }
}