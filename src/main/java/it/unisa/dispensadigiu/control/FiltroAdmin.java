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
 * Filtro per bloccare i clienti normali dalle pagine di gestione commerciale.
 */
@WebFilter(urlPatterns = {
    "/AdminProdotto", 
    "/admin.jsp"
})
public class FiltroAdmin implements Filter {

    public void init(FilterConfig fConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0
        httpResponse.setDateHeader("Expires", 0); // Proxies
        
        HttpSession session = httpRequest.getSession(false);
        UtenteBean utente = null;
        
        if (session != null) {
            utente = (UtenteBean) session.getAttribute("utente");
        }

        // CONTROLLO: Se non è loggato OPPURE è loggato ma non è un admin
        if (utente == null || !"Admin".equals(utente.getRuolo())) {
            // Lo rispediamo alla login dicendo che non ha i permessi
            httpRequest.getSession().setAttribute("toastMsg", "Area riservata al personale della dispensa. Accesso negato.");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
            return; 
        }

        // Se è admin, procedi pure
        chain.doFilter(request, response);
    }

    public void destroy() {}
}