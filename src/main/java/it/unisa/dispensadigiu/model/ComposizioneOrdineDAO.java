package it.unisa.dispensadigiu.model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComposizioneOrdineDAO {

    /**
     * Metodo di utilità interno per mappare il ResultSet nell'oggetto Bean.
     */
    private ComposizioneOrdineBean mapRowToComposizione(ResultSet rs) throws SQLException {
        ComposizioneOrdineBean c = new ComposizioneOrdineBean();
        c.setIdOrdine(rs.getInt("idordine"));
        c.setIdProdotto(rs.getInt("idprodotto"));
        c.setQuantita(rs.getInt("quantita"));
        c.setPrezzoUnitario(rs.getDouble("prezzo_unitario"));
        c.setIva(rs.getDouble("iva"));
        
        // Gestione della Box: usiamo getObject per permettere valori nulli dal DB
        Object idBoxObj = rs.getObject("idboxappartenenza");
        if (idBoxObj != null) {
            c.setIdBoxAppartenenza((Integer) idBoxObj);
        } else {
            c.setIdBoxAppartenenza(-1); // Prodotto sfuso
        }
        
        return c;
    }

    /**
     * 1. doSave
     * A cosa serve: Salva la singola riga d'ordine al momento del checkout.
     * Viene chiamato all'interno di un ciclo (FOR) per ogni elemento presente nel carrello.
     * Congela il prezzo d'acquisto e l'IVA garantendo l'integrità storica.
     */
    public void doSave(ComposizioneOrdineBean composizione) throws SQLException {
        String query = "INSERT INTO composizioneOrdine (idordine, idprodotto, quantita, prezzo_unitario, iva, idboxappartenenza) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, composizione.getIdOrdine());
            ps.setInt(2, composizione.getIdProdotto());
            ps.setInt(3, composizione.getQuantita());
            ps.setDouble(4, composizione.getPrezzoUnitario());
            ps.setDouble(5, composizione.getIva());
            
            if (composizione.getIdBoxAppartenenza() != -1) {
                ps.setInt(6, composizione.getIdBoxAppartenenza());
            } else {
                // Inseriamo -1 nel database se è un prodotto sfuso
                ps.setInt(6, -1);
            }
            
            ps.executeUpdate();
        }
    }

    /**
     * 2. doRetrieveByOrdine
     * A cosa serve: Recupera tutte le righe appartenenti a uno specifico ordine.
     * È il metodo fondamentale che utilizzerai nella Servlet per generare il riepilogo
     * ordine e compilare i dati della fattura da stampare via media query.
     */
    public List<ComposizioneOrdineBean> doRetrieveByOrdine(int idOrdine) throws SQLException {
        List<ComposizioneOrdineBean> righeOrdine = new ArrayList<>();
        String query = "SELECT * FROM composizioneOrdine WHERE idordine = ?";
        
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, idOrdine);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    righeOrdine.add(mapRowToComposizione(rs));
                }
            }
        }
        return righeOrdine;
    }
    
    /**
     * 3. doRetrieveProdottiBox
     * A cosa serve: Metodo specifico per visualizzare i dettagli di un "Pacco da Giù".
     * Estrae solo i prodotti di un ordine che appartengono a una determinata Box.
     */
    public List<ComposizioneOrdineBean> doRetrieveProdottiBox(int idOrdine, int idBox) throws SQLException {
        List<ComposizioneOrdineBean> righeBox = new ArrayList<>();
        String query = "SELECT * FROM composizioneOrdine WHERE idordine = ? AND idboxappartenenza = ?";
        
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, idOrdine);
            ps.setInt(2, idBox);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    righeBox.add(mapRowToComposizione(rs));
                }
            }
        }
        return righeBox;
    }
}