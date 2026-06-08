package it.unisa.dispensadigiu.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrdineDAO {

    /**
     * Salva l'ordine e i rispettivi dettagli nel database utilizzando una transazione.
     * Sfrutta il ritorno delle chiavi generate per associare le righe di composizione.
     */
    public int doSave(OrdineBean ordine, Carrello carrello) throws SQLException {
        String queryOrdine = "INSERT INTO ordine (idutente, data_ordine, totale_ordine, costo_spedizione, stato_ordine) VALUES (?, ?, ?, ?, ?)";
        String queryDettaglio = "INSERT INTO composizioneOrdine (idordine, idprodotto, quantita, prezzo_unitario, iva) VALUES (?, ?, ?, ?, ?)";

        Connection con = null;
        
        try {
            con = ConnectionDatabase.getConnection();
            con.setAutoCommit(false); // Disattiva l'autocommit per gestire la transazione atomica

            int idOrdineGenerato = -1;

            // Inserimento della testata dell'ordine
            try (PreparedStatement psOrdine = con.prepareStatement(queryOrdine, Statement.RETURN_GENERATED_KEYS)) {
                psOrdine.setInt(1, ordine.getIdUtente());
                psOrdine.setTimestamp(2, ordine.getDataOrdine());
                psOrdine.setDouble(3, ordine.getTotaleOrdine());
                psOrdine.setDouble(4, ordine.getCostoSpedizione());
                psOrdine.setString(5, ordine.getStatoOrdine());
                
                psOrdine.executeUpdate();
                
                // Recupero dell'ID autoincrementale appena generato da MySQL
                try (ResultSet rs = psOrdine.getGeneratedKeys()) {
                    if (rs.next()) {
                        idOrdineGenerato = rs.getInt(1); 
                    } else {
                        throw new SQLException("Creazione ordine fallita, nessun ID generato.");
                    }
                }
            }

            //  Inserimento delle singole righe di dettaglio (composizioneOrdine)
            try (PreparedStatement psDettaglio = con.prepareStatement(queryDettaglio)) {
                for (ElementoCarrelloBean item : carrello.getElementi()) {
                    psDettaglio.setInt(1, idOrdineGenerato);
                    psDettaglio.setInt(2, item.getProdotto().getIdProdotto());
                    psDettaglio.setInt(3, item.getQuantita());
                    
                    // Congelamento dei prezzi e IVA storici al momento dell'acquisto
                    psDettaglio.setDouble(4, item.getProdotto().getPrezzoUnitario());
                    psDettaglio.setDouble(5, item.getProdotto().getIva());
                    
                    psDettaglio.executeUpdate();
                }
            }

            con.commit(); // Conferma tutte le operazioni della transazione
            return idOrdineGenerato;

        } catch (SQLException e) {
            if (con != null) {
                con.rollback(); // Annulla tutto in caso di fallimento parziale o totale
            }
            throw e;
        } finally {
            if (con != null) {
                con.setAutoCommit(true); // Ripristina lo stato predefinito prima di restituire la connessione al pool
                con.close(); // Restituisce la connessione al DataSource di Tomcat
            }
        }
    }
    
    
    public OrdineBean doRetrieveById(int idOrdine) throws SQLException {
        String query = "SELECT * FROM ordine WHERE idordine = ?";

        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, idOrdine);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToOrdine(rs);
                }
            }
        }
        return null;
    }

   
    public List<ElementoCarrelloBean> doRetrieveDettagliByOrdine(int idOrdine) throws SQLException {
        List<ElementoCarrelloBean> dettagli = new ArrayList<>();
        
        String query = "SELECT c.idprodotto AS c_idprodotto, c.quantita, c.prezzo_unitario, c.iva, " +
                       "p.nome, p.immagine_url " +
                       "FROM composizioneOrdine c " +
                       "LEFT JOIN prodotto p ON c.idprodotto = p.idprodotto " +
                       "WHERE c.idordine = ?";

        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, idOrdine);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProdottoBean p = new ProdottoBean();
                    
                    int idProdotto = rs.getInt("c_idprodotto");
                    if (rs.wasNull()) {
                        p.setIdProdotto(0); 
                        p.setNome("Prodotto non più disponibile");
                        p.setImmagineUrl("img/default.jpg"); 
                    } else {
                        p.setIdProdotto(idProdotto);
                        p.setNome(rs.getString("nome"));
                        p.setImmagineUrl(rs.getString("immagine_url"));
                    }
                    
                    // I valori economici storici vengono estratti sempre dalla riga d'ordine
                    p.setPrezzoUnitario(rs.getDouble("prezzo_unitario"));
                    p.setIva(rs.getDouble("iva"));

                    ElementoCarrelloBean item = new ElementoCarrelloBean(p, rs.getInt("quantita"));
                    dettagli.add(item);
                }
            }
        }
        return dettagli;
    }
    
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

    /**
     * Helper di mappatura per convertire una riga del ResultSet in un oggetto OrdineBean.
     */
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

