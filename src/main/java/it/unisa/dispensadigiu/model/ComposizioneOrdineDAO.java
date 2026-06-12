package it.unisa.dispensadigiu.model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import javax.sql.DataSource;

public class ComposizioneOrdineDAO {

    private DataSource ds;

    public ComposizioneOrdineDAO(DataSource ds) {
        this.ds = ds;
    }

    // Salva una riga d'ordine nel database
    public void doSave(ComposizioneOrdineBean composizione) throws SQLException {
        String sql = "INSERT INTO composizioneOrdine (idordine, idprodotto, quantita, prezzo_unitario, iva) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ds.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, composizione.getIdOrdine());
            
            
            if (composizione.getIdProdotto() != null) {
                ps.setInt(2, composizione.getIdProdotto());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }
            
            ps.setInt(3, composizione.getQuantita());
            ps.setDouble(4, composizione.getPrezzoUnitario());
            ps.setDouble(5, composizione.getIva());

            ps.executeUpdate();
        }
    }

    public Collection<ComposizioneOrdineBean> doRetrieveByOrdine(int idOrdine) throws SQLException {
        String sql = "SELECT * FROM composizioneOrdine WHERE idordine = ?";
        Collection<ComposizioneOrdineBean> righe = new ArrayList<>();

        try (Connection con = ds.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idOrdine);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ComposizioneOrdineBean riga = new ComposizioneOrdineBean();
                    riga.setIdRigaOrdine(rs.getInt("id_riga_ordine"));
                    riga.setIdOrdine(rs.getInt("idordine"));
                    
                    // Lettura dell'eventuale valore NULL dal database
                    int idProdotto = rs.getInt("idprodotto");
                    if (rs.wasNull()) {
                        riga.setIdProdotto(null);
                    } else {
                        riga.setIdProdotto(idProdotto);
                    }
                    
                    riga.setQuantita(rs.getInt("quantita"));
                    riga.setPrezzoUnitario(rs.getDouble("prezzo_unitario"));
                    riga.setIva(rs.getDouble("iva"));
                    
                    righe.add(riga);
                }
            }
        }
        return righe;
    }
}