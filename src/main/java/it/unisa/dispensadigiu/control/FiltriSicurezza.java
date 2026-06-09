package it.unisa.dispensadigiu.control;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unisa.dispensadigiu.model.UtenteBean;

/**
 * Filtro centralizzato per il controllo degli accessi.
 * Protegge sia le Servlet che i file JSP sensibili.
 */
@WebFilter(urlPatterns = {
    "/AreaUtente", 
    "/Checkout", 
    "/Fattura", 
    "/area-utente.jsp", 
    "/fattura.jsp"
})
public class FiltriSicurezza implements Filter {

    public void init(FilterConfig fConfig) throws ServletException {
        
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0
        httpResponse.setDateHeader("Expires", 0); // Proxies
        
        // Recuperiamo la sessione (senza creane una nuova se non esiste)
        HttpSession session = httpRequest.getSession(false) ;
        
        UtenteBean utente = null;
        if (session != null) {
            utente = (UtenteBean) session.getAttribute("utente");
        }

        // VERIFICA: Se l'utente non è loggato, blocchiamo la richiesta
        if (utente == null) {
            // Salviamo il messaggio di errore nella sessione in modo che sopravviva al redirect
            httpRequest.getSession().setAttribute("toastMsg", "Accesso negato. Effettua il login per visualizzare questa pagina.");
            // Reindirizziamo l'utente alla login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
            return; 
        }
        else if (utente.getRuolo() != null && utente.getRuolo().equalsIgnoreCase("admin")) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/AdminProdotto");
            return;
        } else {    
        // Se l'utente è loggato, la richiesta può proseguire verso la destinazione originale
        chain.doFilter(request, response);
    }
    }
    public void destroy() {
      
    }
}