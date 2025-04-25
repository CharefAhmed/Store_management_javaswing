package Métier;

public class Client {
	 private int idClient;
	 private String nomClient;
	 private String adresseClient;
	 private String telephoneClient;
	 
	 public Client(int idClient, String nomClient, String adresseClient, String telephoneClient) {
	        this.idClient = idClient;
	        this.nomClient = nomClient;
	        this.adresseClient = adresseClient;
	        this.telephoneClient = telephoneClient;
	 }
	 public int getIdClient() {
	        return idClient;
	    }
	    public void setIdClient(int idClient) {
	        this.idClient = idClient;
	    }
	    public String getNomClient() {
	        return nomClient;
	    }
	    public void setNomClient(String nomClient) {
	        this.nomClient = nomClient;
	    }
	    public String getAdresseClient() {
	        return adresseClient;
	    }
	    public void setAdresseClient(String adresseClient) {
	        this.adresseClient = adresseClient;
	    }
	    public String getTelephoneClient() {
	        return telephoneClient;
	    }
	    public void setTelephoneClient(String telephoneClient) {
	        this.telephoneClient = telephoneClient;
	    }
}
