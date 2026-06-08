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
    

	
    public OrdineBean() {
    }

    public int getIdOrdine() { return idOrdine; }
    public void setIdOrdine(int idOrdine) { this.idOrdine = idOrdine; }

    public int getIdUtente() { return idUtente; }
    public void setIdUtente(int idUtente) { this.idUtente = idUtente; }

    public Timestamp getDataOrdine() { return dataOrdine; }
    public void setDataOrdine(Timestamp dataOrdine) { this.dataOrdine = dataOrdine; }

    public Double getTotaleOrdine() { return totaleOrdine; }
    public void setTotaleOrdine(Double totaleOrdine) { this.totaleOrdine = totaleOrdine; }

    public String getStatoOrdine() { return statoOrdine; }
    public void setStatoOrdine(String statoOrdine) { this.statoOrdine = statoOrdine; }

    public Double getCostoSpedizione() { return costoSpedizione; }
    public void setCostoSpedizione(Double costoSpedizione) { this.costoSpedizione = costoSpedizione; }
}