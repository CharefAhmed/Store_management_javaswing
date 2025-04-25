package Métier;

public class Utilisateur {
	private int idUtilisateur;
    private String nomUtilisateur;
    private String motDePasse;
    private String typeUtilisateur;
    
    public Utilisateur(int idUtilisateur, String nomUtilisateur, String motDePasse, String typeUtilisateur) {
        this.idUtilisateur = idUtilisateur;
        this.nomUtilisateur = nomUtilisateur;
        this.motDePasse = motDePasse;
        this.typeUtilisateur = typeUtilisateur;
    }
    public int getIdUtilisateur() {
        return idUtilisateur;
    }
    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
    public String getNomUtilisateur() {
        return nomUtilisateur;
    }
    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }
    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getTypeUtilisateur() {
        return typeUtilisateur;
    }
    public void setTypeUtilisateur(String typeUtilisateur) {
        this.typeUtilisateur = typeUtilisateur;
    }
}
