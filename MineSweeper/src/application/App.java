package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class App extends Application {
	public int windowSize = 20;
	public double cellSize = 40;


	
	@Override
	public void start(Stage stage) {
		Display display = new Display(windowSize, cellSize);
		
		stage.setTitle("MineSweeper : By Kamil Bolka");
		stage.setResizable(false);
		
		Scene scene = new Scene(display.getRoot());
		scene.getStylesheets().add("application/application.css");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
