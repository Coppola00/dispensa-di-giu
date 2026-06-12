package it.unisa.dispensadigiu.model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdottoDAO {
	
	// Utility per mappare Prodotto
	private ProdottoBean mapRowToProdotto(ResultSet rs) throws SQLException {
        ProdottoBean p = new ProdottoBean();
        p.setIdProdotto(rs.getInt("idprodotto"));
        p.setNome(rs.getString("nome"));
        p.setDescrizione(rs.getString("descrizione"));
        p.setPrezzoUnitario(rs.getDouble("prezzo_unitario"));
        p.setIva(rs.getDouble("iva"));
        p.setCategoria(rs.getString("categoria"));
        p.setImmagineUrl(rs.getString("immagine_url"));
        return p;
    }

    public void doSave(ProdottoBean prodotto) throws SQLException {
        String query = "INSERT INTO prodotto (nome, descrizione, prezzo_unitario, iva, categoria, immagine_url) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, prodotto.getNome());
            ps.setString(2, prodotto.getDescrizione());
            ps.setDouble(3, prodotto.getPrezzoUnitario());
            ps.setDouble(4, prodotto.getIva());
            ps.setString(5, prodotto.getCategoria());
            ps.setString(6, prodotto.getImmagineUrl());
            ps.executeUpdate();
        }
    }

    public void doDelete(int idProdotto) throws SQLException {
        String query = "DELETE FROM prodotto WHERE idprodotto = ?";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, idProdotto);
            ps.executeUpdate();
        }
    }

    public ProdottoBean trovaById(int idProdotto) throws SQLException {
        String query = "SELECT * FROM prodotto WHERE idprodotto = ?";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, idProdotto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToProdotto(rs);
                }
            }
        }
        return null;
    }

    public List<ProdottoBean> trovaTutti() throws SQLException {
        List<ProdottoBean> prodotti = new ArrayList<>();
        String query = "SELECT * FROM prodotto";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                prodotti.add(mapRowToProdotto(rs));
            }
        }
        return prodotti;
    }

    
    public List<ProdottoBean> trovaByCategory(String categoria) throws SQLException {
        List<ProdottoBean> prodotti = new ArrayList<>();
        String query = "SELECT * FROM prodotto WHERE categoria = ?";
        
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, categoria);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    prodotti.add(mapRowToProdotto(rs));
                }
            }
        }
        return prodotti;
    }
    
 
    public List<ProdottoBean> doRetrieveByNomeAjax(String hint) throws SQLException {
        List<ProdottoBean> prodotti = new ArrayList<>();
        String query = "SELECT * FROM prodotto WHERE nome LIKE ? LIMIT 5";
        
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, "%" + hint + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    prodotti.add(mapRowToProdotto(rs));
                }
            }
        }
        return prodotti;
    }
}