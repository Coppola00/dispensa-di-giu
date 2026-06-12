package it.unisa.dispensadigiu.control;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unisa.dispensadigiu.model.Carrello;
import it.unisa.dispensadigiu.model.OrdineBean;
import it.unisa.dispensadigiu.model.OrdineDAO;
import it.unisa.dispensadigiu.model.UtenteBean;

@WebServlet("/Checkout")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrdineDAO ordineDAO = new OrdineDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        UtenteBean utente = (UtenteBean) session.getAttribute("utente");
        if (utente == null) {
            // Se l'utente non è loggato non può fare il logout
        	session.setAttribute("toastMsg", "Devi effettuare l'accesso per completare l'ordine.");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Carrello carrello = (Carrello) session.getAttribute("carrello");
        if (carrello == null || carrello.getElementi().isEmpty()) {
            session.setAttribute("toastMsg", "Il tuo carrello è vuoto. Impossibile procedere al checkout.");
            response.sendRedirect(request.getContextPath() + "/Catalogo");
            return;
        }

        try {
            OrdineBean ordine = new OrdineBean();
            ordine.setIdUtente(utente.getIdutente());
            ordine.setDataOrdine(new Timestamp(System.currentTimeMillis()));
            ordine.setTotaleOrdine(carrello.getTotaleComplessivo());
            ordine.setCostoSpedizione(0.0); // Impostata come spedizione gratuita
            ordine.setStatoOrdine("In lavorazione");
            
            int idOrdineGenerato = ordineDAO.doSave(ordine, carrello);

            if (idOrdineGenerato > 0) {
                carrello.svuotaCarrello();
                session.setAttribute("toastMsg", "Ordine #" + idOrdineGenerato + " ricevuto! Grazie per il tuo acquisto.");
                response.sendRedirect(request.getContextPath() + "/Fattura?id=" + idOrdineGenerato);
            } else {
                throw new SQLException("Errore nel recupero dell'ID ordine generato.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Si è verificato un errore interno durante l'elaborazione del checkout.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}