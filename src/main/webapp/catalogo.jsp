<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="it.unisa.dispensadigiu.model.ProdottoBean" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Catalogo - La Dispensa di Giù</title>
    
     <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
<body>

    <%@ include file="fragments/header.jsp" %>

    <main class="container">
        <h1>I Nostri Prodotti</h1>
        
        <div class="griglia-prodotti">
            <%
                // Recuperiamo la lista inserita dalla CatalogoServlet
                List<ProdottoBean> prodotti = (List<ProdottoBean>) request.getAttribute("listaProdotti");
                
                if (prodotti != null && !prodotti.isEmpty()) {
                    for (ProdottoBean p : prodotti) {
            %>
                        <div class="card-prodotto">
                            <img src="<%= p.getImmagineUrl() %>" alt="<%= p.getNome() %>">
                            <h3><%= p.getNome() %></h3>
                            <p class="prezzo"><%= String.format("%.2f", p.getPrezzoUnitario()) %> &euro;</p>
                            <form action="CarrelloServlet" method="POST">
                                <input type="hidden" name="idProdotto" value="<%= p.getIdProdotto() %>">
                                <button type="submit" class="btn-rosso-pomodoro">Aggiungi al carrello</button>
                            </form>
                        </div>
            <%
                    }
                } else {
            %>
                    <p>Nessun prodotto trovato in questa categoria.</p>
            <%
                }
            %>
        </div>
    </main>

    <%@ include file="fragments/footer.jsp" %>
</body>
</html>