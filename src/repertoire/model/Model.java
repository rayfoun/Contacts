package repertoire.model;

import java.util.ArrayList;

import repertoire.view.Controller;


public class Model {

	
	// champs
	
	private Controller controller;
	private ArrayList<Personne> personnes = new ArrayList<Personne>();
	private ArrayList<Adresse> adresses = new ArrayList<Adresse>();
	
	// Constructeur
	
	public Model( Controller controller ) {
		this.controller = controller;
		Personne p1 = new Personne("ADAMS","John");
		Personne p2 = new Personne("DUPONT","Jean");
		Personne p3 = new Personne("SUAREZ","Maria");
		personnes.add(p1);
		personnes.add(p2);
		personnes.add(p3);
		Adresse a1 = new Adresse("31 Rue Picasso","31000","Toulouse");
		Adresse a2 = new Adresse("33 Rue Molière","33000","Bordeaux");
		Adresse a3 = new Adresse("87 Place Churchill","87000","Limoges");
		adresses.add(a1);
		adresses.add(a2);
		adresses.add(a3);
		this.controller.afficherPersonnes(personnes);
		this.controller.afficherAdresses(adresses);
	}
	
	public void traiterSelectionPersonne( Personne personne ) {
		if(personne!=null) {
			controller.afficherPersonne(personne.getNom(), personne.getPrenom());
		}
		else {
			controller.afficherPersonne(null, null);
		}
	}
	
	public void modifierPersonne( Personne personne, String nom, String prenom ) {
		personne.setNom(nom);
		personne.setPrenom(prenom);
		controller.afficherPersonnes(personnes);
	}

	public void creerPersonne( String nom, String prenom ) {
		personnes.add(new Personne(nom,prenom));
		controller.afficherPersonnes(personnes);
	}
	
	public void supprimerPersonne( Personne personne ) {
		personnes.remove(personne);
		controller.afficherPersonnes(personnes);
		controller.afficherPersonne(null, null);
	}
	
	//Adresses
	public void traiterSelectionAdresse( Adresse adresse ) {
		if(adresse!=null) {
			controller.afficherAdresse(adresse.getRue(), adresse.getCodePostal(), adresse.getVille());
		}
		else {
			controller.afficherAdresse(null, null, null);
		}
	}
	
	public void modifierAdresse( Adresse adresse, String rue, String codePostal, String ville ) {
		adresse.setRue(rue);
		adresse.setCodePostal(codePostal);
		adresse.setVille(ville);
		controller.afficherAdresses(adresses);
	}
	
	public void creerAdresse( String rue, String codePostal, String ville ) {
		adresses.add(new Adresse(rue,codePostal,ville));
		controller.afficherAdresses(adresses);
	}
	
	public void supprimerAdresse( Adresse adresse ) {
		adresses.remove(adresse);
		controller.afficherAdresses(adresses);
		controller.afficherAdresse(null, null, null);
	}
	// Actions
}
