package Métier;

public class Article {
    private int idArticle;
    private String designation;
    private String categorie;
    private int quantite;
    private double prixUnitaire;
    private String etatArt;
    
    public Article(int idArticle, String designation, String categorie, int quantite, double prixUnitaire,String etatArt) {
        this.idArticle = idArticle;
        this.designation = designation;
        this.categorie = categorie;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.etatArt=etatArt;
    }
    public Article(String designation, String categorie, int quantite, double prixUnitaire,String etatArt) {
        this.designation = designation;
        this.categorie = categorie;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.etatArt=etatArt;
    }
    
    

	public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
    public String getEtatArt() {
    	return etatArt;
    }
    public void setEtatArt(String EtatArt) {
    	this.etatArt=EtatArt;
    }
}

