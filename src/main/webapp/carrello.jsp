<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.dispensadigiu.model.Carrello" %>
<%@ page import="it.unisa.dispensadigiu.model.ElementoCarrelloBean" %>
<%@ page import="java.util.List" %>

<%@ include file="fragments/header.jsp" %>

<main class="container-carrello">
    <h1 class="titolo-pagina"><i class="fa-solid fa-basket-shopping"></i> Il tuo Carrello</h1>

    <%
        Carrello carrello = (Carrello) session.getAttribute("carrello");
        if (carrello == null || carrello.getElementi().isEmpty()) {
    %>
        <div class="carrello-vuoto">
            <i class="fa-solid fa-bag-shopping icona-vuoto"></i>
            <h2>Il tuo carrello è ancora vuoto!</h2>
            <p>Esplora il nostro catalogo e lasciati conquistare dalle eccellenze artigianali del Sud.</p>
            <a href="<%= request.getContextPath() %>/Catalogo" class="btn-primary btn-ritorna">
                Torna al Catalogo
            </a>
        </div>
    <%
        } else {
    %>
        <div class="carrello-singola-colonna">
            
            <div class="carrello-prodotti">
                <table class="tabella-carrello">
                    <thead>
                        <tr>
                            <th>Prodotto</th>
                            <th>Prezzo</th>
                            <th>Quantità</th>
                            <th>Totale</th>
                            <th>Azioni</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<ElementoCarrelloBean> elementi = carrello.getElementi();
                            for (ElementoCarrelloBean item : elementi) {
                        %>
                            <tr>
                                <td data-label="Prodotto" class="td-prodotto">
                                    <img src="<%= request.getContextPath() %>/<%= item.getProdotto().getImmagineUrl() %>" alt="<%= item.getProdotto().getNome() %>" class="carrello-img">
                                    <span class="nome-prodotto"><%= item.getProdotto().getNome() %></span>
                                </td>
                                
                                <td data-label="Prezzo">
                                    <%= String.format("%.2f", item.getPrezzoSingoloIvato()) %> &euro;
                                </td>
                                
                                <td data-label="Quantità">
                                    <form action="<%= request.getContextPath() %>/CarrelloServlet" method="GET" class="form-quantita">
                                        <input type="hidden" name="action" value="update">
                                        <input type="hidden" name="idProdotto" value="<%= item.getProdotto().getIdProdotto() %>">
                                        <input type="number" name="quantita" value="<%= item.getQuantita() %>" min="1" max="99" class="input-quantita" onchange="this.form.submit()">
                                    </form>
                                </td>
                                
                                <td data-label="Totale" class="subtotale-riga">
                                    <%= String.format("%.2f", item.getTotaleParziale()) %> &euro;
                                </td>
                                
                                <td data-label="Azioni">
                                    <form action="<%= request.getContextPath() %>/CarrelloServlet" method="GET">
                                        <input type="hidden" name="action" value="remove">
                                        <input type="hidden" name="idProdotto" value="<%= item.getProdotto().getIdProdotto() %>">
                                        <button type="submit" class="btn-rimuovi" title="Rimuovi dal carrello">
                                            <i class="fa-regular fa-trash-can"></i> Rimuovi
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>

            <div class="carrello-barra-totale">
                <div class="riepilogo-info-basso">
                    <span class="info-item">Articoli: <strong><%= carrello.getNumeroArticoli() %></strong></span>
                    <span class="info-item">Spedizione: <strong class="spedizione-gratis">GRATIS</strong></span>
                    <span class="info-item info-totale">Totale: <strong><%= String.format("%.2f", carrello.getTotaleComplessivo()) %> &euro;</strong></span>
                </div>
                <div class="riepilogo-azioni-basso">
                    <a href="<%= request.getContextPath() %>/Checkout" class="btn-primary btn-checkout">
                        Procedi al Checkout <i class="fa-solid fa-arrow-right"></i>
                    </a>
                </div>
            </div>

        </div>
    <%
        }
    %>
</main>

<%@ include file="fragments/footer.jsp" %>
