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
    
 // Calcola il totale parziale per questa riga, includendo l'IVA
    public double getTotaleParziale() {
    	
        // 1. Calcolo il moltiplicatore dell'IVA 
        double moltiplicatoreIva = 1 + prodotto.getIva();
        
        // 2. Calcolo il prezzo del singolo pezzo ivato
        double prezzoIvato = prodotto.getPrezzoUnitario () * moltiplicatoreIva;
        
        // 3. Moltiplico per la quantità e arrotondo a due cifre decimali
        double totale = prezzoIvato * quantita;
        
        // Usiamo Math.round per evitare problemi di precisione dei numeri decimali in Java (es. 10.999999999)
        return Math.round(totale * 100.0) / 100.0;
    }
    
 // Restituisce il prezzo del singolo prodotto con l'IVA (utile per la View)
    public double getPrezzoSingoloIvato() {
        double moltiplicatoreIva = 1 + (prodotto.getIva() / 100.0);
        double prezzoIvato = prodotto.getPrezzoUnitario() * moltiplicatoreIva;
        return Math.round(prezzoIvato * 100.0) / 100.0;
    }
}