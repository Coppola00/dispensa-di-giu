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

<%@ include file="fragments/header.jsp" %>

<main class="container-area-utente">
    <h1 class="titolo-pagina"><i class="fa-solid fa-user-gear"></i> Area Cliente</h1>

    <div class="area-utente-layout">
        
        <div class="box-profilo-utente">
            <h3>I Tuoi Dati</h3>
            <div class="dati-anagrafici">
                <p><strong>Nome:</strong> <%= utente.getNome() %></p>
                <p><strong>Cognome:</strong> <%= utente.getCognome() %></p>
                <p><strong>Email:</strong> <%= utente.getEmail() %></p>
            </div>
            
            <hr class="separatore-profilo">
            
            <a href="<%= request.getContextPath() %>/LogoutServlet" class="btn-secondario btn-logout">
                <i class="fa-solid fa-arrow-right-from-bracket"></i> Disconnetti
            </a>
        </div>

        <div class="box-storico-ordini">
            <h3>I Tuoi Ordini</h3>
            
            <% if (ordini == null || ordini.isEmpty()) { %>
                <div class="storico-vuoto">
                    <i class="fa-solid fa-box-open icona-vuoto"></i>
                    <p>Non hai ancora effettuato ordini su La Dispensa di Giù.</p>
                    <a href="<%= request.getContextPath() %>/Catalogo" class="btn-primary">Inizia lo Shopping</a>
                </div>
            <% } else { %>
                <div class="tabella-responsive">
                    <table class="tabella-ordini"> 
                        <thead>
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
                                        <a href="<%= request.getContextPath() %>/Fattura?id=<%= o.getIdOrdine() %>" class="btn-visualizza-ordine" title="Vedi Dettagli e Stampa">
                                            <i class="fa-solid fa-file-invoice"></i> Fattura
                                        </a>
                                    </td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            <% } %>
        </div>

    </div>
</main>

<%@ include file="fragments/footer.jsp" %>
