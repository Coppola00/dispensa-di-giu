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
    "/AdminProdotto", 
    "/admin.jsp"
})
public class FiltroAdmin implements Filter {

    public void init(FilterConfig fConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpResponse.setHeader("Pragma", "no-cache");
        httpResponse.setDateHeader("Expires", 0); 
        
        HttpSession session = httpRequest.getSession(false);
        UtenteBean utente = null;
        
        if (session != null) {
            utente = (UtenteBean) session.getAttribute("utente");
        }

        if (utente == null || !"Admin".equals(utente.getRuolo())) {
            httpRequest.getSession().setAttribute("toastMsg", "Area riservata al personale della dispensa. Accesso negato.");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
            return; 
        }

        
        chain.doFilter(request, response);
    }

    public void destroy() {}
}