<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.dispensadigiu.model.Carrello" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">


<header class="site-header">
    <div class="top-bar">
        <div class="top-bar-item"><i class="fa-solid fa-truck"></i> Spedizione in 24/48h</div>
        <div class="top-bar-item center-text"><i class="fa-solid fa-leaf"></i> I veri sapori del Sud, a casa tua.</div>
        <div class="top-bar-item right-text"><i class="fa-solid fa-star"></i> Filiera corta e tracciabile</div>
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

       <%@ include file="menu.jsp" %>

        <div class="search-wrapper">
            <div class="search-input-group">
                <input type="text" id="barraRicerca" placeholder="Cerca i sapori di giù..." autocomplete="off">
                <button type="button" class="search-submit" aria-label="Cerca">
                    <i class="fa-solid fa-magnifying-glass"></i>
                </button>
            </div>
            <div id="suggerimentiRicerca" class="suggerimenti-box"></div>
        </div>

       <div class="navbar-actions">
            
            <%
                // Controlliamo se esiste un utente in sessione
                Object utenteLoggato = session.getAttribute("utente");
                
                // Se è loggato va all'Area Utente (Servlet), altrimenti va alla Login (JSP)
                String linkDestinazione = (utenteLoggato != null) 
                                          ? request.getContextPath() + "/AreaUtente" 
                                          : request.getContextPath() + "/login.jsp";
            %>

            <a href="<%= linkDestinazione %>" class="icon-btn user-btn" aria-label="Area Utente">
                <i class="fa-regular fa-user"></i>
            </a>

            <a href="<%= request.getContextPath() %>/CarrelloServlet?action=view" class="cart-btn">
                <i class="fa-solid fa-cart-shopping"></i> <span class="hide-on-mobile"></span>
                
                <%
                    // Recupero il carrello per il badge numerico
                    Object carrelloBadge = session.getAttribute("carrello");
                    int numeroArticoliBadge = 0;
                    if (carrelloBadge != null) {                        
                        numeroArticoliBadge = ((it.unisa.dispensadigiu.model.Carrello) carrelloBadge).getNumeroArticoli();
                    }
                %>
                <span class="cart-badge"><%= numeroArticoliBadge %></span>
            </a>
        </div>
    </nav>

   <% 
        String toastMsg = (String) session.getAttribute("toastMsg");
        if (toastMsg != null) { 
    %>
        <div id="toast-notification" class="toast-show"><%= toastMsg %></div>
    <% 
            session.removeAttribute("toastMsg"); 
        } 
    %>
   </header>

<script>
    // Variabile globale utile per gli script esterni
    const contextPath = "${pageContext.request.contextPath}";

    document.addEventListener("DOMContentLoaded", function() {
        // 1. Logica Hamburger Menu
        const hamburger = document.querySelector(".hamburger-btn");
        const headerMenu = document.querySelector(".header-menu");
        if (hamburger && headerMenu) {
            hamburger.addEventListener("click", () => headerMenu.classList.toggle("show"));
        }

        // 2. Logica scomparsa Toast Notification
        const toast = document.getElementById("toast-notification");
        if (toast) {
            setTimeout(() => toast.classList.replace("toast-show", "toast-hide"), 3000);
        }
    });
</script>
<script src="${pageContext.request.contextPath}/js/ricercaAjax.js" defer></script>