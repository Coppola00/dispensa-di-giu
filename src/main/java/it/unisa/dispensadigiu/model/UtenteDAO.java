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
                    utente.setIndirizzo(rs.getString("indirizzo")); // Aggiunto per comodità futura
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

    public void aggiornaPassword(int idUtente, String nuovaPassword) throws SQLException {
        String query = "UPDATE utente SET password = ? WHERE idutente = ?";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, PasswordUtils.hashPassword(nuovaPassword));
            ps.setInt(2, idUtente);
            
            ps.executeUpdate();
        }
    }
}