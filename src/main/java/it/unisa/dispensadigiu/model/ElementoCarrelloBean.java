package it.unisa.dispensadigiu.model;

import java.io.Serializable;

public class ElementoCarrelloBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private ProdottoBean prodotto;
    private int quantita;

    public ElementoCarrelloBean() {
    }

    public ElementoCarrelloBean(ProdottoBean prodotto, int quantita) {
        this.prodotto = prodotto;
        this.quantita = quantita;
    }

    public ProdottoBean getProdotto() {
        return prodotto;
    }

    public void setProdotto(ProdottoBean prodotto) {
        this.prodotto = prodotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
    
    public double getTotaleParziale() {
        double totale = getPrezzoSingoloIvato() * quantita;
        return Math.round(totale * 100.0) / 100.0;
    }
    
    public double getPrezzoSingoloIvato() {
        double moltiplicatoreIva = 1 + (prodotto.getIva() / 100.0);
        double prezzoIvato = prodotto.getPrezzoUnitario() * moltiplicatoreIva;
        return Math.round(prezzoIvato * 100.0) / 100.0;
    }
}