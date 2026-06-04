package it.unisa.dispensadigiu.model;

import java.io.Serializable;

public class BoxPersonalizzataBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int idBox;
	private int idUtente;
	private String nome;
	
	
	public BoxPersonalizzataBean () {
			}
	
	
	public int getIdBox() {
		return idBox;
	}
	public void setIdBox(int idBox) {
		this.idBox = idBox;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public int getIdUtente() {
		return idUtente;
	}


	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}


	
	

}
