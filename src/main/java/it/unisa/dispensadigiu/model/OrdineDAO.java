package it.unisa.dispensadigiu.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrdineDAO {

    // Salva l'ordine e restituisce l'ID generato automaticamente
    public int doSave(OrdineBean ordine) throws SQLException {
        String query = "INSERT INTO ordine (idutente, data_ordine, totale_ordine, costo_spedizione, stato_ordine) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConnectionDatabase.getConnection();
             // Chiediamo a JDBC di restituirci le chiavi generate [cite: 170]
             PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, ordine.getIdUtente());
            ps.setTimestamp(2, ordine.getDataOrdine());
            ps.setDouble(3, ordine.getTotaleOrdine());
            ps.setDouble(4, ordine.getCostoSpedizione());
            ps.setString(5, ordine.getStatoOrdine());
            
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Restituisce l'id_ordine appena creato
                }
            }
        }
        return -1;
    }

    /**
     * 1. doRetrieveAll
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