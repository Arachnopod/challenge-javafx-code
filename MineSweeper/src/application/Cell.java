package application;

import java.util.Random;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.shape.Circle;

public class Cell implements EventHandler<MouseEvent> {
	private Rectangle cell;
	private Circle boom;
	private Text text;
//	private Image image; //Add later
	
	private boolean isBoom;
	private boolean isSeen;
	private boolean isFlag;
	private boolean isText;
	private boolean gameOver;
	
	private int x;
	private int y;
	private double size;
	
	public Cell(double size, int x, int y) {
		this.x = x;
		this.y = y;
		this.size = size;
		isSeen = false;
		isFlag = false;
		isText = false;
		gameOver = false;
		
		
		//Ball Randomizer
		Random r = new Random();
		if (r.nextInt(6) > 4) {
			isBoom = true;
		} else {
			isBoom = false;
		}
		
		//Create the Cell
		cell = new Rectangle(size, size);
		cell.setTranslateX(x*size);
		cell.setTranslateY(y*size);
		cell.setFill(Color.GRAY);
		cell.setStroke(Color.BLACK);
		cell.setStrokeWidth(1);
		cell.setOnMouseClicked(this);
		cell.setId("cell");
		cell.setVisible(true);
		
		//Create the boom
		boom = new Circle(size/2/2);
		boom.setTranslateX((x*size)+(size/2));
		boom.setTranslateY((y*size)+(size/2));
		boom.setFill(Color.BLACK);
		boom.setVisible(false);
		boom.setId("boom");
		
		text = new Text("");
		text.setTranslateX((x*size)+(size/2));
		text.setTranslateY((y*size)+(size/2));	
		text.setFill(Color.WHITE);
		text.setStroke(Color.BLACK);
		text.setStrokeWidth(1);
		text.setFont(Font.font("Verdana", FontWeight.BOLD, size/2));
		text.setVisible(false);
		text.setId("text");
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Rectangle getCell() {
		return cell;
	}
	
	public Circle getBoom() {
		return boom;
	}
	
	public Text getText() {
		return text;
	}
	
	public boolean isBoom() {
		return isBoom;
	}
	
	public boolean isSeen() {
		return isSeen;
	}
	
	public boolean isFlag() {
		return isFlag;
	}
	
	public boolean isText() {
		return isText;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	public void setGameOver() {
		gameOver = true;
	}
	
	public void setIsText() {
		isText = true;
	}
	
	public void setSeen() {
		isSeen = true;
	}

	public double getSize() {
		return size;
	}
	
	@Override
	public void handle(MouseEvent e) {
		if (!(isSeen) && !gameOver) {
			if (e.getButton() == MouseButton.PRIMARY) {
				isSeen = true;
				isFlag = false;
				cell.setId("cellSeen");
				if (text.getText().length() != 0) {
					text.setVisible(true);
				}
				
				if (isBoom) {
					boom.setVisible(true);
					cell.setFill(Color.INDIANRED);
					gameOver = true;
				} else {
					cell.setFill(Color.LIGHTGRAY);
				}
			} else if (e.getButton() == MouseButton.SECONDARY) {
				if (!(isFlag)) {
					System.out.println("Flag has been set on cell x:"+x+" y:"+y);
					cell.setFill(Color.INDIANRED);
					isFlag = true;
				} else {
					System.out.println("Flag has been un-set on cell x:"+x+" y:"+y);
					cell.setFill(Color.GRAY);
					isFlag = false;
				}
			}
			
		}
	}
}