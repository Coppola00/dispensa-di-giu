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
        
        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpResponse.setHeader("Pragma", "no-cache"); 
        httpResponse.setDateHeader("Expires", 0); 
        
        String requestURI = httpRequest.getRequestURI();

        HttpSession session = httpRequest.getSession(false);
        
        UtenteBean utente = null;
        if (session != null) {
            utente = (UtenteBean) session.getAttribute("utente");
        }

        // Se l'utente non è loggato, blocchiamo la richiesta
        if (utente == null) {
            httpRequest.getSession().setAttribute("toastMsg", "Accesso negato. Effettua il login per visualizzare questa pagina.");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
            return; 
        }
        
        // Logica di smistamento per l'Amministratore
        else if (utente.getRuolo() != null && utente.getRuolo().equalsIgnoreCase("admin")) {
            
            // Se l'admin vuole vedere una fattura, lo facciamo passare
            if (requestURI.contains("/Fattura") || requestURI.contains("/fattura.jsp")) {
                chain.doFilter(request, response);
                return;
            } else {
                // Se cerca di andare nel Checkout o nell'Area Utente normale, lo rimandiamo al suo pannello
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/AdminProdotto");
                return;
            }
        } 
        
        // Se è un utente normale, lo lasciamo passare
        else {    
            chain.doFilter(request, response);
        }
    }
    
    public void destroy() {
        
    }
}