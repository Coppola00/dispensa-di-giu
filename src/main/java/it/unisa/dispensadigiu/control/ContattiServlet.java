package it.unisa.dispensadigiu.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ContattiServlet")
public class ContattiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // Recupero i parametri inviati dal form della pagina contatti.jsp
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String messaggio = request.getParameter("messaggio");

        if (nome == null || nome.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            messaggio == null || messaggio.trim().isEmpty()) {
            
            
            request.setAttribute("errorMsg", "Attenzione: tutti i campi sono obbligatori. Compila il form correttamente.");
            
           
            RequestDispatcher dispatcher = request.getRequestDispatcher("/contatti.jsp");
            dispatcher.forward(request, response);
            return; 
        }

        // Se tutto è andato a buon fine, impostiamo il messaggio di successo
        request.setAttribute("successMsg", "Grazie " + nome + "! Il tuo messaggio è stato ricevuto. Ti risponderemo al più presto all'indirizzo " + email + ".");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/contatti.jsp");
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/contatti.jsp");
        dispatcher.forward(request, response);
    }
}