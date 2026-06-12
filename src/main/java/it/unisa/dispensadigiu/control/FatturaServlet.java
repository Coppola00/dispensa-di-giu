package it.unisa.dispensadigiu.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unisa.dispensadigiu.model.OrdineBean;
import it.unisa.dispensadigiu.model.OrdineDAO;
import it.unisa.dispensadigiu.model.ElementoCarrelloBean;
import it.unisa.dispensadigiu.model.UtenteBean;
import it.unisa.dispensadigiu.model.UtenteDAO;

@WebServlet("/Fattura")
public class FatturaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrdineDAO ordineDAO = new OrdineDAO();
    private UtenteDAO utenteDAO = new UtenteDAO(); 

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        
        UtenteBean utenteLoggato = (UtenteBean) session.getAttribute("utente");
        if (utenteLoggato == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        try {
            int idOrdine = Integer.parseInt(idParam);
            
            // Recuperiamo l'ordine generale
            OrdineBean ordine = ordineDAO.doRetrieveById(idOrdine);
            
            if (ordine != null) {
                
                // Usiamo l'ID utente salvato dentro l'ordine per estrarre il cliente reale dal DB
                UtenteBean acquirenteReale = utenteDAO.doRetrieveById(ordine.getIdUtente());
                
                // Controllo di sicurezza: Se non sei admin e l'ordine NON è il tuo, ti blocco.
                boolean isAdmin = utenteLoggato.getRuolo() != null && utenteLoggato.getRuolo().equalsIgnoreCase("admin");
                if (!isAdmin && utenteLoggato.getIdutente() != acquirenteReale.getIdutente()) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accesso negato: non sei autorizzato a visualizzare questa fattura.");
                    return;
                }
            
                                
             
                List<ElementoCarrelloBean> dettagli = ordineDAO.doRetrieveDettagliByOrdine(idOrdine);
                
                // Passiamo i dati alla JSP
                request.setAttribute("ordineFattura", ordine);
                request.setAttribute("dettagliFattura", dettagli);
                request.setAttribute("utenteFattura", acquirenteReale); 
                request.getRequestDispatcher("/fattura.jsp").forward(request, response);
                
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ordine non trovato");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel caricamento della fattura");
        }
    }
}