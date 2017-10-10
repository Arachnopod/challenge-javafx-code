package application;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Display implements EventHandler<MouseEvent> {
	private Cell[][] cells;
	private Pane root = new Pane();
	private Text amountOfBooms;
	private Text amountOfFlags;
	private Text message;	
	
	public Display(int windowSize, double cellSize) {	
		amountOfBooms = new Text(80, windowSize*cellSize+20, "BOOMS: ");
		amountOfBooms.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		amountOfBooms.setFill(Color.WHITE);
		
		amountOfFlags = new Text(230, windowSize*cellSize+20, "FLAGS: 0");
		amountOfFlags.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		amountOfFlags.setFill(Color.WHITE);
		
		message = new Text(360, windowSize*cellSize+20, "Click on any box to start");
		message.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		message.setFill(Color.WHITE);
		
		root.setPrefSize(windowSize*cellSize-10, windowSize*cellSize+15);
		root.setOnMouseClicked(this);
		
		cells = new Cell[windowSize][windowSize];
		
		//SetupAll Cells
		setupCells(cellSize);
		
		Button button = new Button("Reset Map");
		button.setTranslateX(0);
		button.setTranslateY(windowSize*cellSize);
		button.setOnMouseClicked(e -> {
			message.setText("Restart Successful");
			
			for (int x = 0; x < cells.length; x++) {
				for (int y = 0; y < cells[x].length; y++) {
					root.getChildren().remove(cells[x][y].getBoom());
					root.getChildren().remove(cells[x][y].getText());
					root.getChildren().remove(cells[x][y].getCell());					
				}
			}
			
			//SetupAll Cells
			setupCells(cellSize);
		});
		
		root.getChildren().addAll(button, amountOfBooms, amountOfFlags, message);
	}
	
	public Pane getRoot() {
		return root;
	}
	
	public void countNighbors(Cell c) {
		int count = 0;
		
		for (int xoff = -1; xoff <= 1; xoff++) {
			for (int yoff = -1; yoff <= 1; yoff++) {
				int x = c.getX() + xoff;
				int y = c.getY() + yoff;
				if (!(c.isBoom()) && x > -1 && x < cells.length && y > -1 && y < cells.length) {
					if (cells[x][y].isBoom()) {
						count++;
					}
				}
			}
		}
		
		if (count > 0) {
			c.getText().setText(""+count);		
			c.setIsText();
		}
	}

	@Override
	public void handle(MouseEvent event) {		
		//update GameOver
		boolean gameOver = false;
		for (int x = 0; x < cells.length; x++) {
			for (int y = 0; y < cells[x].length; y++) {
				if (cells[x][y].isGameOver()) {
					gameOver = true;
				}
			}
		}
		if (gameOver) {
			for (int x = 0; x < cells.length; x++) {
				for (int y = 0; y < cells[x].length; y++) {
					cells[x][y].setGameOver();
				}
			}	
			message.setText("Fail, Click \"Restart Map\" to play");
		}
		
		//Count flags 
		int flagCount = 0;
		for (int x = 0; x < cells.length; x++) {
			for (int y = 0; y < cells[x].length; y++) {
				if (cells[x][y].isFlag()) {
					flagCount++;
				}
			}
		}
		amountOfFlags.setText("FLAGS: "+flagCount);
		
		//Update the grid by using length of the display
		for (int i = 0; i < cells.length; i++) { 
			for (int x = 0; x < cells.length; x++) {
				for (int y = 0; y < cells[x].length; y++) {
					if (cells[x][y].isSeen() && !(cells[x][y].isText()) && !(cells[x][y].isBoom())) {
						for (int xoff = -1; xoff <= 1; xoff++) {
							for (int yoff = -1; yoff <= 1; yoff++) {
								int movex = x + xoff;
								int movey = y + yoff;
								if (movex > -1 && movex < cells.length && movey > -1 && movey < cells[0].length) {
									cells[movex][movey].setSeen();
									cells[movex][movey].getCell().setId("cellSeen");
									cells[movex][movey].getText().setVisible(true);
									cells[movex][movey].getCell().setFill(Color.LIGHTGRAY);
								}
							}
						}
					}
				}
			}
		}
		
		//Check for win
		int boomsCount = 0;
		for (int x = 0; x < cells.length; x++) {
			for (int y = 0; y < cells[x].length; y++) {
				if (cells[x][y].isBoom()) {
					boomsCount++;
				}
			}
		}
		
		for (int x = 0; x < cells.length; x++) {
			for (int y = 0; y < cells[x].length; y++) {
				if (cells[x][y].isBoom() == cells[x][y].isFlag()) {
					boomsCount--;
				}
			}
		}
		
		if (boomsCount == 0) {
			message.setText("YOU WON!!!");
			for (int x = 0; x < cells.length; x++) {
				for (int y = 0; y < cells[x].length; y++) {
					cells[x][y].setGameOver();
				}
			}
		}
	}
	
	private void setupCells(double cellSize) {
		//Setup cells
		for (int x = 0; x < cells.length; x++) {
			for (int y = 0; y < cells[x].length; y++) {
				Cell c = new Cell(cellSize, x, y);
				cells[x][y] = c;
				root.getChildren().addAll(c.getCell(), c.getBoom(), c.getText());				
			}
		}
		
		//Find neighbors count && Find all booms
		for (int x = 0; x < cells.length; x++) {
			for (int y = 0; y < cells[x].length; y++) {
				countNighbors(cells[x][y]);
				if (!(cells[x][y].isBoom())) {
					cells[x][y].getBoom().setVisible(false);
				}
			}
		}
		
		//Find amount of booms
		int boomCount = 0;
		for (int x = 0; x < cells.length; x++) {
			for (int y = 0; y < cells[x].length; y++) {
				if (cells[x][y].isBoom()) {
					boomCount++;
				}
			}
		}
		amountOfBooms.setText("BOOMS: "+boomCount);
	}
}
