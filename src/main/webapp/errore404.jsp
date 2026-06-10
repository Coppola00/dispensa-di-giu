<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="fragments/header.jsp" %>

<main class="container-semplice container-errore-page testo-centro">
    <div class="box-errore">
        <div class="icona-errore colore-404">
            <i class="fa-solid fa-compass-drafting"></i>
        </div>
        
        <h1 class="codice-errore colore-404">404</h1>
        <h2>Pagina non trovata!</h2>
        
        <p class="testo-descrizione-errore">
            La pagina o il prodotto che stai cercando non è presente nella nostra Dispensa.<br>
            Potrebbe essere stato rimosso o l'indirizzo digitato potrebbe essere errato.
        </p>
        
        <a href="<%= request.getContextPath() %>/home.jsp" class="btn-primary">
            Torna alla Home
        </a>
    </div>
</main>

<%@ include file="fragments/footer.jsp" %>