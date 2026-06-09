<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String successMsg = (String) request.getAttribute("successMsg");
    String errorMsg = (String) request.getAttribute("errorMsg");
%>

<%-- Mostra il box di successo solo se la variabile esiste e non è vuota --%>
<% if (successMsg != null && !successMsg.trim().isEmpty()) { %>
    <div class="alert-box alert-success">
        <%= successMsg %>
    </div>
<% } %>

<%-- Mostra il box di errore solo se la variabile esiste e non è vuota --%>
<% if (errorMsg != null && !errorMsg.trim().isEmpty()) { %>
    <div class="alert-box alert-error">
        <%= errorMsg %>
    </div>
<% } %>