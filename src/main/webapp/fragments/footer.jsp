<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- Sezione Vantaggi (Pre-Footer) -->
<section class="pre-footer">
    <div class="features-row">
        <div class="feature-item">
            <i class="fa-solid fa-tractor"></i>
            <h4>Piccoli produttori</h4>
            <p>Sosteniamo le realtà locali</p>
        </div>
        <div class="feature-item">
            <i class="fa-solid fa-medal"></i>
            <h4>Qualità garantita</h4>
            <p>Selezioniamo solo il meglio</p>
        </div>
        <div class="feature-item">
            <i class="fa-solid fa-sun"></i>
            <h4>Tradizione autentica</h4>
            <p>Ricette di famiglia, sapori veri</p>
        </div>
    </div>
</section>

<!-- Footer -->
<footer class="site-footer">
    <div class="footer-container">
        <div class="footer-col brand-col">
            <img src="${pageContext.request.contextPath}/img/logo.png" alt="La Dispensa di Giù" class="footer-logo">
            <p>I veri sapori del Sud, direttamente a casa tua.</p>
        </div>

        <div class="footer-col">
            <h4>Naviga</h4>
            <ul>
                <li><a href="${pageContext.request.contextPath}/CatalogoServlet">Prodotti</a></li>
                <li><a href="${pageContext.request.contextPath}/pacco-da-giu.jsp">Pacco da Giù</a></li>
                <li><a href="${pageContext.request.contextPath}/chi-siamo.jsp">Chi Siamo</a></li>
                <li><a href="${pageContext.request.contextPath}/contatti.jsp">Contatti</a></li>
            </ul>
        </div>

        <div class="footer-col">
            <h4>Aiuto</h4>
            <ul>
                <li><a href="#">Spedizioni</a></li>               
                <li><a href="#">Domande Frequenti</a></li>
                <li><a href="#">Traccia il tuo ordine</a></li>
            </ul>
        </div>

        <div class="footer-col">
            <h4>Info</h4>
            <ul>
                <li><a href="#">Termini e Condizioni</a></li>
                <li><a href="#">Privacy Policy</a></li>
                <li><a href="#">Cookie Policy</a></li>
            </ul>
        </div>

        <div class="footer-col social-col">
            <h4>Seguici</h4>
            <div class="social-icons">
                <a href="#"><i class="fa-brands fa-instagram"></i></a>
                <a href="#"><i class="fa-brands fa-facebook-f"></i></a>
                <a href="#"><i class="fa-brands fa-tiktok"></i></a>
            </div>
            <h4 class="mt-20">Metodi di pagamento</h4>
            <div class="payment-icons">
                <i class="fa-brands fa-cc-visa"></i>
                <i class="fa-brands fa-cc-mastercard"></i>
                <i class="fa-brands fa-cc-paypal"></i>
                <i class="fa-brands fa-cc-apple-pay"></i>
            </div>
        </div>
    </div>

    <div class="footer-bottom">
        <p>&copy; 2026 La Dispensa di Giù - P.IVA 12345678910 - Tutti i diritti riservati</p>
        <p>Sito Made by Raffaele Coppola</p>
    </div>
</footer>