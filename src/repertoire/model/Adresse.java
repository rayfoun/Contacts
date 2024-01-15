package repertoire.model;

public class Adresse {
	//Attributs
	private String rue;
	private String codePostal;
	private String ville;
	
	public Adresse(String rue,String codePostal,String ville) {
		// TODO Auto-generated constructor stub
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	@Override
	public String toString() {
		return rue + " - " + codePostal + " " + ville;
	}
	

}
