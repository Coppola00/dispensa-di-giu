<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="fragments/header.jsp" %>

<main class="container-auth">
    <div class="auth-card">
        <h2 class="titolo-auth">Unisciti a La Dispensa di Giù</h2>
        
        <%@ include file="fragments/messaggi_feedback.jsp" %>

        <form id="formRegistrazione" action="${pageContext.request.contextPath}/RegistrazioneServlet" method="post" novalidate>
            
            <div class="form-group">
                <label for="nome">Nome</label>
                <input type="text" id="nome" name="nome" placeholder="Inserisci il tuo nome (es. Mario)">
                <span class="errore-inline" id="errore-nome"></span>
            </div>
            
            <div class="form-group">
                <label for="cognome">Cognome</label> <input type="text" id="cognome" name="cognome" placeholder="Inserisci il tuo cognome (es. Rossi)">
                <span class="errore-inline" id="errore-cognome"></span>
            </div>

            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="mario.rossi@email.it">
                <span class="errore-inline" id="errore-email"></span>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Almeno 8 caratteri, una maiuscola e un numero">
                <span class="errore-inline" id="errore-password"></span>
            </div>

            <button type="submit" class="btn-primary btn-full">Registrati</button>
        </form>
        
        <p class="auth-link">
            Hai già un account? <a href="${pageContext.request.contextPath}/login.jsp">Accedi ora</a>
        </p>
    </div>
</main>

<%@ include file="fragments/footer.jsp" %>

<script src="${pageContext.request.contextPath}/js/validazione.js"></script>

