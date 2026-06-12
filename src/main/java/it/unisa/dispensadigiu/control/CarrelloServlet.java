package it.unisa.dispensadigiu.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unisa.dispensadigiu.model.Carrello;
import it.unisa.dispensadigiu.model.ProdottoBean;
import it.unisa.dispensadigiu.model.ProdottoDAO;

@WebServlet("/CarrelloServlet")
public class CarrelloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProdottoDAO prodottoDAO = new ProdottoDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recuperiamo la Sessione e il Carrello
        HttpSession session = request.getSession();
        Carrello carrello = (Carrello) session.getAttribute("carrello");
        
        // Se non esiste ancora un carrello per questo utente, lo creiamo
        if (carrello == null) {
            carrello = new Carrello();
            session.setAttribute("carrello", carrello);
        }

        // Per capire l'azione dell'utente
        String action = request.getParameter("action");
        if (action == null) action = "view"; // Azione di default: mostra il carrello

        try {
            switch (action) {
                case "add":
                    int idAdd = Integer.parseInt(request.getParameter("idProdotto"));
                    ProdottoBean prodotto = prodottoDAO.trovaById(idAdd); 
                    if (prodotto != null) {
                        carrello.aggiungiProdotto(prodotto, 1);
                        session.setAttribute("toastMsg", "Hai aggiunto " + prodotto.getNome() + " al carrello!");
                    }
                    String referer = request.getHeader("referer");
                    response.sendRedirect(referer != null ? referer : request.getContextPath() + "/Catalogo");
                    break;

                case "remove":
                    int idRemove = Integer.parseInt(request.getParameter("idProdotto"));
                    carrello.rimuoviProdotto(idRemove);
                    session.setAttribute("toastMsg", "Prodotto rimosso.");
                    response.sendRedirect(request.getContextPath() + "/CarrelloServlet?action=view");
                    break;

                case "update":
                    int idUpdate = Integer.parseInt(request.getParameter("idProdotto"));
                    int quantita = Integer.parseInt(request.getParameter("quantita"));
                    carrello.aggiornaQuantita(idUpdate, quantita);
                    response.sendRedirect(request.getContextPath() + "/CarrelloServlet?action=view");
                    break;

                case "view":
                default:
                    request.getRequestDispatcher("/carrello.jsp").forward(request, response);
                    break;
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nella gestione del carrello.");
        }
    }
}