<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="fragments/header.jsp" %>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<main class="container-auth">
    <h2 class="titolo-auth">Accedi a La Dispensa di Giù</h2>
    
    <%@ include file="fragments/messaggi_feedback.jsp" %>
    
    <form id="formLogin" action="LoginServlet" method="post">
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" placeholder="Inserisci la tua email" required>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" placeholder="Inserisci la password" required>
        </div>

        <button type="submit" class="btn-pomodoro btn-full">Accedi</button>
    </form>

    <p style="text-align: center; margin-top: 20px;">
        Non hai un account? <a href="registrazione.jsp" style="color: var(--rosso-pomodoro);">Registrati ora</a>
    </p>
</main>

<%@ include file="fragments/footer.jsp" %>