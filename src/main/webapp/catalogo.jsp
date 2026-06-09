<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="it.unisa.dispensadigiu.model.ProdottoBean" %>

<%@ include file="fragments/header.jsp" %>

<main class="container-catalogo">
    <h1 class="titolo-pagina">I Nostri Prodotti</h1>
    
    <div class="filtri-categoria">
        <a href="${pageContext.request.contextPath}/Catalogo" class="btn-filtro">Tutti i prodotti</a>
        <a href="${pageContext.request.contextPath}/Catalogo?categoria=Salumi" class="btn-filtro">Salumi</a>
        <a href="${pageContext.request.contextPath}/Catalogo?categoria=Formaggi" class="btn-filtro">Formaggi</a>
        <a href="${pageContext.request.contextPath}/Catalogo?categoria=Pasta" class="btn-filtro">Pasta</a>
        <a href="${pageContext.request.contextPath}/Catalogo?categoria=Olio e Sottoli" class="btn-filtro">Olio e Sottoli</a>
    </div>
    
    <div class="griglia-prodotti">
        <%
            List<ProdottoBean> prodotti = (List<ProdottoBean>) request.getAttribute("listaProdotti");
            
            if (prodotti != null && !prodotti.isEmpty()) {
                for (ProdottoBean p : prodotti) {
        %>
                    <div class="card-prodotto">
                        <div class="card-immagine">
                            <img src="${pageContext.request.contextPath}/<%= p.getImmagineUrl() %>" alt="<%= p.getNome() %>">
                        </div>
                        
                        <div class="card-info">
                            <span class="prodotto-categoria"><%= p.getCategoria() %></span>
                            <h3 class="prodotto-nome"><%= p.getNome() %></h3>
                            <p class="prodotto-prezzo"><%= String.format("%.2f", p.getPrezzoUnitario()) %> &euro; + IVA</p>
                        </div>
                        
                        <div class="card-azioni">
                            <a href="${pageContext.request.contextPath}/Catalogo?action=dettaglio&idProdotto=<%= p.getIdProdotto() %>" class="btn-secondario">
                                <i class="fa-solid fa-eye"></i> Dettaglio
                            </a>
                            
                            <form action="${pageContext.request.contextPath}/CarrelloServlet" method="POST" class="form-carrello">
                                <input type="hidden" name="action" value="add">
                                <input type="hidden" name="idProdotto" value="<%= p.getIdProdotto() %>">
                                <input type="hidden" name="quantita" value="1"> <button type="submit" class="btn-primary btn-carrello" title="Aggiungi al carrello">
                                    <i class="fa-solid fa-cart-plus"></i> Aggiungi al carrello
                                </button>
                            </form>
                        </div>
                    </div>
        <%
                }
            } else {
        %>
                <div class="catalogo-vuoto">
                    <i class="fa-solid fa-basket-shopping"></i>
                    <p>Nessun prodotto trovato in questa categoria.</p>
                </div>
        <%
            }
        %>
    </div>
</main>

<%@ include file="fragments/footer.jsp" %>

