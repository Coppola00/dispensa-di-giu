package it.unisa.dispensadigiu.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Carrello implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<ElementoCarrelloBean> elementi;

    public Carrello() {
        elementi = new ArrayList<>();
    }

    public void aggiungiProdotto(ProdottoBean prodotto, int quantita) {
        // Controllo prima se il prodotto è già nel carrello
        for (ElementoCarrelloBean elemento : elementi) {
            if (elemento.getProdotto().getIdProdotto() == prodotto.getIdProdotto()) {
                elemento.setQuantita(elemento.getQuantita() + quantita);
                return;
            }
        }
        elementi.add(new ElementoCarrelloBean(prodotto, quantita));
    }

    public void rimuoviProdotto(int idProdotto) {
        elementi.removeIf(e -> e.getProdotto().getIdProdotto() == idProdotto);
    }

    public void aggiornaQuantita(int idProdotto, int nuovaQuantita) {
        if (nuovaQuantita <= 0) {
            rimuoviProdotto(idProdotto);
            return;
        }
        for (ElementoCarrelloBean elemento : elementi) {
            if (elemento.getProdotto().getIdProdotto() == idProdotto) {
                elemento.setQuantita(nuovaQuantita);
                break;
            }
        }
    }

    public void svuotaCarrello() {
        elementi.clear();
    }

    public List<ElementoCarrelloBean> getElementi() {
        return elementi;
    }

    public double getTotaleComplessivo() {
        double totale = 0;
        for (ElementoCarrelloBean elemento : elementi) {
            totale += elemento.getTotaleParziale();
        }
        return totale;
    }
    
    public int getNumeroArticoli() {
        int conteggio = 0;
        for (ElementoCarrelloBean elemento : elementi) {
            conteggio += elemento.getQuantita();
        }
        return conteggio;
    }
    
  
}