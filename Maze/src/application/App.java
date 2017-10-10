package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {
	private Display display = new Display(20, 20, 40);
	
	public static void main(String[] args) {
		launch(args); //Start app
	}

	@Override
	public void start(Stage stage) throws Exception {
		Scene scene = new Scene(display.getRoot());
		stage.setResizable(false);
		stage.setScene(scene);
		stage.setTitle("Maze : By Kamil Bolka");
		stage.show();
		play(); //Start
		
//		//Testing
//		display.moveBottom(display.getCell(3, 1), display.getCell(3, 2));
//		display.moveRight(display.getCell(3, 2), display.getCell(4, 2));
//		display.moveTop(display.getCell(4, 2), display.getCell(4, 1));
//		display.moveLeft(display.getCell(4, 1), display.getCell(3, 1));
	}
	
	public void play() {		
		//This timeline will create the game		
		Timeline createMaze = new Timeline(new KeyFrame(Duration.millis(100), new GenerateMazeEvent(display, 0, 0)));
		createMaze.setCycleCount(Timeline.INDEFINITE);
		createMaze.play();
	}
}
