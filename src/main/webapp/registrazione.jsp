<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="fragments/header.jsp" %>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
    
<main class="container-auth">
    <h2 class="titolo-auth">Unisciti a La Dispensa di Giù</h2>
    
    

    <form id="formRegistrazione" action="RegistrazioneServlet" method="post" novalidate>
        
        <div class="form-group">
            <label for="nome">Nome</label>
            <input type="text" id="nome" name="nome" placeholder="Inserisci il tuo nome (es. Mario)">
            <span class="errore-inline" id="errore-nome"></span>
        </div>
        
        <div class="form-group">
            <label for="nome">Cognome</label>
            <input type="text" id="cognome" name="cognome" placeholder="Inserisci il tuo cognome (es. Rossi)">
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

        <button type="submit" class="btn-pomodoro btn-full">Registrati</button>
    </form>
</main>

<script src="js/validazione.js"></script>
<%@ include file="fragments/footer.jsp" %>