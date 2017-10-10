package application;

import java.util.Stack;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class GenerateMazeEvent implements EventHandler<ActionEvent> {

	//Passed in display
	private Display display;
	
	//Start positions
	private int x;
	private int y;
	private int startX;
	private int startY;
		
	private Stack<Cell> stack = new Stack<Cell>();
	private Random random = new Random();
	
	public GenerateMazeEvent(Display display, int x, int y) {
		this.display = display;
		this.x = x;
		this.y = y;
		this.startX = x;
		this.startY = y;
		display.getCell(x, y).setVisited();
	}
	
	public GenerateMazeEvent(Display display) {
		this(display, 0, 0); //Default 0,0
	}
	
	@Override
	public void handle(ActionEvent event) {
		display.getCell(x, y).stopHighLight();
		
		boolean[] tryRoot = {false, false, false, false};

		int moveType = 0;
	
		int moveX = x;
		int moveY = y;
		
		boolean useStack = false;
		boolean valid = false;
		do {
			moveX = x;
			moveY = y;			
			moveType = random.nextInt(4);
			
			switch (moveType) {
				case 0:
					//move top
					moveY -= 1;
					tryRoot[0] = true;
					break;
				case 1:
					//move bottom
					moveY += 1;
					tryRoot[1] = true;
					break;
				case 2:
					//move right
					moveX += 1;
					tryRoot[2] = true;
					break;
				case 3:
					//move left
					moveX -= 1;
					tryRoot[3] = true;
					break;
			}
			
			if (moveX < 0 || moveX >= display.getWidth()) {
				valid = false;
			} else if (moveY < 0 || moveY >= display.getLength()) {
				valid = false;
			} else if (display.getCell(moveX, moveY).isVisited() == true) {
				valid = false;
			} else {
				valid = true;
			}	
			
			if (valid == false && tryRoot[0] == true && tryRoot[1] == true && tryRoot[2] == true && tryRoot[3] == true && stack.size() > 0) {
				Cell c = stack.pop();	
				moveX = (int)(c.getX()/c.getSize());
				moveY = (int)(c.getY()/c.getSize());
			    valid = true;
			    useStack = true;
			}
			
		} while (valid != true);
			
		if (useStack == false) {
			stack.push(display.getCell(x, y));
			move(moveX, moveY, moveType);
			display.getCell(moveX, moveY).setVisited();
		}
		
		display.getCell(moveX, moveY).highLight();
		display.getCell(x, y).setVisitedColor();
		
		System.out.println("Stack size: "+stack.size()); //Testing
		
		x = moveX;
		y = moveY;
		
		// Test for stop
		stopTest(moveX, moveY);
	}
	
	private void stopTest(int moveX, int moveY) {
		if (display.allCellVisited() && moveX == startX && moveY == startY) {
			System.exit(0); // Stop the program
		}
	}

	private void move(int moveX, int moveY, int moveType) {
		//This moves the selector
		switch (moveType) {
			case 0:
				//Move Top
				display.moveTop(display.getCell(x, y), display.getCell(moveX, moveY));
				break;
			case 1:
				//Move Bottom
				display.moveBottom(display.getCell(x, y), display.getCell(moveX, moveY));
				break;
			case 2:
				//Move right
				display.moveRight(display.getCell(x, y), display.getCell(moveX, moveY));
				break;
			case 3:
				//Move Left
				display.moveLeft(display.getCell(x, y), display.getCell(moveX, moveY));
				break;
		}		
	}
}
