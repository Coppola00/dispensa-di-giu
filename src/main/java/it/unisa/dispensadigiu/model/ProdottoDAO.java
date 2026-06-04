package it.unisa.dispensadigiu.model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdottoDAO {

    public void doSave(ProdottoBean prodotto) throws SQLException {
        String query = "INSERT INTO Prodotto (nome, descrizione, prezzo_base, iva, categoria, immagine_url) VALUES (?, ?, ?, ?, ?, ?)";
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

    public void doUpdate(ProdottoBean prodotto) throws SQLException {
        String query = "UPDATE Prodotto SET nome = ?, descrizione = ?, prezzo_base = ?, iva = ?, categoria = ?, immagine_url = ? WHERE id_prodotto = ?";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, prodotto.getNome());
            ps.setString(2, prodotto.getDescrizione());
            ps.setDouble(3, prodotto.getPrezzoUnitario());
            ps.setDouble(4, prodotto.getIva());
            ps.setString(5, prodotto.getCategoria());
            ps.setString(6, prodotto.getImmagineUrl());
            ps.setInt(7, prodotto.getIdProdotto());
            ps.executeUpdate();
        }
    }

    public void doDelete(int idProdotto) throws SQLException {
        String query = "DELETE FROM Prodotto WHERE id_prodotto = ?";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, idProdotto);
            ps.executeUpdate();
        }
    }

    public ProdottoBean trovaById(int idProdotto) throws SQLException {
        String query = "SELECT * FROM Prodotto WHERE id_prodotto = ?";
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
        String query = "SELECT * FROM Prodotto";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                prodotti.add(mapRowToProdotto(rs));
            }
        }
        return prodotti;
    }

    private ProdottoBean mapRowToProdotto(ResultSet rs) throws SQLException {
        ProdottoBean p = new ProdottoBean();
        p.setIdProdotto(rs.getInt("id_prodotto"));
        p.setNome(rs.getString("nome"));
        p.setDescrizione(rs.getString("descrizione"));
        p.setPrezzoUnitario(rs.getDouble("prezzo_base"));
        p.setIva(rs.getDouble("iva"));
        p.setCategoria(rs.getString("categoria"));
        p.setImmagineUrl(rs.getString("immagine_url"));
        return p;
    }
    
    /**
     * Filtra i prodotti in base alla categoria esatta.
     */
    public List<ProdottoBean> trovaByCategory(String categoria) throws SQLException {
        List<ProdottoBean> prodotti = new ArrayList<>();
        String query = "SELECT * FROM Prodotto WHERE categoria = ?";
        
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
    
    /**
     * Cerca i prodotti il cui nome contiene i caratteri digitati (Case Insensitive in MySQL).
     * Ottimizzato per i suggerimenti AJAX limitando i risultati.
     */
    public List<ProdottoBean> doRetrieveByNomeAjax(String hint) throws SQLException {
        List<ProdottoBean> prodotti = new ArrayList<>();
        // Il LIMIT 5 è una best practice per non intasare l'interfaccia a tendina dei suggerimenti
        String query = "SELECT * FROM Prodotto WHERE nome LIKE ? LIMIT 5";
        
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            // Aggiungiamo i % prima e dopo per cercare in qualsiasi punto del nome
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