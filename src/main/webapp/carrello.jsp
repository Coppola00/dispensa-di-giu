<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.dispensadigiu.model.Carrello" %>
<%@ page import="it.unisa.dispensadigiu.model.ElementoCarrelloBean" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Il Tuo Carrello - La Dispensa di Giù</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <%@ include file="fragments/header.jsp" %>

    <main class="container">
        <h1 class="titolo-pagina"><i class="fa-solid fa-basket-shopping"></i> Il tuo Carrello</h1>

        <%
            // Recuperiamo il carrello dalla sessione
            Carrello carrello = (Carrello) session.getAttribute("carrello");
            
            if (carrello == null || carrello.getElementi().isEmpty()) {
        %>
                <div class="carrello-vuoto">
                    <i class="fa-solid fa-bag-shopping icona-vuoto"></i>
                    <h2>Il tuo carrello è ancora vuoto!</h2>
                    <p>Esplora il nostro catalogo e lasciati conquistare dalle eccellenze artigianali del Sud.</p>
                    <a href="${pageContext.request.contextPath}/Catalogo" class="btn-rosso-pomodoro btn-ritorna">
                        Torna al Catalogo
                    </a>
                </div>
        <%
            } else {
        %>
                <div class="carrello-layout">
                    
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
                                                <img src="${pageContext.request.contextPath}/<%= item.getProdotto().getImmagineUrl() %>" alt="<%= item.getProdotto().getNome() %>" class="carrello-img">
                                                <span class="nome-prodotto"><%= item.getProdotto().getNome() %></span>
                                            </td>
                                            
                                            <td data-label="Prezzo">
                                                <%= String.format("%.2f", item.getPrezzoSingoloIvato()) %> &euro;
                                            </td>
                                            
                                            <td data-label="Quantità">
                                                <form action="${pageContext.request.contextPath}/CarrelloServlet" method="GET" class="form-quantita">
                                                    <input type="hidden" name="action" value="update">
                                                    <input type="hidden" name="idProdotto" value="<%= item.getProdotto().getIdProdotto() %>">
                                                    <input type="number" name="quantita" value="<%= item.getQuantita() %>" min="1" max="99" class="input-quantita" onchange="this.form.submit()">
                                                    <button type="submit" class="btn-aggiorna" title="Aggiorna quantità">
                                                        <i class="fa-solid fa-rotate"></i>
                                                    </button>
                                                </form>
                                            </td>
                                            
                                            <td data-label="Totale" class="subtotale-riga">
                                                <%= String.format("%.2f", item.getTotaleParziale()) %> &euro;
                                            </td>
                                            
                                            <td data-label="Azioni">
                                                <form action="${pageContext.request.contextPath}/CarrelloServlet" method="GET">
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

                    <div class="carrello-riepilogo">
                        <div class="box-totale">
                            <h3>Riepilogo Ordine</h3>
                            <div class="riga-riepilogo">
                                <span>Articoli nel carrello:</span>
                                <strong><%= carrello.getNumeroArticoli() %></strong>
                            </div>
                            <div class="riga-riepilogo">
                                <span>Spedizione:</span>
                                <span class="spedizione-gratis">GRATIS</span>
                            </div>
                            <hr class="separatore-riepilogo">
                            <div class="riga-riepilogo totale-finale">
                                <span>Totale:</span>
                                <span><%= String.format("%.2f", carrello.getTotaleComplessivo()) %> &euro;</span>
                            </div>
                            
                            <a href="${pageContext.request.contextPath}/Checkout" class="btn-rosso-pomodoro btn-checkout">
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

</body>
</html>

