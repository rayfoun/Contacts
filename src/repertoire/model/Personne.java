package repertoire.model;

public class Personne {
	//Attributs 
	private String nom;
	private String prenom;

	public Personne(String nom,String prenom) {
		// TODO Auto-generated constructor stub
		this.nom = nom;
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@Override
	public String toString() {
		return nom + " " + prenom;
	}
	

}
