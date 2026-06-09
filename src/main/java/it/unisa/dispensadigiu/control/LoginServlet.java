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

import it.unisa.dispensadigiu.model.UtenteBean;
import it.unisa.dispensadigiu.model.UtenteDAO;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        UtenteDAO utenteDAO = new UtenteDAO();
        
        try {
            UtenteBean utente = utenteDAO.controlloLogin(email, password);

            if (utente != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute("utente", utente);
                
                
                if (utente.getRuolo() != null && utente.getRuolo().equalsIgnoreCase("admin")) {
                    response.sendRedirect(request.getContextPath() + "/AdminProdotto");
                } else {
                    session.setAttribute("toastMsg", "Bentornato, " + utente.getNome() + "! Accesso effettuato.");
                    response.sendRedirect("home.jsp");
                }
            } else {
                request.setAttribute("errorMsg", "Email o password errati.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }
}
