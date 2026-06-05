<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%  
    String currentURI = request.getRequestURI();
%>
<nav class="nav-menu">
    <ul>
        <li>
            <a href="home.jsp" class="<%= currentURI.endsWith("home.jsp") || currentURI.endsWith("/") ? "active" : "" %>">Home</a>
        </li>
        <li>
            <a href="CatalogoServlet" class="<%= currentURI.endsWith("CatalogoServlet") ? "active" : "" %>">Prodotti</a>
        </li>
        <li>
            <a href="ComponiPaccoServlet" class="<%= currentURI.endsWith("ComponiPaccoServlet") ? "active" : "" %>">Pacco da Giù</a>
        </li>
        <li>
            <a href="chi-siamo.jsp" class="<%= currentURI.endsWith("chi-siamo.jsp") ? "active" : "" %>">Chi Siamo</a>
        </li>
        <li>
            <a href="contatti.jsp" class="<%= currentURI.endsWith("contatti.jsp") ? "active" : "" %>">Contatti</a>
        </li>
    </ul>
</nav>