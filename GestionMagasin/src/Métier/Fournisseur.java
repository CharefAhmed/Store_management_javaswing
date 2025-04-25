package Métier;

public class Fournisseur {
    private int idFournisseur;
    private String nomFournisseur;
    private String adresseFournisseur;
    private String telephoneFournisseur;
    

    public Fournisseur(int idFournisseur, String nomFournisseur, String adresseFournisseur, String telephoneFournisseur) {
        this.idFournisseur = idFournisseur;
        this.nomFournisseur = nomFournisseur;
        this.adresseFournisseur = adresseFournisseur;
        this.telephoneFournisseur = telephoneFournisseur;
    }
    public Fournisseur(String nomFournisseur, String adresseFournisseur, String telephoneFournisseur) {
        this.nomFournisseur = nomFournisseur;
        this.adresseFournisseur = adresseFournisseur;
        this.telephoneFournisseur = telephoneFournisseur;
    }
    

    public int getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(int idFournisseur) {
        this.idFournisseur = idFournisseur;
    }

    public String getNomFournisseur() {
        return nomFournisseur;
    }

    public void setNomFournisseur(String nomFournisseur) {
        this.nomFournisseur = nomFournisseur;
    }

    public String getAdresseFournisseur() {
        return adresseFournisseur;
    }

    public void setAdresseFournisseur(String adresseFournisseur) {
        this.adresseFournisseur = adresseFournisseur;
    }

    public String getTelephoneFournisseur() {
        return telephoneFournisseur;
    }

    public void setTelephoneFournisseur(String telephoneFournisseur) {
        this.telephoneFournisseur = telephoneFournisseur;
    }
}

