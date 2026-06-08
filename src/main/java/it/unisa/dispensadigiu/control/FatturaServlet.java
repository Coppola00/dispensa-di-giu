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

@WebServlet("/Fattura")
public class FatturaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrdineDAO ordineDAO = new OrdineDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Verifica Sicurezza: Solo gli utenti loggati possono vedere le fatture
        UtenteBean utente = (UtenteBean) session.getAttribute("utente");
        if (utente == null) {
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
            
            // 1. Recuperiamo l'ordine generale
            OrdineBean ordine = ordineDAO.doRetrieveById(idOrdine);
            
            if (ordine != null) {
                // Controllo Sicurezza: l'utente sta guardando la SUA fattura?
                if (ordine.getIdUtente() != utente.getIdutente()) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accesso negato a questa fattura.");
                    return;
                }
                
                // 2. Recuperiamo i dettagli "congelati" (prodotti e prezzi storici)
                List<ElementoCarrelloBean> dettagli = ordineDAO.doRetrieveDettagliByOrdine(idOrdine);
                
                // Passiamo i dati alla JSP
                request.setAttribute("ordineFattura", ordine);
                request.setAttribute("dettagliFattura", dettagli);
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