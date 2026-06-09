<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Legge dalla request 
    String successMsg = (String) request.getAttribute("successMsg");
    String errorMsg = (String) request.getAttribute("errorMsg");

    // Legge dalla sessione 
    String assegnatoToast = (String) session.getAttribute("toastMsg");
    if (assegnatoToast != null) {
        String testoLower = assegnatoToast.toLowerCase();
        
        // Se il messaggio contiene parole chiave negative, lo trattiamo come errore
        if (testoLower.contains("errore") || testoLower.contains("negato") || testoLower.contains("errat")) {
            errorMsg = assegnatoToast;
        } else {
            successMsg = assegnatoToast;
        }
        
        // Rimuoviamo il messaggio originale dalla sessione dopo averlo letto
        session.removeAttribute("toastMsg");
    }
%>

<%-- Mostra il box di successo solo se la variabile esiste e non è vuota --%>
<% if (successMsg != null && !successMsg.trim().isEmpty()) { %>
    <div class="alert-box alert-success">
        <i class="fa-solid fa-circle-check"></i> <%= successMsg %>
    </div>
<% } %>

<%-- Mostra il box di errore solo se la variabile esiste e non è vuota --%>
<% if (errorMsg != null && !errorMsg.trim().isEmpty()) { %>
    <div class="alert-box alert-error">
        <i class="fa-solid fa-circle-exclamation"></i> <%= errorMsg %>
    </div>
<% } %>