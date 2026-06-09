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
        
        // 1. Recupero dei parametri inviati dal form della pagina contatti.jsp
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String messaggio = request.getParameter("messaggio");

        // 2. Validazione dei dati lato server 
        if (nome == null || nome.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            messaggio == null || messaggio.trim().isEmpty()) {
            
            
            request.setAttribute("errorMsg", "Attenzione: tutti i campi sono obbligatori. Compila il form correttamente.");
            
           
            RequestDispatcher dispatcher = request.getRequestDispatcher("/contatti.jsp");
            dispatcher.forward(request, response);
            return; // Blocca l'esecuzione
        }

        // 4. Se tutto è andato a buon fine, impostiamo il messaggio di successo
        request.setAttribute("successMsg", "Grazie " + nome + "! Il tuo messaggio è stato ricevuto. Ti risponderemo al più presto all'indirizzo " + email + ".");

        // 5. Facciamo forward nuovamente alla pagina contatti 
        RequestDispatcher dispatcher = request.getRequestDispatcher("/contatti.jsp");
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Se l'utente digita l'URL della Servlet direttamente nel browser (richiesta GET), 
        // lo reindirizziamo semplicemente alla vista dei contatti in modo pulito.
        RequestDispatcher dispatcher = request.getRequestDispatcher("/contatti.jsp");
        dispatcher.forward(request, response);
    }
}