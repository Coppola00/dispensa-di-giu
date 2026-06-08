<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.dispensadigiu.model.UtenteBean" %>
<%@ page import="it.unisa.dispensadigiu.model.OrdineBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>


<%
    // Recuperiamo l'utente dalla sessione e la lista ordini dalla servlet
    UtenteBean utente = (UtenteBean) session.getAttribute("utente");
    List<OrdineBean> ordini = (List<OrdineBean>) request.getAttribute("storicoOrdini");
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Area Riservata - La Dispensa di Giù</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

    <%@ include file="fragments/header.jsp" %>

    <main class="container">
        <h1 class="titolo-pagina"><i class="fa-solid fa-user-gear"></i> Area Cliente</h1>

        <div class="area-utente-layout">
            
            <div class="box-profilo-utente">
                <h3>I Tuoi Dati</h3>
                <div class="dati-anagrafici">
                    <p><strong>Nome:</strong> <%= utente.getNome() %></p>
                    <p><strong>Cognome:</strong> <%= utente.getCognome() %></p>
                    <p><strong>Email:</strong> <%= utente.getEmail() %></p>
                </div>
                <hr>
                <a href="${pageContext.request.contextPath}/LogoutServlet" class="btn-secondario" style="margin-left:0; width:100%; text-align:center;">
                    <i class="fa-solid fa-arrow-right-from-bracket"></i> Disconnetti
                </a>
            </div>

            <div class="box-storico-ordini">
                <h3>I Tuoi Ordini</h3>
                
                <% if (ordini == null || ordini.isEmpty()) { %>
                    <div class="storico-vuoto">
                        <i class="fa-solid fa-box-open"></i>
                        <p>Non hai ancora effettuato ordini su La Dispensa di Giù.</p>
                        <a href="${pageContext.request.contextPath}/Catalogo" class="btn-rosso-pomodoro">Inizia lo Shopping</a>
                    </div>
                <% } else { %>
                    <table class="tabella-carrello"> <thead>
                            <tr>
                                <th>ID Ordine</th>
                                <th>Data e Ora</th>
                                <th>Stato</th>
                                <th>Totale</th>
                                <th>Azioni</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (OrdineBean o : ordini) { %>
                                <tr>
                                    <td data-label="ID Ordine"><strong>#<%= o.getIdOrdine() %></strong></td>
                                    <td data-label="Data e Ora"><%= sdf.format(o.getDataOrdine()) %></td>
                                    <td data-label="Stato">
                                        <span class="badge-stato"><%= o.getStatoOrdine() %></span>
                                    </td>
                                    <td data-label="Totale"><%= String.format("%.2f", o.getTotaleOrdine()) %> &euro;</td>
                                    <td data-label="Azioni">
                                        <a href="${pageContext.request.contextPath}/Fattura?id=<%= o.getIdOrdine() %>" class="btn-visualizza-ordine" title="Vedi Dettagli e Stampa">
                                            <i class="fa-solid fa-file-invoice"></i> Fattura
                                        </a>
                                    </td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                <% } %>
            </div>

        </div>
    </main>

    <%@ include file="fragments/footer.jsp" %>

</body>
</html>