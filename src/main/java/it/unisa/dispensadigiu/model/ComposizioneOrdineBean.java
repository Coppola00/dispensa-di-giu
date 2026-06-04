package it.unisa.dispensadigiu.model;

import java.io.Serializable;

public class ComposizioneOrdineBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int idOrdine;
	private int idProdotto;
	private int idBoxAppartenenza;
	private int quantita;
	private Double prezzoUnitario;
	private Double iva;
	
	public ComposizioneOrdineBean() {
			}

	public int getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(int idOrdine) {
		this.idOrdine = idOrdine;
	}

	public int getIdProdotto() {
		return idProdotto;
	}

	public void setIdProdotto(int idProdotto) {
		this.idProdotto = idProdotto;
	}


	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public Double getPrezzoUnitario() {
		return prezzoUnitario;
	}

	public void setPrezzoUnitario(Double prezzoAcquisto) {
		this.prezzoUnitario = prezzoAcquisto;
	}

	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public int getIdBoxAppartenenza() {
		return idBoxAppartenenza;
	}

	public void setIdBoxAppartenenza(int idBoxAppartenenza) {
		this.idBoxAppartenenza = idBoxAppartenenza;
	}

	

}
