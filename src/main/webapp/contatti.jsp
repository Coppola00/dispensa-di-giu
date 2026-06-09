<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="fragments/header.jsp" %>

<main class="container-semplice">
    <h1 class="titolo-pagina"><i class="fa-solid fa-envelope"></i> Contatta la Dispensa</h1>
    
    <%@ include file="fragments/messaggi_feedback.jsp" %>

    <div class="contatti-layout">
        
        <div class="contatti-info">
            <h3>I Nostri Recapiti</h3>
            <p>Se hai domande sui nostri prodotti artigianali o sul tuo ordine, non esitare a contattarci.</p>
            
            <ul class="lista-recapiti">
                <li><i class="fa-solid fa-location-dot"></i> Via Roma, 10 - 83028 Serino (AV)</li>
                <li><i class="fa-solid fa-phone"></i> +39 081 1234567</li>
                <li><i class="fa-solid fa-envelope"></i> info@dispensadigiu.it</li>
                <li><i class="fa-solid fa-clock"></i> Lun - Ven: 9:00 - 18:00</li>
            </ul>
        </div>

        <div class="contatti-form-box">
            <h3>Inviaci un Messaggio</h3>
            
            <form id="formContatti" action="<%= request.getContextPath() %>/ContattiServlet" method="POST" novalidate>
                
                <div class="form-group">
                    <label for="nome">Nome completo</label>
                    <input type="text" id="nome" name="nome" placeholder="Inserisci il tuo nome e cognome" required>
                    <span class="errore-inline" id="errore-nome"></span>
                </div>

                <div class="form-group">
                    <label for="email">Indirizzo Email</label>
                    <input type="email" id="email" name="email" placeholder="mario.rossi@email.it" required>
                    <span class="errore-inline" id="errore-email"></span>
                </div>

                <div class="form-group">
                    <label for="messaggio">Messaggio</label>
                    <textarea id="messaggio" name="messaggio" rows="5" class="input-textarea" placeholder="Scrivi qui la tua richiesta..." required></textarea>
                    <span class="errore-inline" id="errore-messaggio"></span>
                </div>

                <button type="submit" class="btn-primary btn-full">Invia Richiesta</button>
            </form>
        </div>

    </div>
</main>

<%@ include file="fragments/footer.jsp" %>
