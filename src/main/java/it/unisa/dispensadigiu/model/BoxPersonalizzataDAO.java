package it.unisa.dispensadigiu.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BoxPersonalizzataDAO {

    private BoxPersonalizzataBean mapRowToBox(ResultSet rs) throws SQLException {
        BoxPersonalizzataBean b = new BoxPersonalizzataBean();
        b.setIdBox(rs.getInt("idbox"));
        b.setIdUtente(rs.getInt("idutente"));
        b.setNome(rs.getString("nome_box"));
        return b;
    }

    /**
     * A cosa serve: Crea la "testata" del pacco personalizzato e restituisce l'ID generato.
     * L'ID generato servirà poi per inserire i prodotti usando ComposizioneOrdineDAO.
     */
    public int doSave(BoxPersonalizzataBean box) throws SQLException {
        String query = "INSERT INTO boxpersonalizzata (idutente, nome_box) VALUES (?, ?)";
        
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, box.getIdUtente());
            ps.setString(2, box.getNome());
            
            ps.executeUpdate();
            
            // Recupero l'ID generato in automatico dal database per questa Box
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    /**
     * A cosa serve: Permette a un utente loggato di visualizzare le proprie Box
     * create in precedenza, magari all'interno della sua Dashboard.
     */
    public List<BoxPersonalizzataBean> doRetrieveByUtente(int idUtente) throws SQLException {
        List<BoxPersonalizzataBean> boxes = new ArrayList<>();
        String query = "SELECT * FROM boxpersonalizzata WHERE idutente = ?";
        
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, idUtente);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    boxes.add(mapRowToBox(rs));
                }
            }
        }
        return boxes;
    }
    
    /**
     * A cosa serve: Recupera i dettagli di una singola box tramite il suo ID.
     */
    public BoxPersonalizzataBean doRetrieveById(int idBox) throws SQLException {
        String query = "SELECT * FROM boxpersonalizzata WHERE idbox = ?";
        
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, idBox);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToBox(rs);
                }
            }
        }
        return null;
    }
}