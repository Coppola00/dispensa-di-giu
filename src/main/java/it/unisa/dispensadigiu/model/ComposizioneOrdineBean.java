package it.unisa.dispensadigiu.model;


import java.io.Serializable;

public class ComposizioneOrdineBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idRigaOrdine;
    private int idOrdine;
    private Integer idProdotto; // Uso di Integer per consentire il valore null
    private int quantita;
    private double prezzoUnitario;
    private double iva;

    public ComposizioneOrdineBean() {
    }

    public int getIdRigaOrdine() {
        return idRigaOrdine;
    }

    public void setIdRigaOrdine(int idRigaOrdine) {
        this.idRigaOrdine = idRigaOrdine;
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public Integer getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(Integer idProdotto) {
        this.idProdotto = idProdotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public double getPrezzoUnitario() {
        return prezzoUnitario;
    }

    public void setPrezzoUnitario(double prezzoUnitario) {
        this.prezzoUnitario = prezzoUnitario;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }
}
