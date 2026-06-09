<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.dispensadigiu.model.ProdottoBean" %>

<%@ include file="fragments/header.jsp" %>
<%@ include file="fragments/messaggi_feedback.jsp" %>

<main class="container-semplice">
    <%
        ProdottoBean prodotto = (ProdottoBean) request.getAttribute("prodottoDettaglio");

        if (prodotto == null) {
    %>
        <div class="messaggio-errore">
            <h2>Prodotto non trovato</h2>
            <a href="<%= request.getContextPath() %>/Catalogo" class="btn-primary">Torna al Catalogo</a>
        </div>
    <%
        } else {
    %>
        <div class="prodotto-dettaglio-singolo">
            
            <img src="<%= request.getContextPath() %>/<%= prodotto.getImmagineUrl() %>" alt="<%= prodotto.getNome() %>" class="img-dettaglio">
            
            <div class="info-dettaglio">
                <h2><%= prodotto.getNome() %></h2>
                <p class="categoria-testo">Categoria: <%= prodotto.getCategoria() %></p>
                
                <p class="descrizione-testo"><%= prodotto.getDescrizione() %></p>
                
                <h3 class="prezzo-testo">
                    Prezzo: <%= String.format("%.2f", prodotto.getPrezzoUnitario()) %> &euro;
                </h3>

                <form action="<%= request.getContextPath() %>/CarrelloServlet" method="POST" class="form-semplice">
                    <input type="hidden" name="action" value="add">
                    <input type="hidden" name="idProdotto" value="<%= prodotto.getIdProdotto() %>">
                    
                    <label for="quantita">Quantità:</label>
                    <input type="number" id="quantita" name="quantita" value="1" min="1" max="99">
                    
                    <button type="submit" class="btn-primary">Aggiungi al carrello</button>
                </form>
            </div>
            
        </div>
    <%
        }
    %>
</main>

<%@ include file="fragments/footer.jsp" %>
                    
                    