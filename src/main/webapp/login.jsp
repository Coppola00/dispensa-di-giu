<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="fragments/header.jsp" %>

<main class="container-auth">
    <div class="auth-card">
        <h2 class="titolo-auth">Accedi a La Dispensa di Giù</h2>
        
        <%@ include file="fragments/messaggi_feedback.jsp" %>
        
        <form id="formLogin" action="${pageContext.request.contextPath}/LoginServlet" method="post">
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="Inserisci la tua email" required>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Inserisci la password" required>
            </div>

            <button type="submit" class="btn-primary btn-full">Accedi</button>
        </form>

        <p class="auth-link">
            Non hai un account? <a href="${pageContext.request.contextPath}/registrazione.jsp">Registrati ora</a>
        </p>
    </div>
</main>

<%@ include file="fragments/footer.jsp" %>