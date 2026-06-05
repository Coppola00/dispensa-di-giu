package it.unisa.dispensadigiu.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtenteDAO {
	
	public void registrazione(UtenteBean utente) throws SQLException {
        String query = "INSERT INTO utente (nome, cognome, email, password, ruolo) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, utente.getNome());
            ps.setString(2, utente.getCognome());
            ps.setString(3, utente.getEmail());
            // CIFRATURA DELLA PASSWORD 
            ps.setString(4, utente.getPassword());
            ps.setString(5, utente.getRuolo() != null ? utente.getRuolo() : "USER");
            
            ps.executeUpdate();
        }
    }
	
	public UtenteBean controlloLogin(String email, String password) throws SQLException {
        String query = "SELECT * FROM utente WHERE email = ? AND password = ?";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, email);
            // Confrontiamo l'hash della password inserita con l'hash nel DB
            ps.setString(2, PasswordUtils.hashPassword(password));
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UtenteBean utente = new UtenteBean();
                    utente.setIdutente(rs.getInt("idutente"));
                    utente.setNome(rs.getString("nome"));
                    utente.setCognome(rs.getString("cognome"));
                    utente.setEmail(rs.getString("email"));
                    utente.setRuolo(rs.getString("ruolo"));
                    return utente;
                }
            }
        }
        return null;
    }
	
	public boolean emailEsistente(String email) throws SQLException {
        String query = "SELECT idutente FROM utente WHERE email = ?";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Ritorna true se l'email esiste già
            }
        }
    }
	
	/**
     * Aggiorna le informazioni personali dell'utente (esclusa la password).
     */
    public void aggiornaProfilo(UtenteBean utente) throws SQLException {
        String query = "UPDATE utente SET nome = ?, cognome = ?, email = ? WHERE idutente = ?";
        
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, utente.getNome());
            ps.setString(2, utente.getCognome());
            ps.setString(3, utente.getEmail());
            ps.setInt(4, utente.getIdutente()); 
            
            ps.executeUpdate();
        }
    }

    /**
     * Aggiorna esclusivamente la password dell'utente, applicando la cifratura.
     */
    public void aggiornaPassword(int idUtente, String nuovaPassword) throws SQLException {
        String query = "UPDATE utente SET password = ? WHERE idutente = ?";
        
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            // CIFRATURA DELLA PASSWORD prima di inserirla nella query
            ps.setString(1, PasswordUtils.hashPassword(nuovaPassword));
            ps.setInt(2, idUtente);
            
            ps.executeUpdate();
        }
    }
}
