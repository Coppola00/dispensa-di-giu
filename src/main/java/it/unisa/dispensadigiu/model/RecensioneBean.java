package it.unisa.dispensadigiu.model;

import java.io.Serializable;
import java.sql.Date;

public class RecensioneBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int idRecensione;
	private int idUtente;
	private int idProdotto;
	private int valutazioneStelle;
	private String testo;
	private Date dataPubblicazione;
	public int getIdRecensione() {
		return idRecensione;
	}
	public void setIdRecensione(int idRecensione) {
		this.idRecensione = idRecensione;
	}
	public int getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}
	public int getIdProdotto() {
		return idProdotto;
	}
	public void setIdProdotto(int idProdotto) {
		this.idProdotto = idProdotto;
	}
	public int getValutazioneStelle() {
		return valutazioneStelle;
	}
	public void setValutazioneStelle(int valutazioneStelle) {
		this.valutazioneStelle = valutazioneStelle;
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public Date getDataPubblicazione() {
		return dataPubblicazione;
	}
	public void setDataPubblicazione(Date dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}

}
