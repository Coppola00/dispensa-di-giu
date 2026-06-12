package it.unisa.dispensadigiu.control;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unisa.dispensadigiu.model.ProdottoBean;
import it.unisa.dispensadigiu.model.ProdottoDAO;

@WebServlet("/RicercaAjax")
public class RicercaAjaxServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ProdottoDAO prodottoDAO = new ProdottoDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("q");
        
        // Impostiamo il tipo di risposta come JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (query != null && query.trim().length() >= 2) {
            try {
                List<ProdottoBean> prodotti = prodottoDAO.doRetrieveByNomeAjax(query);
                
                StringBuilder json = new StringBuilder("[");
                for (int i = 0; i < prodotti.size(); i++) {
                    ProdottoBean p = prodotti.get(i);
                    json.append("{")
                        .append("\"id\":").append(p.getIdProdotto()).append(",")
                        .append("\"nome\":\"").append(p.getNome().replace("\"", "\\\"")).append("\"")
                        .append("}");
                    if (i < prodotti.size() - 1) {
                        json.append(",");
                    }
                }
                json.append("]");
                
                response.getWriter().write(json.toString());
                
            } catch (SQLException e) {
            	e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.getWriter().write("[]"); 
        }
    }
}