package it.unisa.dispensadigiu.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecensioneDAO {

    /**
     * Metodo di utilità interno per mappare il ResultSet.
     */
    private RecensioneBean mapRowToRecensione(ResultSet rs) throws SQLException {
        RecensioneBean r = new RecensioneBean();
        r.setIdRecensione(rs.getInt("id_recensione"));
        r.setIdUtente(rs.getInt("id_utente"));
        r.setIdProdotto(rs.getInt("id_prodotto"));
        r.setValutazioneStelle(rs.getInt("valutazione_stelle"));
        r.setTesto(rs.getString("testo"));
        r.setDataPubblicazione(rs.getDate("data_pubblicazione"));
        return r;
    }

    /**
     * Inserisce una nuova recensione nel database.
     * Utilizza CURRENT_DATE di SQL per registrare automaticamente il giorno dell'inserimento.
     */
    public void doSave(RecensioneBean recensione) throws SQLException {
        String query = "INSERT INTO Recensione (id_utente, id_prodotto, valutazione_stelle, testo, data_pubblicazione) VALUES (?, ?, ?, ?, CURRENT_DATE)";
        
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, recensione.getIdUtente());
            ps.setInt(2, recensione.getIdProdotto());
            ps.setInt(3, recensione.getValutazioneStelle());
            ps.setString(4, recensione.getTesto());
            
            ps.executeUpdate();
        }
    }

    /**
     * Estrae tutte le recensioni relative a un singolo prodotto.
     * Ordinate dalla più recente alla più vecchia per mostrarle nella View.
     */
    public List<RecensioneBean> doRetrieveByProdotto(int idProdotto) throws SQLException {
        List<RecensioneBean> recensioni = new ArrayList<>();
        String query = "SELECT * FROM Recensione WHERE id_prodotto = ? ORDER BY data_pubblicazione DESC";
        
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, idProdotto);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    recensioni.add(mapRowToRecensione(rs));
                }
            }
        }
        return recensioni;
    }
    
    /**
     * Opzionale: Estrae tutte le recensioni scritte da un singolo utente.
     * Utile se vuoi mostrare uno storico nella Dashboard Utente.
     */
    public List<RecensioneBean> doRetrieveByUtente(int idUtente) throws SQLException {
        List<RecensioneBean> recensioni = new ArrayList<>();
        String query = "SELECT * FROM Recensione WHERE id_utente = ? ORDER BY data_pubblicazione DESC";
        
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, idUtente);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    recensioni.add(mapRowToRecensione(rs));
                }
            }
        }
        return recensioni;
    }
}