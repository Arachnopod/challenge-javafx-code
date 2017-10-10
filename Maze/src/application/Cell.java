package application;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Cell extends StackPane {
	//Cell
	private boolean visited = false;
	
	//Rectangle
	private Rectangle cell;
	private Color mainColor;
	
	//Lines
	private Line topWall;
	private Line bottomWall;
	private Line rightWall;
	private Line leftWall;
	
	public Cell(double size, double x, double y, Color color) {
		mainColor = color;
		
		cell = new Rectangle(size, size);
		cell.setTranslateX(x);
		cell.setTranslateY(y);
		cell.setFill(color);
		
		//Create topWall
		topWall = new Line();
		topWall.setTranslateX(x);
		topWall.setTranslateY(y-(size/2));
		topWall.setStartX(0);
		topWall.setEndX(size);
		topWall.setFill(Color.BLACK);
		
		//Create bottomWall
		bottomWall = new Line();
		bottomWall.setTranslateX(x);
		bottomWall.setTranslateY(y+(size/2));
		bottomWall.setStartX(0);
		bottomWall.setEndX(size);
		bottomWall.setFill(Color.BLACK);
		
		//Create rightWall
		rightWall = new Line();
		rightWall.setTranslateX(x-(size/2));
		rightWall.setTranslateY(y);
		rightWall.setStartY(0);
		rightWall.setEndY(size);
		rightWall.setFill(Color.BLACK);
		
		//Create leftWall
		leftWall = new Line();
		leftWall.setTranslateX(x+(size/2));
		leftWall.setTranslateY(y);
		leftWall.setStartY(0);
		leftWall.setEndY(size);
		leftWall.setFill(Color.BLACK);
		
		getChildren().addAll(cell, topWall, bottomWall, rightWall, leftWall);
	}
	
	public Cell(double size, double x, double y) {
		this(size, x, y, Color.GRAY); //Default color
	}
		
	public Line getTopWall() {
		return topWall;
	}
	
	public Line getBottomWall() {
		return bottomWall;
	}
	
	public Line getRightWall() {
		return rightWall;
	}
	
	public Line getLeftWall() {
		return leftWall;
	}
	
	public void highLight() {
		cell.setFill(Color.DARKRED);
	}
	
	public void stopHighLight() {
		cell.setFill(mainColor);
	}
	
	public boolean isVisited() {
		return visited;
	}

	public void setVisited() {
		visited = true;
	}
	
	public void setVisitedColor() {
		cell.setFill(Color.INDIANRED);
	}

	public double getSize() {
		return cell.getWidth();
	}

	public double getX() {
		return cell.getTranslateX();
	}

	public void setX(double x) {
		cell.setTranslateX(x);
	}

	public double getY() {
		return cell.getTranslateY();
	}

	public void setY(double y) {
		cell.setTranslateY(y);
	}
}
