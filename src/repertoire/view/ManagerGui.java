package repertoire.view;

import java.util.Optional;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import repertoire.model.Model;


public class ManagerGui {

	
	// Champs

	private Stage			stage;
	private Scene			scene;
    
    private Pane			pane;
    private Controller		controller;
    private Model			model;
	
	
	// Initialisations
	
	public void launch() {
		ApplicationJavaFX.launch(this);
	}
	
	
	private void start( Stage stage ) throws Exception {
	
			// Configure la scene
			FXMLLoader loader = new FXMLLoader(getClass().getResource( "View.fxml" ));
			pane = loader.load(); 
			controller = loader.getController();
			controller.setManagerGui(this);
			model = new Model( controller );
			controller.setModel(model);

			scene = new Scene( pane );
			
			
			// Configure le stage
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.sizeToScene();
			stage.setMinHeight(300);
			stage.setMinWidth(400);
			stage.setTitle("Répertoire");
//			stage.getIcons().add(new Image(getClass().getResource("icone.png").toExternalForm()));
			this.stage = stage;
			
			// Affiche le stage
			stage.show();
	}
	
	
	// Actions
	
	public void close() {
		stage.close();
	}
	
	
	// Actions générales
	
	public void execTask( Runnable runnable ) {

		final EventHandler<InputEvent> inputEventConsumer = (event) ->	event.consume() ;  
		stage.addEventFilter(InputEvent.ANY, inputEventConsumer);  
		stage.getScene().setCursor( Cursor.WAIT );  

		Timeline timeline = new Timeline(  new KeyFrame(
	        Duration.ONE,
            new EventHandler<ActionEvent>() {  
	            @Override  
	            public void handle(ActionEvent event) {
					runnable.run();
		            stage.removeEventFilter(InputEvent.ANY, inputEventConsumer);  
		            stage.getScene().setCursor(Cursor.DEFAULT);  
	              }  
	         }));  
		timeline.play();  

	}
	
	public void afficherMessage( String message ) {
		final Alert alert = new Alert(Alert.AlertType.INFORMATION);  
		alert.initOwner(stage); 
		alert.setHeaderText(message); 
		alert.showAndWait(); 
	}

	public void afficherErreur( Throwable exception ) {
		afficherErreur( null, exception );
	}

	public void afficherErreur( String message ) {
		afficherErreur( message, null );
	}

	public void afficherErreur( String message, Throwable exception ) {
		
		if (message == null ) {
			message = "Ecec du traitement demandé.";
		}
		
		final Alert alert = new Alert(Alert.AlertType.ERROR);  
		alert.initOwner(stage); 
		alert.setHeaderText( message ); 
		alert.showAndWait(); 
	}
	

	public boolean demanderConfirmation( String message ) {
		final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);  
		alert.initOwner( stage ); 
		alert.setHeaderText( message); 
		final Optional<ButtonType> result = alert.showAndWait(); 
		return result.get() == ButtonType.OK;
	}
	
	
	// Classes auxiliaires
	

	public static class ApplicationJavaFX extends Application {
		
		private static ManagerGui	managerGui;
		
		public static void launch( ManagerGui managerGui ) {
			ApplicationJavaFX.managerGui = managerGui;
			launch();
		}
		
		@Override
		public void start(Stage stage) throws Exception {
			managerGui.start(stage);
		}
	}	
	
	
	public static interface RunnableWithException {
		void run() throws Exception;
	}
	

}
