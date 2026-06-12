<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.dispensadigiu.model.ProdottoBean" %>
<%@ page import="it.unisa.dispensadigiu.model.OrdineBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%@ include file="fragments/header.jsp" %>

<%
    // Recupero variabili per il filtro date degli ordini
    String dataInizio = (String) request.getAttribute("dataInizioSelezionata");
    String dataFine = (String) request.getAttribute("dataFineSelezionata");
    if(dataInizio == null) dataInizio = "";
    if(dataFine == null) dataFine = "";

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
%>

<main class="container-semplice">
    
    <div class="admin-header-bar">
        <h1 class="titolo-pagina"><i class="fa-solid fa-user-shield"></i> Pannello Amministratore</h1>
        <a href="<%= request.getContextPath() %>/LogoutServlet" class="btn-logout">
            <i class="fa-solid fa-right-from-bracket"></i> Disconnetti
        </a>
    </div>

    <%@ include file="fragments/messaggi_feedback.jsp" %>

    <div class="box-admin-form">
        <h3><i class="fa-solid fa-plus"></i> Aggiungi Nuovo Prodotto in Dispensa</h3>
        
        <form action="<%= request.getContextPath() %>/AdminProdotto" method="POST" enctype="multipart/form-data" class="form-admin-prodotto">
            <input type="hidden" name="action" value="inserisci">

            <div class="form-group-orizzontale">
                <div class="campo-form">
                    <label for="nome">Nome Prodotto</label>
                    <input type="text" id="nome" name="nome" required>
                </div>
                <div class="campo-form">
                    <label for="categoria">Categoria</label>
                    <input type="text" id="categoria" name="categoria" placeholder="es. Salumi, Formaggi, Sottoli" required>
                </div>
            </div>

            <div class="form-group-orizzontale">
                <div class="campo-form">
                    <label for="prezzo">Prezzo Unitario (&euro;)</label>
                    <input type="number" id="prezzo" name="prezzo" step="0.01" min="0.01" required>
                </div>
                <div class="campo-form">
                    <label for="iva">Aliquota IVA (%)</label>
                    <input type="number" id="iva" name="iva" min="0" max="100" value="22" required>
                </div>
                <div class="campo-form">
                    <label for="immagine">Scegli Immagine Prodotto</label>
                    <input type="file" id="immagine" name="immagine" accept="image/*" required>
                </div>
            </div>

            <div class="form-group">
                <label for="descrizione">Descrizione Prodotto</label>
                <textarea id="descrizione" name="descrizione" rows="3" class="input-textarea" required></textarea>
            </div>

            <button type="submit" class="btn-primary">Pubblica Prodotto</button>
        </form>
    </div>

    <div class="box-admin-tabella">
        <h3><i class="fa-solid fa-boxes-stacked"></i> Inventario Prodotti Corrente</h3>
        
        <%
            List<ProdottoBean> prodotti = (List<ProdottoBean>) request.getAttribute("listaProdottiAdmin");
            if (prodotti == null || prodotti.isEmpty()) {
        %>
            <p>La dispensa è vuota. Inserisci il tuo primo prodotto compilando il form soprastante!</p>
        <%
            } else {
        %>
            <table class="tabella-admin">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Immagine</th>
                        <th>Nome</th>
                        <th>Categoria</th>
                        <th>Prezzo</th>
                        <th>IVA</th>
                        <th class="testo-centro">Azioni</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (ProdottoBean p : prodotti) { %>
                        <tr>
                            <td><%= p.getIdProdotto() %></td>
                            <td>
                                <img src="<%= request.getContextPath() %>/<%= p.getImmagineUrl() %>" alt="Anteprima" class="img-mini-admin">
                            </td>
                            <td><strong><%= p.getNome() %></strong></td>
                            <td><%= p.getCategoria() %></td>
                            <td><%= String.format("%.2f", p.getPrezzoUnitario()) %> &euro;</td>
                            <td><%= (p.getIva() != null) ? String.format("%.0f", p.getIva()) : "22" %>%</td>
                            <td class="testo-centro">
                                <a href="<%= request.getContextPath() %>/AdminProdotto?action=elimina&idProdotto=<%= p.getIdProdotto() %>" 
                                   class="btn-rimuovi-admin" 
                                   onclick="return confirm('Sei sicuro di voler eliminare definitivamente questo prodotto dalla dispensa?');">
                                    <i class="fa-solid fa-trash-can"></i> Elimina
                                </a>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <%
            }
        %>
    </div>

    <div class="box-admin-form box-filtro-ordini">
    <h3><i class="fa-solid fa-filter"></i> Filtra Registro Ordini Clienti</h3>
    
    <form action="<%= request.getContextPath() %>/AdminProdotto" method="GET" class="form-filtro-date">
        <div class="form-group-orizzontale">
            <div class="campo-form">
                <label for="dataInizio">Data Inizio (Dal):</label>
                <input type="date" id="dataInizio" name="dataInizio" value="<%= dataInizio %>">
            </div>
            
            <div class="campo-form">
                <label for="dataFine">Data Fine (Al):</label>
                <input type="date" id="dataFine" name="dataFine" value="<%= dataFine %>">
            </div>
            
            <div class="campo-form azioni-filtro">
                <button type="submit" class="btn-primary btn-applica">Applica Filtro</button>
                <% if(!dataInizio.isEmpty() || !dataFine.isEmpty()) { %>
                    <a href="<%= request.getContextPath() %>/AdminProdotto" class="btn-logout btn-annulla">Annulla</a>
                <% } %>
            </div>
        </div>
    </form>
    </div>

    <div class="box-admin-tabella">
        <h3><i class="fa-solid fa-list-check"></i> Elenco Storico delle Transazioni</h3>
        
        <%
            List<OrdineBean> ordini = (List<OrdineBean>) request.getAttribute("listaOrdiniAdmin");
            if (ordini == null || ordini.isEmpty()) {
        %>
            <p>Nessun ordine registrato nel sistema per il periodo selezionato.</p>
        <%
            } else {
        %>
            <table class="tabella-admin">
                <thead>
                    <tr>
                        <th>ID Ordine</th>
                        <th>Data e Ora</th>
                        <th>ID Acquirente</th>
                        <th>Totale Lordo</th>
                        <th>Stato Spedizione</th>
                        <th class="testo-centro">Dettaglio</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (OrdineBean o : ordini) { %>
                        <tr>
                            <td>#<%= o.getIdOrdine() %></td>
                            <td><%= (o.getDataOrdine() != null) ? sdf.format(o.getDataOrdine()) : "N/D" %></td>
                            <td>Codice Utente: <%= o.getIdUtente() %></td>
                            <td><strong><%= String.format("%.2f", o.getTotaleOrdine()) %> &euro;</strong></td>
                            <td>
                                <span class="badge-stato <%= o.getStatoOrdine().toLowerCase() %>">
                                    <%= o.getStatoOrdine() %>
                                </span>
                            </td>
                            <td class="testo-centro">
                                <a href="<%= request.getContextPath() %>/Fattura?id=<%= o.getIdOrdine() %>" class="btn-visualizza-ordine" title="Vedi Dettagli e Stampa">
                                            <i class="fa-solid fa-file-invoice"></i>Vedi Fattura
                                        </a>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <%
            }
        %>
    </div>
</main>

<%@ include file="fragments/footer.jsp" %>
