package application;

import javafx.scene.layout.Pane;

public class Display {
	private Cell[][] cells;
	private Pane root;
	
	public Display(int width, int height, double cellSize) {
		cells = new Cell[width][height];
		
		root = new Pane();
		root.setPrefSize(width*cellSize, height*cellSize);
		
		//Create all cells and add them to the array
		for (int x = 0; x < cells.length; x++) {
			for (int y = 0; y < cells.length; y++) {
				Cell c = new Cell(cellSize, x*cellSize, y*cellSize);
				cells[x][y] = c;
				root.getChildren().add(c);
			}
		}
	}

	public Display(int size, double cellSize) {
		this(size, size, cellSize);
	}
		
	public void moveBottom(Cell moveFrom, Cell moveTo) {
		moveFrom.getBottomWall().setVisible(false);
		moveTo.getTopWall().setVisible(false);
	}
	
	public void moveTop(Cell moveFrom, Cell moveTo) {
		moveFrom.getTopWall().setVisible(false);
		moveTo.getBottomWall().setVisible(false);
	}
	
	public void moveLeft(Cell moveFrom, Cell moveTo) {
		moveFrom.getRightWall().setVisible(false);
		moveTo.getLeftWall().setVisible(false);
	}
	
	public void moveRight(Cell moveFrom, Cell moveTo) {
		moveFrom.getLeftWall().setVisible(false);
		moveTo.getRightWall().setVisible(false);
	}
	
	public Cell getCell(int x, int y) {
		return cells[x][y];
	}
	
	public Pane getRoot() {
		return root;
	}
	
	public int getWidth() {
		return cells.length;
	}
	
	public int getLength() {
		return cells[0].length;
	}
	
	public boolean allCellVisited() {	
		// Go through 2D array and check if all cells are visited
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				if (!cells[i][j].isVisited()) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	
}
