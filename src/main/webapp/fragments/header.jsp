<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.dispensadigiu.model.Carrello" %>

<%
    Carrello carrelloHeader = (Carrello) session.getAttribute("carrello");
    int numeroArticoli = 0;
    if (carrelloHeader != null) {
        numeroArticoli = carrelloHeader.getNumeroArticoli();
    }
%>

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
            
            <a href="${pageContext.request.contextPath}/AreaUtente" class="icon-btn user-btn" aria-label="Area Utente">
                <i class="fa-regular fa-user"></i>
            </a>

            <a href="${pageContext.request.contextPath}/CarrelloServlet?action=view" class="cart-btn">
                <i class="fa-solid fa-cart-shopping"></i> Carrello
                <span class="cart-badge"><%= numeroArticoli %></span>
            </a>
        </div>
    </nav>

    <script>
        const contextPath = "${pageContext.request.contextPath}";
    </script>   
    <script src="${pageContext.request.contextPath}/js/ricercaAjax.js" defer></script>

    <%-- Gestione Toast Notification --%>
    <%
        String toastMsg = (String) session.getAttribute("toastMsg");
        if (toastMsg != null) {
    %>
            <div id="toast-notification" class="toast-show">
                <%= toastMsg %>
            </div>
            
            <script>
                setTimeout(function() {
                    var toast = document.getElementById("toast-notification");
                    if(toast) {
                        toast.className = toast.className.replace("toast-show", "toast-hide");
                    }
                }, 3000);
            </script>
    <%
            session.removeAttribute("toastMsg");
        }
    %>

    <%-- JAVASCRIPT CORRETTO PER IL TOGGLE DEL MENU MOBILE --%>
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            const hamburger = document.querySelector(".hamburger-btn");
            const headerMenu = document.querySelector(".header-menu");

            if (hamburger && headerMenu) {
                hamburger.addEventListener("click", function() {
                    // Accende/spegne la classe .show coerente con le regole CSS sotto
                    headerMenu.classList.toggle("show");
                });
            }
        });
    </script>
</header>