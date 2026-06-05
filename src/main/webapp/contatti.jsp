<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contatti | La Dispensa di Giù</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <%@ include file="fragments/header.jsp" %>

    <main class="static-page">
        <section class="page-header">
            <h1>Contattaci</h1>
            <p>Siamo qui per aiutarti. Scrivici per informazioni sui prodotti o sul tuo ordine.</p>
        </section>

        <section class="contact-container">
            <div class="contact-info">
                <h3>I nostri recapiti</h3>
                <div class="info-item">
                    <i class="fa-solid fa-location-dot"></i>
                    <div>
                        <strong>Sede Operativa</strong><br>
                        Via delle Tradizioni, 12<br>
                        84084 Fisciano (SA)
                    </div>
                </div>
                <div class="info-item">
                    <i class="fa-solid fa-envelope"></i>
                    <div>
                        <strong>Email</strong><br>
                        info@dispensadigiu.it
                    </div>
                </div>
                <div class="info-item">
                    <i class="fa-solid fa-phone"></i>
                    <div>
                        <strong>Telefono</strong><br>
                        +39 089 1234567<br>
                        <small>Lun - Ven: 9:00 - 18:00</small>
                    </div>
                </div>
            </div>

            <div class="contact-form-wrapper">
                <h3>Inviaci un messaggio</h3>
                <form action="#" method="POST" class="contact-form">
                    <div class="form-group">
                        <label for="nome">Nome e Cognome *</label>
                        <input type="text" id="nome" name="nome" placeholder="Es. Mario Rossi" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="email">Email *</label>
                        <input type="email" id="email" name="email" placeholder="mario.rossi@email.com" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="oggetto">Oggetto</label>
                        <select id="oggetto" name="oggetto">
                            <option value="info">Informazioni sui prodotti</option>
                            <option value="ordine">Problema con un ordine</option>
                            <option value="fornitore">Proponi i tuoi prodotti</option>
                            <option value="altro">Altro</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="messaggio">Messaggio *</label>
                        <textarea id="messaggio" name="messaggio" rows="5" placeholder="Scrivi qui il tuo messaggio..." required></textarea>
                    </div>
                    
                    <button type="submit" class="btn-primary">Invia Messaggio <i class="fa-solid fa-paper-plane"></i></button>
                </form>
            </div>
        </section>
    </main>

    <%@ include file="fragments/footer.jsp" %>

    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>