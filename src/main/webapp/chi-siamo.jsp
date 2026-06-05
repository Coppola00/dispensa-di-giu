<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi Siamo | La Dispensa di Giù</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <%@ include file="fragments/header.jsp" %>

    <main class="static-page">
        <section class="page-header">
            <h1>La nostra storia</h1>
            <p>Passione, dati e sapore: come portiamo il Sud a casa tua.</p>
        </section>

        <section class="about-content">
            <div class="about-text">
                <h2>Oltre la semplice nostalgia</h2>
                <p><strong>La Dispensa di Giù</strong> non è solo una vetrina digitale, ma un ponte reale tra le piccole eccellenze locali e le tavole di chi vive lontano. Il progetto nasce da una sinergia precisa: da un lato l'esigenza di costruire un'infrastruttura tecnologica solida e moderna, dall'altro l'attenta analisi statistica ed economica del mercato agroalimentare per selezionare i prodotti migliori e garantire una filiera equa e sostenibile.</p>
                
                <p>Non scegliamo i nostri fornitori a caso. Combiniamo l'amore per la tradizione con uno studio rigoroso dei dati e dei trend di mercato, collaborando fianco a fianco con chi conosce intimamente l'economia del territorio. Questo ci permette di offrirti il massimo della qualità artigianale a un prezzo giusto, supportando al contempo le piccole realtà del Meridione.</p>
                
                <ul class="about-values">
                    <li><i class="fa-solid fa-check"></i> <strong>Selezione basata sui dati:</strong> Solo prodotti con un reale impatto sull'economia locale.</li>
                    <li><i class="fa-solid fa-check"></i> <strong>Trasparenza:</strong> Filiera tracciabile dal produttore al tuo carrello.</li>
                    <li><i class="fa-solid fa-check"></i> <strong>Innovazione:</strong> Un'interfaccia semplice per un'esperienza di acquisto perfetta.</li>
                </ul>
            </div>
            <div class="about-image">
                <div class="placeholder-img">
                    <i class="fa-solid fa-store"></i>
                </div>
            </div>
        </section>
    </main>

    <%@ include file="fragments/footer.jsp" %>

    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>