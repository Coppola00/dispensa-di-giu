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
        
        // 1. VERIFICA DI SICUREZZA: L'utente è loggato?
        UtenteBean utente = (UtenteBean) session.getAttribute("utente");
        if (utente == null) {
            // Se non è loggato, impostiamo il messaggio di errore e reindirizziamo al login
            session.setAttribute("toastMsg", "Devi effettuare l'accesso per completare l'ordine.");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 2. VERIFICA STATO CARRELLO: Esiste ed ha elementi?
        Carrello carrello = (Carrello) session.getAttribute("carrello");
        if (carrello == null || carrello.getElementi().isEmpty()) {
            session.setAttribute("toastMsg", "Il tuo carrello è vuoto. Impossibile procedere al checkout.");
            response.sendRedirect(request.getContextPath() + "/Catalogo");
            return;
        }

        try {
            // 3. PREPARAZIONE DEL BEAN ORDINE
            OrdineBean ordine = new OrdineBean();
            ordine.setIdUtente(utente.getIdutente());
            ordine.setDataOrdine(new Timestamp(System.currentTimeMillis()));
            ordine.setTotaleOrdine(carrello.getTotaleComplessivo());
            ordine.setCostoSpedizione(0.0); // Impostata come spedizione gratuita nel riepilogo
            ordine.setStatoOrdine("In lavorazione");
            

            // 4. SALVATAGGIO TRANSAZIONALE (Ordine + Dettagli) nel Database
            int idOrdineGenerato = ordineDAO.doSave(ordine, carrello);

            if (idOrdineGenerato > 0) {
                // 5. PULIZIA DEL CARRELLO E MESSAGGIO DI CONFERMA
                carrello.svuotaCarrello();
                session.setAttribute("toastMsg", "Ordine #" + idOrdineGenerato + " ricevuto! Grazie per il tuo acquisto.");
                
                // Reindirizziamo l'utente alla visualizzazione della fattura per l'ordine appena creato
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