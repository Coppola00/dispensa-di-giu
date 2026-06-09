<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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

<footer class="site-footer">
    <div class="footer-container">
        <div class="footer-col brand-col">
            <img src="${pageContext.request.contextPath}/img/logo.png" alt="La Dispensa di Giù" class="footer-logo">
            <p>I veri sapori del Sud, direttamente a casa tua.</p>
        </div>

        <div class="footer-col">
            <h4>Naviga</h4>
            <ul>
                <li><a href="${pageContext.request.contextPath}/home.jsp">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/Catalogo">Prodotti</a></li>
                <li><a href="${pageContext.request.contextPath}/contatti.jsp">Contatti</a></li>
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
                <a href="#" aria-label="Instagram"><i class="fa-brands fa-instagram"></i></a>
                <a href="#" aria-label="Facebook"><i class="fa-brands fa-facebook-f"></i></a>
                <a href="#" aria-label="TikTok"><i class="fa-brands fa-tiktok"></i></a>
            </div>
            
            <h4 class="mt-20">Metodi di pagamento</h4>
            <div class="payment-icons">
                <i class="fa-brands fa-cc-visa" title="Visa"></i>
                <i class="fa-brands fa-cc-mastercard" title="Mastercard"></i>
                <i class="fa-brands fa-cc-paypal" title="PayPal"></i>
                <i class="fa-brands fa-cc-apple-pay" title="Apple Pay"></i>
            </div>
        </div>
    </div>

    <div class="footer-bottom">
        <p>&copy; 2026 La Dispensa di Giù - P.IVA 12345678910 - Tutti i diritti riservati</p>
        <p>Sito Made by Raffaele Coppola</p>
    </div>
</footer>
