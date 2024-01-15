package repertoire.view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

@SuppressWarnings( { "rawtypes", "unchecked" })
public class Controller {

	
	// Composants visuels de la vue
	
	@FXML
	private ListView		listViewPersonnes;
	@FXML
	private TextField		textFieldNom;
	@FXML
	private TextField		textFieldPrenom;
	@FXML
	private Button			buttonPersonneModifier;
	@FXML
	private Button			buttonPersonneSupprimer;
	@FXML
	private HBox			hboxPersonneModif;	
	@FXML
	private HBox			hboxPersonneAjout;	
	
	@FXML
	private ListView		listViewAdresses;
	@FXML
	private TextField		textFieldRue;
	@FXML
	private TextField		textFieldCodePostal;
	@FXML
	private TextField		textFieldVille;
	@FXML
	private Button			buttonAdresseAjouter;
	@FXML
	private Button			buttonAdresseModifier;
	@FXML
	private Button			buttonAdresseSupprimer;
	@FXML
	private HBox			hboxAdresseModif;	
	@FXML
	private HBox			hboxAdresseAjout;	

	@FXML
	private Button			buttonAttacher;
	@FXML
	private Button			buttonDetacher;

	
	// Objets observables
	
	private final ObservableList	personnes = FXCollections.observableArrayList();
	private final ObservableList	adresses = FXCollections.observableArrayList();
	
	private final ObservableList	personnesColorees = FXCollections.observableArrayList();
	private final ObservableList	adressesColorees = FXCollections.observableArrayList();
	
	
	// Autres champs
	
	private boolean		flagDebug = false;
	
	private ManagerGui	managerGui;
	private Object		model;
	
	private boolean 	flagPersonneAjout = false;
	private boolean		flagPersonneModif = false;
	private boolean 	flagAdresseAjout = false;
	private boolean		flagAdresseModif = false;

	private Class 		classePersonne;
	private Class 		classeAdresse;
	
	// constructeur
	
	public Controller() {
		
		try {
			classePersonne = Class.forName( "repertoire.model.Personne" );
			classeAdresse = Class.forName( "repertoire.model.Adresse" );
		} catch (ClassNotFoundException e) {
			if (flagDebug) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	// Actions
	
	@FXML
	private void doPersonneAjouter() {
		listViewPersonnes.getSelectionModel().clearSelection();
		colorerAdresse(null);
		flagPersonneAjout = true;
		afficherPersonne(null, null );
		textFieldNom.requestFocus();
	}
	
	@FXML
	private void doPersonneAnnuler() {
		flagPersonneAjout = false;
		afficherPersonne(null, null );
		listViewPersonnes.requestFocus();
	}
	
	@FXML
	private void doPersonneValider() {
		try {
			Method method = model.getClass().getMethod( "creerPersonne", String.class, String.class );
			method.invoke( model, new Object[] {
					textFieldNom.getText(),
					textFieldPrenom.getText(),
			} );
		} catch ( NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
			if ( flagDebug ) {
				e.printStackTrace();
			}
		} catch ( InvocationTargetException e) {
			e.getCause().printStackTrace();
		}
		flagPersonneAjout = false;
		configurerAffichage();
	}
	
	@FXML
	private void doPersonneModifier() {
		try {
			Method method = model.getClass().getMethod( "modifierPersonne", classePersonne, String.class, String.class );
			method.invoke( model, new Object[] {
					listViewPersonnes.getSelectionModel().getSelectedItem(),
					textFieldNom.getText(),
					textFieldPrenom.getText(),
			} );
		} catch ( NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
			if ( flagDebug ) {
				e.printStackTrace();
			}
		} catch ( InvocationTargetException e) {
			e.getCause().printStackTrace();
		}
	}
	
	@FXML
	private void doPersonneSupprimer() {
		try {
			Method method = model.getClass().getMethod( "supprimerPersonne", classePersonne );
			method.invoke( model, new Object[] {
					listViewPersonnes.getSelectionModel().getSelectedItem(),
			} );
		} catch ( NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
			if ( flagDebug ) {
				e.printStackTrace();
			}
		} catch ( InvocationTargetException e) {
			e.getCause().printStackTrace();
		}
	}

	
	// Actions sur Adresse
	
	@FXML
	private void doAdresseAjouter() {
		listViewAdresses.getSelectionModel().clearSelection();
		colorerPersonne(null);
		flagAdresseAjout = true;
		afficherAdresse( null, null, null );
		textFieldRue.requestFocus();
	}
	
	@FXML
	private void doAdresseAnnuler() {
		flagAdresseAjout = false;
		afficherAdresse( null, null, null );
		listViewAdresses.requestFocus();
	}
	
	@FXML
	private void doAdresseValider() {
		try {
			Method method = model.getClass().getMethod( "creerAdresse", String.class, String.class, String.class );
			method.invoke( model, new Object[] {
					textFieldRue.getText(),
					textFieldCodePostal.getText(),
					textFieldVille.getText(),
			} );
		} catch ( NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
			if ( flagDebug ) {
				e.printStackTrace();
			}
		} catch ( InvocationTargetException e) {
			e.getCause().printStackTrace();
		}
		flagAdresseAjout = false;
		configurerAffichage();
	}
	
	@FXML
	private void doAdresseModifier() {
		try {
			Method method = model.getClass().getMethod( "modifierAdresse", classeAdresse, String.class, String.class, String.class );
			method.invoke( model, new Object[] {
					listViewAdresses.getSelectionModel().getSelectedItem(),
					textFieldRue.getText(),
					textFieldCodePostal.getText(),
					textFieldVille.getText(),
			} );
		} catch ( NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
			if ( flagDebug ) {
				e.printStackTrace();
			}
		} catch ( InvocationTargetException e) {
			e.getCause().printStackTrace();
		}
	}
	
	@FXML
	private void doAdresseSupprimer() {
		try {
			Method method = model.getClass().getMethod( "supprimerAdresse", classeAdresse );
			method.invoke( model, new Object[] {
					listViewAdresses.getSelectionModel().getSelectedItem(),
			} );
		} catch ( NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
			if ( flagDebug ) {
				e.printStackTrace();
			}
		} catch ( InvocationTargetException e) {
			e.getCause().printStackTrace();
		}
	}
	
	
	// Actions attahcer et détacher
	
	@FXML
	private void doAttacher() {
		try {
			Method method = model.getClass().getMethod( "attacher", classePersonne, classeAdresse );
			method.invoke( model, new Object[] {
					listViewPersonnes.getSelectionModel().getSelectedItem(),
					listViewAdresses.getSelectionModel().getSelectedItem(),			
			} );
		} catch ( NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
			if ( flagDebug ) {
				e.printStackTrace();
			}
		} catch ( InvocationTargetException e) {
			if ( e.getCause().getClass().equals( Exception.class ) ) {
				managerGui.afficherErreur( e.getCause().getMessage() );
			} else {
				e.getCause().printStackTrace();
			}
		}
	}
	
	
	@FXML
	private void doDetacher() {
		try {
			Method method = model.getClass().getMethod( "detacher", classePersonne, classeAdresse );
			method.invoke( model, new Object[] {
					listViewPersonnes.getSelectionModel().getSelectedItem(),
					listViewAdresses.getSelectionModel().getSelectedItem(),			
			} );
		} catch ( NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
			if ( flagDebug ) {
				e.printStackTrace();
			}
		} catch ( InvocationTargetException e) {
			if ( e.getCause().getClass().equals( Exception.class ) ) {
				managerGui.afficherErreur( e.getCause().getMessage() );
			} else {
				e.getCause().printStackTrace();
			}
		}
	}
	
	
	@FXML
	private void doTest1() {
		try {
			Method method = model.getClass().getMethod( "test1" );
			method.invoke( model, new Object[] {} );
		} catch ( NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
			if ( flagDebug ) {
				e.printStackTrace();
			}
		} catch ( InvocationTargetException e) {
			e.getCause().printStackTrace();
		}
	}
	
	@FXML
	private void doTest2() {
		try {
			Method method = model.getClass().getMethod( "test2" );
			method.invoke( model, new Object[] {} );
		} catch ( NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
			if ( flagDebug ) {
				e.printStackTrace();
			}
		} catch ( InvocationTargetException e) {
			e.getCause().printStackTrace();
		}
	}
	
	
	// Services
	
	public void afficherPersonnes( ArrayList personnes ) {
		this.personnes.clear();
		if ( personnes != null ) {
			for ( Object personne : personnes ) {
				this.personnes.add( personne );
			}
		}
	}
	
	public void afficherAdresses( ArrayList adresses ) {
		this.adresses.clear();
		if ( adresses != null ) {
			for ( Object adresse : adresses ) {
				this.adresses.add( adresse );
			}
		}
	}
	
	
	public void afficherPersonne( String nom, String prenom ) {
		if ( nom == null || prenom == null ) {
			textFieldNom.setText( "" );
			textFieldPrenom.setText( "" );
			flagPersonneModif = false;
		} else {
			textFieldNom.setText( nom );
			textFieldPrenom.setText( prenom );
			flagPersonneModif = true;
		}
		configurerAffichage();
	}
	
	
	public void afficherAdresse( String rue, String codePostal, String ville ) {
		if ( rue == null || codePostal == null || ville == null ) {
			textFieldRue.setText( "" );
			textFieldCodePostal.setText( "" );
			textFieldVille.setText( "" );
			flagAdresseModif = false;
		} else {
			textFieldRue.setText( rue );
			textFieldCodePostal.setText( codePostal );
			textFieldVille.setText( ville );
			flagAdresseModif = true;
		}
		configurerAffichage();
	}

	
	public void selectionnerPersonne( Object personne ) {
		listViewPersonnes.getSelectionModel().clearSelection();
		listViewPersonnes.getSelectionModel().select( personne );
		listViewPersonnes.scrollTo(personne);
		listViewPersonnes.requestFocus();
	}
	
	public void selectionnerAdresse( Object adresse ) {
		listViewAdresses.getSelectionModel().clearSelection();
		listViewAdresses.getSelectionModel().select( adresse );
		listViewAdresses.scrollTo(adresse);
		listViewAdresses.requestFocus();
	}
	
	
	public void colorerPersonnes( ArrayList personnes ) {
		this.personnesColorees.clear();
		if ( personnes != null ) {
			for ( Object personne : personnes ) {
				this.personnesColorees.add( personne );
			}
		}
	}
	
	public void colorerAdresses( ArrayList adresses ) {
		this.adressesColorees.clear();
		if ( adresses != null ) {
			for ( Object adresse : adresses ) {
				this.adressesColorees.add( adresse );
			}
		}
	}
	
	
	public void colorerPersonne( Object personne ) {
		this.personnesColorees.clear();
		if ( personne != null ) {
			this.personnesColorees.add( personne );
		}
	}
	
	public void colorerAdresse( Object adresse ) {
		this.adressesColorees.clear();
		if ( adresse != null ) {
			this.adressesColorees.add( adresse );
		}
	}
	
	
	// Initialisaiton

	public void initialize() {
		
		listViewPersonnes.setItems( personnes );
		listViewAdresses.setItems( adresses );
		
		// Listener séleciton d'une personne
		listViewPersonnes.getSelectionModel().getSelectedItems().addListener(
				(ListChangeListener<Object>) ( c ) -> traiterSelectionPersonne()
		);
		listViewPersonnes.focusedProperty().addListener(
				(ChangeListener<Boolean>) ( obs, oldValue, newValue ) -> {
					if ( newValue.booleanValue() ) {
						traiterSelectionPersonne();
					}
				}
		);
		
		// Listener séleciton d'une adresse
		listViewAdresses.getSelectionModel().getSelectedItems().addListener(
				(ListChangeListener<Object>) ( c ) -> traiterSelectionAdresse()
		);
		listViewAdresses.focusedProperty().addListener(
				(ChangeListener<Boolean>) ( obs, oldValue, newValue ) -> {
					if ( newValue.booleanValue() ) {
						 traiterSelectionAdresse();
					}
				}
		);

		
		// Coloration des personnes
		listViewPersonnes.setCellFactory(list -> new ListCell() {
		    BooleanBinding binding ;
		    {
		        binding = Bindings.createBooleanBinding(
		            () -> personnesColorees.contains( getItem() ),
		            itemProperty(), personnesColorees
		        );
		        binding.addListener((obs, oldValue, newValue) -> {
		            if (newValue) {
		                setStyle("-fx-text-fill:blue;-fx-font-weight:bold;");
		            } else {
		                setStyle("");
		            }
		        });
		    }
		    @Override
		    protected void updateItem(Object item, boolean empty) {
		        super.updateItem(item, empty); 
		        setText(empty ? null : item.toString());
		    }
		});
		

		
		// Coloration des adresses
		listViewAdresses.setCellFactory(list -> new ListCell() {
		    BooleanBinding binding ;
		    {
		        binding = Bindings.createBooleanBinding(
		            () -> adressesColorees.contains( getItem() ),
		            itemProperty(), adressesColorees
		        );
		        binding.addListener((obs, oldValue, newValue) -> {
		            if (newValue) {
		                setStyle("-fx-text-fill:blue;-fx-font-weight:bold;");
		            } else {
		                setStyle("");
		            }
		        });
		    }
		    @Override
		    protected void updateItem(Object item, boolean empty) {
		        super.updateItem(item, empty); 
		        setText(empty ? null : item.toString());
		    }
		});
		
		
		configurerAffichage();
	}
	
	
	public void setManagerGui(ManagerGui managerGui) {
		this.managerGui = managerGui;
	}
	
	public void setModel( Object model) {
		this.model = model;
	}
	
	
	
	// Méthodes auxiliaires
	
	private void configurerAffichage() {
		
		// Configuration la plus courante
		hboxPersonneModif.setVisible(true);
		hboxPersonneAjout.setVisible(false);
		buttonPersonneModifier.setDisable(true);
		buttonPersonneSupprimer.setDisable(true);
		textFieldNom.setDisable(false);
		textFieldPrenom.setDisable(false);

		// Personne
		if ( flagPersonneAjout ) {
			// Mode Ajout
			listViewPersonnes.getSelectionModel().clearSelection();
			hboxPersonneModif.setVisible(false);
			hboxPersonneAjout.setVisible(true);

		} else if ( flagPersonneModif ) {
			// Sélection
			buttonPersonneModifier.setDisable(false);
			buttonPersonneSupprimer.setDisable(false);
		
		} else {
			// Pas de séelction
			textFieldNom.setDisable(true);
			textFieldPrenom.setDisable(true);
		}

		// Adresse
		
		hboxAdresseModif.setVisible(true);
		hboxAdresseAjout.setVisible(false);
		buttonAdresseModifier.setDisable(true);
		buttonAdresseSupprimer.setDisable(true);
		textFieldRue.setDisable(false);
		textFieldCodePostal.setDisable(false);
		textFieldVille.setDisable(false);
		
		
		if ( flagAdresseAjout ) {
			// Mode Ajout
			listViewAdresses.getSelectionModel().clearSelection();
			hboxAdresseModif.setVisible(false);
			hboxAdresseAjout.setVisible(true);

		} else if ( flagAdresseModif ) {
			// Sélection
			buttonAdresseModifier.setDisable(false);
			buttonAdresseSupprimer.setDisable(false);

		} else {
			// Pas de séelction
			textFieldRue.setDisable(true);
			textFieldCodePostal.setDisable(true);
			textFieldVille.setDisable(true);
		}

		// Attacher & Détacher
		
		buttonAttacher.setDisable(true);
		buttonDetacher.setDisable(true);
		
		if( flagPersonneModif && flagAdresseModif ) {
			buttonAttacher.setDisable(false);
			buttonDetacher.setDisable(false);
		}
	}
	
	
	private void traiterSelectionPersonne() {
		flagPersonneAjout = false;
		flagPersonneModif = false;
		colorerPersonnes(null);
		try {
			Method method = model.getClass().getMethod( "traiterSelectionPersonne", classePersonne );
			method.invoke( model, new Object[] {
					listViewPersonnes.getSelectionModel().getSelectedItem(),
			} );
		} catch ( NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
			if ( flagDebug ) {
				e.printStackTrace();
			}
		} catch ( InvocationTargetException e) {
			e.getCause().printStackTrace();
		}
	
	}		

	private void traiterSelectionAdresse() {
		flagAdresseAjout = false;
		flagAdresseModif = false;
		colorerAdresses(null);
		try {
			Method method = model.getClass().getMethod( "traiterSelectionAdresse", classeAdresse );
			method.invoke( model, new Object[] {
					listViewAdresses.getSelectionModel().getSelectedItem(),
			} );
		} catch ( NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
			if ( flagDebug ) {
				e.printStackTrace();
			}
		} catch ( InvocationTargetException e) {
			e.getCause().printStackTrace();
		}
	}
	
	
}
