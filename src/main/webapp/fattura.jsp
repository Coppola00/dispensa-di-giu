<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.dispensadigiu.model.OrdineBean" %>
<%@ page import="it.unisa.dispensadigiu.model.ElementoCarrelloBean" %>
<%@ page import="it.unisa.dispensadigiu.model.UtenteBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
    UtenteBean intestatario = (UtenteBean) request.getAttribute("utenteFattura");
    OrdineBean ordine = (OrdineBean) request.getAttribute("ordineFattura");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
%>

<div class="no-print">
    <%@ include file="fragments/header.jsp" %>
</div>

<main class="container-fattura">
    
    <div id="documento-fattura" class="box-fattura">
        
        <div class="intestazione-fattura">
            <div class="dati-mittente">
                <img src="<%= request.getContextPath() %>/img/logo.png" alt="Logo La Dispensa di Giù" width="120">
                <h2>La Dispensa di Giù</h2>
                <p>Via Roma, 10 - 83028 Serino (AV)</p>
                <p>P.IVA: IT12345678901</p>
            </div>
            
            <div class="dati-documento">
                <h1>Riepilogo Ordine</h1>
                <p><strong>Numero Ordine:</strong> #<%= ordine.getIdOrdine() %></p>
                <p><strong>Data:</strong> <%= sdf.format(ordine.getDataOrdine()) %></p>
                <p><strong>Stato:</strong> <span class="badge-stato"><%= ordine.getStatoOrdine() %></span></p>
            </div>
        </div>

        <hr class="separatore-fattura">

        <div class="dati-cliente">
            <h3>Intestato a:</h3>
            <p><strong><%= intestatario.getNome() %> <%= intestatario.getCognome() %></strong></p>
            <p>Email: <%= intestatario.getEmail() %></p>
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
                   List<ElementoCarrelloBean> dettagli = (List<ElementoCarrelloBean>) request.getAttribute("dettagliFattura");	
                
                    if (ordine != null) {
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
                    }
                %>
            </tbody>
        </table>

        <div class="totali-fattura">
            <p>Subtotale: <%= String.format("%.2f", ordine.getTotaleOrdine()) %> &euro;</p>
            <p>Spedizione: GRATIS</p>
            <h2 class="totale-netto">Totale pagato: <%= String.format("%.2f", ordine.getTotaleOrdine()) %> &euro;</h2>
        </div>
        
    </div>

    <div class="azioni-fattura no-print">
        <button onclick="window.print()" class="btn-primary">
            <i class="fa-solid fa-print"></i> Stampa Documento
        </button>
        <a href="<%= request.getContextPath() %>/AreaUtente" class="btn-secondario">
            <i class="fa-solid fa-arrow-left"></i> Torna agli Ordini
        </a>
    </div>

</main>

<div class="no-print">
    <%@ include file="fragments/footer.jsp" %>
</div>
