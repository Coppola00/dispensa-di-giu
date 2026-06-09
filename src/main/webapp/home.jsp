<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="fragments/header.jsp" %>

<main>
    <section class="hero-section">
        <div class="hero-overlay"></div>
        <div class="hero-content">
            <h1 class="hero-title">Dal Sud, direttamente a casa tua.</h1>
            <p class="hero-subtitle">Senti già il profumo? Riempi la dispensa con i veri sapori della tradizione artigianale meridionale.</p>
            <div class="hero-actions">
                <a href="${pageContext.request.contextPath}/Catalogo" class="btn-primary hero-btn">
                    Fai scorta ora <i class="fa-solid fa-arrow-right"></i>
                </a>
            </div>
        </div>
    </section>
</main>

<%@ include file="fragments/footer.jsp" %>