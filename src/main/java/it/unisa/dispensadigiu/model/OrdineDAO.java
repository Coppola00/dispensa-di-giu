package it.unisa.dispensadigiu.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrdineDAO {

	// Salva l'ordine E i suoi dettagli usando le Transazioni
    public int doSave(OrdineBean ordine, Carrello carrello) throws SQLException {
        String queryOrdine = "INSERT INTO ordine (idutente, data_ordine, totale_ordine, costo_spedizione, stato_ordine) VALUES (?, ?, ?, ?, ?)";
        // Aggiornata con i nomi della tua tabella composizioneOrdine
        String queryDettaglio = "INSERT INTO composizioneOrdine (idordine, idprodotto, quantita, prezzo_unitario, iva) VALUES (?, ?, ?, ?, ?)";

        Connection con = null;
        
        try {
            con = ConnectionDatabase.getConnection();
            con.setAutoCommit(false); // Inizia la Transazione

            int idOrdineGenerato = -1;

            try (PreparedStatement psOrdine = con.prepareStatement(queryOrdine, Statement.RETURN_GENERATED_KEYS)) {
                psOrdine.setInt(1, ordine.getIdUtente());
                psOrdine.setTimestamp(2, ordine.getDataOrdine());
                psOrdine.setDouble(3, ordine.getTotaleOrdine());
                psOrdine.setDouble(4, ordine.getCostoSpedizione());
                psOrdine.setString(5, ordine.getStatoOrdine());
                
                psOrdine.executeUpdate();
                
                try (ResultSet rs = psOrdine.getGeneratedKeys()) {
                    if (rs.next()) {
                        idOrdineGenerato = rs.getInt(1); 
                    } else {
                        throw new SQLException("Creazione ordine fallita, nessun ID generato.");
                    }
                }
            }

            try (PreparedStatement psDettaglio = con.prepareStatement(queryDettaglio)) {
                for (ElementoCarrelloBean item : carrello.getElementi()) {
                    psDettaglio.setInt(1, idOrdineGenerato);
                    psDettaglio.setInt(2, item.getProdotto().getIdProdotto());
                    psDettaglio.setInt(3, item.getQuantita());
                    psDettaglio.setDouble(4, item.getProdotto().getPrezzoUnitario());
                    psDettaglio.setDouble(5, item.getProdotto().getIva());
                    
                    psDettaglio.executeUpdate();
                }
            }

            con.commit(); // Salva tutto definitivamente
            return idOrdineGenerato;

        } catch (SQLException e) {
            if (con != null) {
                con.rollback(); // Annulla tutto in caso di errore
            }
            throw e;
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }
    
    public OrdineBean doRetrieveById(int idOrdine) throws SQLException {
        OrdineBean ordine = null;
        String query = "SELECT * FROM ordine WHERE idordine = ?";

        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, idOrdine);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ordine = new OrdineBean();
                    ordine.setIdOrdine(rs.getInt("idordine"));
                    ordine.setIdUtente(rs.getInt("idutente"));
                    ordine.setDataOrdine(rs.getTimestamp("data_ordine"));
                    ordine.setTotaleOrdine(rs.getDouble("totale_ordine"));
                    ordine.setCostoSpedizione(rs.getDouble("costo_spedizione"));
                    ordine.setStatoOrdine(rs.getString("stato_ordine"));
                }
            }
        }
        return ordine;
    }

    /**
     * Recupera la lista dei prodotti acquistati in un determinato ordine,
     * utilizzando i prezzi e l'IVA "congelati" nella tabella composizioneOrdine.
     */
    public List<ElementoCarrelloBean> doRetrieveDettagliByOrdine(int idOrdine) throws SQLException {
        List<ElementoCarrelloBean> dettagli = new ArrayList<>();
        
        // JOIN tra la tua tabella composizioneOrdine e prodotto
        String query = "SELECT c.quantita, c.prezzo_unitario, c.iva, " +
                       "p.idprodotto, p.nome, p.immagine_url " +
                       "FROM composizioneOrdine c " +
                       "JOIN prodotto p ON c.idprodotto = p.idprodotto " +
                       "WHERE c.idordine = ?";

        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, idOrdine);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProdottoBean p = new ProdottoBean();
                    p.setIdProdotto(rs.getInt("idprodotto"));
                    p.setNome(rs.getString("nome"));
                    p.setImmagineUrl(rs.getString("immagine_url"));
                    
                    // Assegniamo i prezzi storici estratti da composizioneOrdine
                    p.setPrezzoUnitario(rs.getDouble("prezzo_unitario"));
                    p.setIva(rs.getDouble("iva"));

                    ElementoCarrelloBean item = new ElementoCarrelloBean(p, rs.getInt("quantita"));
                    dettagli.add(item);
                }
            }
        }
        return dettagli;
    }
    
    /**
     * A cosa serve: Mostra all'Admin l'elenco complessivo di tutti gli ordini ricevuti.
     * Ordiniamo i risultati in modo decrescente (dal più recente al più vecchio).
     */
    public List<OrdineBean> doRetrieveAll() throws SQLException {
        List<OrdineBean> ordini = new ArrayList<>();
        String query = "SELECT * FROM ordine ORDER BY data_ordine DESC";
        
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ordini.add(mapRowToOrdine(rs));
            }
        }
        return ordini;
    }
    
    
    public List<OrdineBean> storicoOrdiniUtente(int idUtente) throws SQLException {
        List<OrdineBean> ordini = new ArrayList<>();
        String query = "SELECT * FROM ordine WHERE idutente = ? ORDER BY data_ordine DESC";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, idUtente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ordini.add(mapRowToOrdine(rs));
                }
            }
        }
        return ordini;
    }

    // Filtro per data per l'amministratore [cite: 190]
    public List<OrdineBean> storicoOrdiniByDateRange(java.sql.Date dal, java.sql.Date al) throws SQLException {
        List<OrdineBean> ordini = new ArrayList<>();
        String query = "SELECT * FROM ordine WHERE DATE(data_ordine) BETWEEN ? AND ? ORDER BY data_ordine DESC";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setDate(1, dal);
            ps.setDate(2, al);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ordini.add(mapRowToOrdine(rs));
                }
            }
        }
        return ordini;
    }

    private OrdineBean mapRowToOrdine(ResultSet rs) throws SQLException {
        OrdineBean o = new OrdineBean();
        o.setIdOrdine(rs.getInt("idordine"));
        o.setIdUtente(rs.getInt("idutente"));
        o.setDataOrdine(rs.getTimestamp("data_ordine"));
        o.setTotaleOrdine(rs.getDouble("totale_ordine"));
        o.setCostoSpedizione(rs.getDouble("costo_spedizione"));
        o.setStatoOrdine(rs.getString("stato_ordine"));
        return o;
    }
}