package Métier;

import java.util.Date;

public class BonAchat {
    private int idBonAchat;
    private int idFournisseur;
    private Date dateAchat;
    private double montantTotal;
    private int idArticle;
    
    public BonAchat(int idBonAchat, int idFournisseur,int idArticle, Date dateAchat, double montantTotal) {
        this.idBonAchat = idBonAchat;
        this.idFournisseur = idFournisseur;
        this.idArticle = idArticle;
        this.dateAchat = dateAchat;
        this.montantTotal = montantTotal;
    }
    public BonAchat(int idFournisseur,int idArticle, Date dateAchat, double montantTotal) {
        this.idFournisseur = idFournisseur;
        this.idArticle = idArticle;
        this.dateAchat = dateAchat;
        this.montantTotal = montantTotal;
    }
   public BonAchat(int idBonAchat, Date dateAchat, double montantTotal) {
	   this.idBonAchat = idBonAchat;
	   this.dateAchat = dateAchat;
	   this.montantTotal = montantTotal;
	}
	/*private String selectedFournisseur;

	public BonAchat(int idBonAchat, String selectedFournisseur, Date dateAchat, double montantTotal) {
		this.idBonAchat = idBonAchat;
        this.selectedFournisseur = selectedFournisseur;
        this.dateAchat = dateAchat;
        this.montantTotal = montantTotal;	}*/
	public int getIdBonAchat() {
        return idBonAchat;
    }

    public void setIdBonAchat(int idBonAchat) {
        this.idBonAchat = idBonAchat;
    }

    public int getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(int idFournisseur) {
        this.idFournisseur = idFournisseur;
    }
    public int getIdArticle() {
        return idArticle;
    }
    public void setIdArticle(int idArticle) {
        this.idArticle= idArticle;
    }

    public Date getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(Date dateAchat) {
        this.dateAchat = dateAchat;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }
}

