<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %> 
<%@ include file="fragments/header.jsp" %>

<main class="container-semplice container-errore-page testo-centro">
    <div class="box-errore">
        <div class="icona-errore colore-500">
            <i class="fa-solid fa-triangle-exclamation"></i>
        </div>
        
        <h1 class="codice-errore colore-500">500</h1>
        <h2>Qualcosa è andato storto...</h2>
        
        <p class="testo-descrizione-errore">
            Si è verificato un errore interno durante l'elaborazione della richiesta.<br>
            I nostri artigiani del software sono già all'opera per rimettere in sesto la dispensa!
        </p>
        
        <% if (exception != null) { %>
            <div class="debug-stacktrace-box">
                <strong>Dettaglio tecnico dell'errore:</strong><br>
                <%= exception.toString() %>
            </div>
        <% } %>

        <a href="<%= request.getContextPath() %>/index.jsp" class="btn-primary">
            Ritorna alla Home
        </a>
    </div>
</main>

<%@ include file="fragments/footer.jsp" %>