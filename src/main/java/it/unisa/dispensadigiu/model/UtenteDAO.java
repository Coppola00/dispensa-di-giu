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
            
            // FIX: Ora la password viene cifrata prima di essere inserita nel DB
            ps.setString(4, PasswordUtils.hashPassword(utente.getPassword()));
            
            // FIX: Case-sensitive per combaciare con l'ENUM del DB ('User' e non 'USER')
            ps.setString(5, utente.getRuolo() != null ? utente.getRuolo() : "User");
            
            ps.executeUpdate();
        }
    }
	
    public UtenteBean controlloLogin(String email, String password) throws SQLException {
        String query = "SELECT * FROM utente WHERE email = ? AND password = ?";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, email);
            ps.setString(2, PasswordUtils.hashPassword(password));
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UtenteBean utente = new UtenteBean();
                    utente.setIdutente(rs.getInt("idutente"));
                    utente.setNome(rs.getString("nome"));
                    utente.setCognome(rs.getString("cognome"));
                    utente.setEmail(rs.getString("email"));
                    utente.setRuolo(rs.getString("ruolo"));
                    utente.setIndirizzo(rs.getString("indirizzo")); 
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
                return rs.next(); 
            }
        }
    }

    public UtenteBean doRetrieveById(int idUtente) throws SQLException {
        String query = "SELECT * FROM utente WHERE idUtente = ?";
        
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, idUtente);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UtenteBean u = new UtenteBean();
                    u.setIdutente(rs.getInt("idUtente"));
                    u.setNome(rs.getString("nome"));
                    u.setCognome(rs.getString("cognome"));
                    u.setEmail(rs.getString("email"));
                    u.setRuolo(rs.getString("ruolo"));
                    
                    return u; 
                }
            }
        }
        return null; 
	}
}