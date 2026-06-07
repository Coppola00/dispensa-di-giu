<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header class="site-header">
    <div class="top-bar">
        <div class="top-bar-item">
            <i class="fa-solid fa-truck"></i> Spedizione in 24/48h
        </div>
        <div class="top-bar-item center-text">
            <i class="fa-solid fa-leaf"></i> I veri sapori del Sud, a casa tua.
        </div>
        <div class="top-bar-item right-text">
            <i class="fa-solid fa-star"></i>  Filiera corta e tracciabile
        </div>
    </div>

    <nav class="navbar">
        
        <div class="navbar-brand">
            <a href="${pageContext.request.contextPath}/index.jsp">
                <img src="${pageContext.request.contextPath}/img/logo.png" alt="La Dispensa di Giù Logo" class="logo-img">
            </a>
        </div>
        
        <button class="hamburger-btn" aria-label="Apri menu principale">
            <i class="fa-solid fa-bars"></i>
        </button>

        <div class="header-menu">
        <%@ include file="menu.jsp" %>
        </div>

        <div class="navbar-actions">
            <div class="search-wrapper">
        <button id="btn-toggle-ricerca" class="icon-btn search-btn" aria-label="Cerca nel sito">
            <i class="fa-solid fa-magnifying-glass"></i>
        </button>

    <div id="contenitore-ricerca-nascosto" class="search-inline" style="display: none;">
        <input type="text" id="barraRicerca" placeholder="Cerca i sapori di giù..." autocomplete="off">
        
        <div id="suggerimentiRicerca" class="suggerimenti-box"></div>
    </div>
    </div>
            
            <a href="login.jsp" class="icon-btn user-btn" aria-label="Area Utente">
                <i class="fa-regular fa-user"></i>
            </a>

            <a href="CarrelloServlet" class="cart-btn">
                <i class="fa-solid fa-cart-shopping"></i> Carrello
                <span class="cart-badge">0</span>
            </a>
        </div>
    </nav>
<script>
    const contextPath = "${pageContext.request.contextPath}";
</script>    
    
<script src="${pageContext.request.contextPath}/js/ricercaAjax.js" defer></script>
</header>