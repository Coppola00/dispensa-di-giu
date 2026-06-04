package it.unisa.dispensadigiu.model;

import java.io.Serializable;

public class ProdottoBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int idProdotto;
	private String nome;
	private String descrizione;
	private Double prezzoUnitario;
	private Double iva;
	private String categoria;
	private String immagineUrl;
	
	public ProdottoBean() {
			}

	public int getIdProdotto() {
		return idProdotto;
	}

	public void setIdProdotto(int idProdotto) {
		this.idProdotto = idProdotto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Double getPrezzoUnitario() {
		return prezzoUnitario;
	}

	public void setPrezzoUnitario(Double prezzoUnitario) {
		this.prezzoUnitario = prezzoUnitario;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getImmagineUrl() {
		return immagineUrl;
	}

	public void setImmagineUrl(String immagineUrl) {
		this.immagineUrl = immagineUrl;
	}

	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}
	
	
	

}
