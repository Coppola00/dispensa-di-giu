<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.dispensadigiu.model.OrdineBean" %>
<%@ page import="it.unisa.dispensadigiu.model.ElementoCarrelloBean" %>
<%@ page import="it.unisa.dispensadigiu.model.UtenteBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
    OrdineBean ordine = (OrdineBean) request.getAttribute("ordineFattura");
    List<ElementoCarrelloBean> dettagli = (List<ElementoCarrelloBean>) request.getAttribute("dettagliFattura");
    UtenteBean utente = (UtenteBean) session.getAttribute("utente");
    
    // Formattatore per la data
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Fattura Ordine #<%= ordine.getIdOrdine() %> - La Dispensa di Giù</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <div class="no-print">
        <%@ include file="fragments/header.jsp" %>
    </div>

    <main class="container">
        
        <div id="documento-fattura" class="box-fattura">
            
            <div class="intestazione-fattura">
                <div class="dati-mittente">
                    <img src="${pageContext.request.contextPath}/img/logo.png" alt="Logo" width="120">
                    <h2>La Dispensa di Giù</h2>
                    <p>Via dei Sapori, 10 - 80100 Napoli (NA)</p>
                    <p>P.IVA: IT12345678901</p>
                </div>
                
                <div class="dati-documento">
                    <h1>Riepilogo Ordine</h1>
                    <p><strong>Numero Ordine:</strong> #<%= ordine.getIdOrdine() %></p>
                    <p><strong>Data:</strong> <%= sdf.format(ordine.getDataOrdine()) %></p>
                    <p><strong>Stato:</strong> <span class="badge-stato"><%= ordine.getStatoOrdine() %></span></p>
                </div>
            </div>

            <hr>

            <div class="dati-cliente">
                <h3>Intestato a:</h3>
                <p><strong><%= utente.getNome() %> <%= utente.getCognome() %></strong></p>
                <p>Email: <%= utente.getEmail() %></p>
            </div>

            <table class="tabella-fattura">
                <thead>
                    <tr>
                        <th>Prodotto</th>
                        <th class="testo-centro">Q.tà</th>
                        <th class="testo-destra">Prezzo Unit. (Ivato)</th>
                        <th class="testo-destra">Totale</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (ElementoCarrelloBean item : dettagli) {
                    %>
                            <tr>
                                <td><%= item.getProdotto().getNome() %></td>
                                <td class="testo-centro"><%= item.getQuantita() %></td>
                                <td class="testo-destra"><%= String.format("%.2f", item.getPrezzoSingoloIvato()) %> &euro;</td>
                                <td class="testo-destra"><strong><%= String.format("%.2f", item.getTotaleParziale()) %> &euro;</strong></td>
                            </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>

            <div class="totali-fattura">
                <p>Subtotale: <%= String.format("%.2f", ordine.getTotaleOrdine()) %> &euro;</p>
                <p>Spedizione: GRATIS</p>
                <h2 class="totale-netto">Totale Pagato: <%= String.format("%.2f", ordine.getTotaleOrdine()) %> &euro;</h2>
            </div>
            
        </div>

        <div class="azioni-fattura no-print">
            <button onclick="window.print()" class="btn-rosso-pomodoro">
                <i class="fa-solid fa-print"></i> Stampa Documento
            </button>
            
        </div>

    </main>

</body>
</html>