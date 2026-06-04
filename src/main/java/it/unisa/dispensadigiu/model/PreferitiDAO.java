package it.unisa.dispensadigiu.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PreferitiDAO {

    /**
     * Aggiunge un prodotto alla lista dei desideri dell'utente.
     */
    public void aggiungiPreferito(int idUtente, int idProdotto) throws SQLException {
        String query = "INSERT INTO Preferisce (id_utente, id_prodotto) VALUES (?, ?)";
        
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, idUtente);
            ps.setInt(2, idProdotto);
            
            // Usiamo executeUpdate per l'inserimento
            ps.executeUpdate();
        }
    }

    /**
     * Rimuove un prodotto dalla lista dei desideri dell'utente.
     */
    public void rimuoviPreferito(int idUtente, int idProdotto) throws SQLException {
        String query = "DELETE FROM Preferisce WHERE id_utente = ? AND id_prodotto = ?";
        
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, idUtente);
            ps.setInt(2, idProdotto);
            
            ps.executeUpdate();
        }
    }

    /**
     * Verifica se un prodotto è già presente nei preferiti (utile per mostrare il cuore "pieno" o "vuoto" nella UI).
     */
    public boolean isPreferito(int idUtente, int idProdotto) throws SQLException {
        String query = "SELECT 1 FROM Preferisce WHERE id_utente = ? AND id_prodotto = ?";
        
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, idUtente);
            ps.setInt(2, idProdotto);
            
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Ritorna true se trova una corrispondenza
            }
        }
    }

    /**
     * Estrae la lista COMPLETA dei prodotti preferiti da un utente sfruttando una JOIN.
     * Restituisce direttamente una lista di ProdottoBean, pronta per la View.
     */
    public List<ProdottoBean> doRetrieveProdottiPreferiti(int idUtente) throws SQLException {
        List<ProdottoBean> prodotti = new ArrayList<>();
        
        // La JOIN unisce la tabella dei preferiti con quella dei prodotti
        String query = "SELECT p.* FROM Prodotto p " +
                       "JOIN Preferisce pr ON p.id_prodotto = pr.id_prodotto " +
                       "WHERE pr.id_utente = ?";
        
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, idUtente);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Mappiamo il ResultSet in un ProdottoBean (come fai in ProdottoDAO)
                    ProdottoBean p = new ProdottoBean();
                    p.setIdProdotto(rs.getInt("id_prodotto"));
                    p.setNome(rs.getString("nome"));
                    p.setDescrizione(rs.getString("descrizione"));
                    p.setPrezzoUnitario(rs.getDouble("prezzo_base"));
                    p.setIva(rs.getDouble("iva"));
                    p.setCategoria(rs.getString("categoria"));
                    p.setImmagineUrl(rs.getString("immagine_url"));
                    
                    prodotti.add(p);
                }
            }
        }
        return prodotti;
    }
}