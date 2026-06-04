package it.unisa.dispensadigiu.model;

import java.io.Serializable;
import java.sql.Timestamp;


public class OrdineBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int idOrdine;
	private int idUtente;
	private Timestamp dataOrdine;
	private Double totaleOrdine;
	private Double costoSpedizione;
	private String statoOrdine;
	private String dest_via;
	private String dest_civico;
	private String dest_citta;
	private String dest_cap;
	private String dest_provincia;
	
	public OrdineBean() {
			}

	public int getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(int idOrdine) {
		this.idOrdine = idOrdine;
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public Timestamp getDataOrdine() {
		return dataOrdine;
	}

	public void setDataOrdine(Timestamp dataOrdine) {
		this.dataOrdine = dataOrdine;
	}

	public Double getTotaleOrdine() {
		return totaleOrdine;
	}

	public void setTotaleOrdine(Double totaleOrdine) {
		this.totaleOrdine = totaleOrdine;
	}

	public String getStatoOrdine() {
		return statoOrdine;
	}

	public void setStatoOrdine(String statoOrdine) {
		this.statoOrdine = statoOrdine;
	}

	public Double getCostoSpedizione() {
		return costoSpedizione;
	}

	public void setCostoSpedizione(Double costoSpedizione) {
		this.costoSpedizione = costoSpedizione;
	}

	public String getDest_via() {
		return dest_via;
	}

	public void setDest_via(String dest_via) {
		this.dest_via = dest_via;
	}

	public String getDest_civico() {
		return dest_civico;
	}

	public void setDest_civico(String dest_civico) {
		this.dest_civico = dest_civico;
	}

	public String getDest_citta() {
		return dest_citta;
	}

	public void setDest_citta(String dest_citta) {
		this.dest_citta = dest_citta;
	}

	public String getDest_cap() {
		return dest_cap;
	}

	public void setDest_cap(String dest_cap) {
		this.dest_cap = dest_cap;
	}

	public String getDest_provincia() {
		return dest_provincia;
	}

	public void setDest_provincia(String dest_provincia) {
		this.dest_provincia = dest_provincia;
	}
	
	

}
