package Métier;

import java.util.Date;
import java.util.List;

public class Commande {
    private int idCommande;
    private int idClient;
    private int idArticle;
    private String designation;
    private int quantite;
    private Date dateCommande;
    private double montantTotal;
    

    

    public Commande(int idCommande, int idClient,int idArticle ,String designation ,int quantite , Date dateCommande, double montantTotal) {
        this.idCommande = idCommande;
        this.idClient = idClient;
        this.idArticle= idArticle;
    	this.designation = designation;
    	this.quantite = quantite;
        this.dateCommande = dateCommande;
        this.montantTotal = montantTotal;
    }
    

    public Commande(int idClient,int idArticle ,String designation ,int quantite ,Date dateCommande, double montantTotal) {
    	this.idClient = idClient;
    	this.idArticle= idArticle;
    	this.designation = designation;
    	this.quantite = quantite;
        this.dateCommande = dateCommande;
        this.montantTotal = montantTotal;
	}


	public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
    public int getIdArticle() {
        return idArticle;
    }
    public void setIdArticle(int idArticle) {
        this.idArticle= idArticle;
    }
    public String getDesignation() {
        return designation;
    }
    public void setDesignation(String designation) {
        this.designation= designation;
    }
    public int getQuantite() {
        return quantite;
    }
    public void setQuantite(int quantite) {
        this.quantite= quantite;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }
   
}

