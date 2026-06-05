package it.unisa.dispensadigiu.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.unisa.dispensadigiu.model.UtenteDAO;

@WebServlet("/VerificaEmailServlet")
public class VerificaEmailServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
		String email = request.getParameter("email");
		
        
		boolean esiste = false;

        // 2. Interazione con il Model (DAO) per il controllo sul Database
        if (email != null && !email.trim().isEmpty()) {
            UtenteDAO utenteDAO = new UtenteDAO();
            try {
                // Invochiamo il metodo del DAO che esegue la query SQL
                esiste = utenteDAO.emailEsistente(email);
            } catch (SQLException e) {
                // Il server non deve perdere il controllo. Gestiamo l'eccezione loggandola
                // e restituendo un codice di errore HTTP 500 che la Fetch API può intercettare.
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
        }
        
        // Imposto la risposta in formato JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        out.print("{\"esiste\": " + esiste + "}");
        out.flush();
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Reindirizziamo le chiamate POST al metodo GET per riutilizzare la logica
        doGet(request, response);
    }
}