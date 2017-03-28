import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JPanel;

public class MyPanel extends JPanel {
	private static final long serialVersionUID = 3426940946811133635L;
	private static final int GRID_X = 25;
	private static final int GRID_Y = 25;
	private static final int INNER_CELL_SIZE = 29;
	private static final int TOTAL_COLUMNS = 9;
	private static final int TOTAL_ROWS = 9; // Last row has only one cell
	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];
	public Color[][] letterColor = new Color[TOTAL_COLUMNS][TOTAL_ROWS];
	private Random generator = new Random();
	public int[][] bombs = new int[TOTAL_COLUMNS][TOTAL_ROWS];
	private int bomb = -1;
	public int numberOfBombs;
	public int[][] numbersAround = new int[TOTAL_COLUMNS][TOTAL_ROWS];
	public Color youWin = Color.LIGHT_GRAY;
	public Color youLose = Color.LIGHT_GRAY;
	
	private boolean canBeColored(int x, int y) {
		for (int i = -1; i < 2; i++) {
			if ((x + i) <= -1 || (x + i) >= TOTAL_COLUMNS) {
				return false;
			}
			for (int j = -1; j < 2; j++) {
				if ((y + j) <= -1 || (y + j) >=TOTAL_ROWS) {
					return false;
				}
			}
		}
		return (colorArray[x][y].equals(Color.WHITE));
	}

	private boolean checkForZero(int x, int y) {
		for (int i = y - 1; i < y + 2; i++) {
			for (int j = x - 1; j < x + 2; j++) {
				try {
					if (this.numbersAround[i][j] == 0) {
						return true;

					} else if (i == x && j == y) {
						continue;
					}
				} catch (IndexOutOfBoundsException ex) {
					continue;

				}
			}
		}
		return false;
	}
	public void chainOpener(int x, int y) {
		if (canBeColored(x, y) && this.numbersAround[x][y] == 0) {
			paintCell(x, y);
			chainOpener(x + 1, y);
			chainOpener(x - 1, y);
			chainOpener(x, y - 1);
			chainOpener(x, y + 1);
			chainOpener(x-1,y-1);
			chainOpener(x+1,y+1);
			chainOpener(x-1,y+1);
			chainOpener(x+1,y-1);
			
		} //l4k
		else if (!canBeColored(x, y) && this.numbersAround[x][y] == 0) {
			paintCell(x, y);			
		} 
		else if (this.numbersAround[x][y] > 0){
			paintCell(x, y);
		} 
		/*else if (this.numbersAround[x][y]>0  && checkForZero(x,y)){
			paintCell(x, y);
			
		}*/
		else{
			return;
		}
	}

	public void paintCell(int x, int y){
		Color newColor = Color.GRAY;
		colorArray[x][y] = newColor;
		if (numbersAround[x][y] == 0) {
			letterColor[x][y] = newColor;
		} 
		else {
			Color color2 = Color.BLUE;
			letterColor[x][y] = color2;
		}
		repaint();
		
	}
	public boolean winOrLose(int x, int y){
		boolean result= colorArray[x][y].equals(Color.RED); 
		boolean result1=haveBomb(x,y);
		return ( result && result1);
	}
	
	public int getBomb() {
		return bomb;
	}

	public int getTotalColumns() {
		return TOTAL_COLUMNS;
	}

	public int getTotalRows() {
		return TOTAL_ROWS;
	}

	public MyPanel() { // This is the constructor... this code runs first to
						// initialize
		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) { // Use of
																// "random" to
																// prevent
																// unwanted
																// Eclipse
																// warning
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}
		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) { // Use of "random"
																// to prevent
																// unwanted
																// Eclipse
																// warning
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}
		if (TOTAL_ROWS + (new Random()).nextInt(1) < 3) { // Use of "random" to
															// prevent unwanted
															// Eclipse warning
			throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		}
		//Arreglo Colores del grid
		for (int x = 0; x < TOTAL_COLUMNS; x++) { // The rest of the grid
			for (int y = 0; y < TOTAL_ROWS; y++) {
				colorArray[x][y] = Color.WHITE;
			}
		}
		//arreglo de colores de numeros
		for (int x = 0; x < TOTAL_COLUMNS; x++) { // The rest of the grid
			for (int y = 0; y < TOTAL_ROWS; y++) {
				letterColor[x][y] = Color.WHITE;
			}
		}
		// Crear panel de juego
		// crear las bombas
		this.numberOfBombs = this.generator.nextInt(10);
		
		// espacios com bombas tienen -1

		for (int a = 0; a < numberOfBombs; a++) {
			int x = this.generator.nextInt(TOTAL_COLUMNS);
			int y = this.generator.nextInt(TOTAL_ROWS);
			bombs[x][y] = this.bomb;
		
			//letterColor[x][y] = Color.BLACK;
			// CHANGE COLOR HERE!!!!!!!! ERASE
		}

		// inicializar los espacios basios sin bombas a 0
		for (int j = 0; j < TOTAL_ROWS; j++) {
			for (int i = 0; i < TOTAL_COLUMNS; i++)
				if (!(this.bombs[i][j] == this.bomb)) {
					this.bombs[i][j] = 0;
				}
		}
		// inicializar el arreglo de conteo de numeros a 0
		for (int j = 0; j < TOTAL_ROWS; j++) {
			for (int i = 0; i < TOTAL_COLUMNS; i++) {
				if (!(this.bombs[i][j] == this.bomb)) {
					this.numbersAround[i][j] = 0;
					
				}
			}
		}
		// asignar los numeros correspondientes
		for (int j = 0; j < TOTAL_ROWS; j++) {
			for (int i = 0; i < TOTAL_COLUMNS; i++) {
				if (!(this.bombs[i][j] == this.bomb)) {
					int b;
					int a;
					if (j == 0) {// Excepciones del primer row
						switch (i) {
						case 0: {// izquierda arriba
							for (b = j; b < j + 2; b++) {
								for (a = 0; a < i + 2; a++) {
									if (this.bombs[a][b] == this.bomb)
										this.numbersAround[i][j]++;
								}
							}
							break;
						}
						case TOTAL_COLUMNS - 1: {// derecha arriba
							for (b = j; b < j + 2; b++) {
								for (a = TOTAL_COLUMNS - 2; a < TOTAL_COLUMNS - 1; a++) {
									if (this.bombs[a][b] == this.bomb)
										this.numbersAround[i][j]++;
								}
							}
							break;
						}
						default: {// derecha a izquierda arriba
							{
								for (b = j; b < (j + 2); b++) {
									for (a = i - 1; a < (i + 2); a++) {
										if (this.bombs[a][b] == this.bomb)
											this.numbersAround[i][j]++;
									}
								}
							}

							break;
						}
						}
					} else if (j == TOTAL_ROWS - 1) {// Excepciones del ultimo
														// row
						switch (i) {
						case 0: {// izquierda abajo
							for (b = j - 1; b < j + 1; b++) {
								for (a = 0; a < i + 2; a++) {
									if (this.bombs[a][b] == this.bomb)
										this.numbersAround[i][j]++;
								}
							}
							break;
						}
						case TOTAL_COLUMNS - 1: {// derecha abajo
							for (b = j - 1; b < j + 1; b++) {
								for (a = TOTAL_COLUMNS - 2; a < TOTAL_COLUMNS; a++) {
									if (this.bombs[a][b] == this.bomb)
										this.numbersAround[i][j]++;
								}
							}
							break;
						}
						default: {// derecha a izquierda abajo
							{
								for (b = j - 1; b < (j + 1); b++) {
									for (a = i - 1; a < (i + 2); a++) {
										if (this.bombs[a][b] == this.bomb)
											this.numbersAround[i][j]++;
									}
								}
							}

							break;
						}
						}

					} else if (i == 0 && j != 0 && j != TOTAL_ROWS - 1) {// arriba
																			// hacia
																			// abajo
																			// izquierda
						for (b = j - 1; b < j + 2; b++) {
							for (a = i; a < i + 2; a++) {
								if (this.bombs[a][b] == this.bomb)
									this.numbersAround[i][j]++;
							}
						}

					} else if (i == TOTAL_COLUMNS - 1 && j != 0 && j != TOTAL_ROWS - 1) {// arriba
																							// hacia
																							// abajo
																							// derecha
						for (b = j - 1; b < j + 2; b++) {
							for (a = i - 1; a < i + 1; a++) {
								if (this.bombs[a][b] == this.bomb)
									this.numbersAround[i][j]++;
							}
						}
					} else if (i > 0 && j > 0 && i < TOTAL_COLUMNS - 1 && j < TOTAL_ROWS - 1) {

						for (b = j - 1; b < (j + 2); b++) {
							for (a = i - 1; a < (i + 2); a++) {
								if (this.bombs[a][b] == this.bomb)
									this.numbersAround[i][j]++;
							}
						}

					}
				}
				//para poner -1 en las bombas del arreglo de numeros tambien.
					// else{ this.numbersAround[i][j]=this.bomb; }
					 
			}
		}

	}

	
	boolean haveBomb(int x, int y) {
		return (this.bombs[x][y] == this.bomb);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Compute interior coordinates
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		int x2 = getWidth() - myInsets.right - 1;
		int y2 = getHeight() - myInsets.bottom - 1;
		int width = x2 - x1;
		int height = y2 - y1;

		// Paint the background
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x1, y1, width + 1, height + 1);

		// Draw the grid minus the bottom row (which has only one cell)
		// By default, the grid will be 10x10 (see above: TOTAL_COLUMNS and
		// TOTAL_ROWS)
		g.setColor(Color.BLACK);
		for (int y = 0; y <= TOTAL_ROWS; y++) {
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)),
					x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}
		for (int x = 0; x <= TOTAL_COLUMNS; x++) {
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y,
					x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS)));

		}

		// Paint cell colors
		for (int x = 0; x < TOTAL_COLUMNS; x++) {
			for (int y = 0; y < TOTAL_ROWS; y++) {
				Color c = colorArray[x][y];
				g.setColor(c);
				g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1,
						INNER_CELL_SIZE, INNER_CELL_SIZE);

			}
		}
		for (int x = 0; x < TOTAL_COLUMNS; x++) {
			for (int y = 0; y < TOTAL_ROWS; y++) {
				Color c = letterColor[x][y];
				g.setColor(c);
				g.drawString(numbersAround[x][y] + "", x1 + 2 * GRID_X + (x * (INNER_CELL_SIZE + 1)) - GRID_X / 2,
						y1 + 2 * GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1 - GRID_Y / 2);
			}

		}
		
		g.setColor(this.youLose);
		g.drawString("YOU LOST THE GAME!",  x1 + 2 * GRID_X + (4 * (INNER_CELL_SIZE + 1)) - GRID_X / 2,
						y1 + 2 * GRID_Y + (9* (INNER_CELL_SIZE + 1)) + 1 - GRID_Y / 2 );
		g.setColor(this.youWin);
		g.drawString("YOU WON THE GAME!",  x1 + 2 * GRID_X + (4 * (INNER_CELL_SIZE + 1)) - GRID_X / 2,
						y1 + 2 * GRID_Y + (10 * (INNER_CELL_SIZE + 1)) + 1 - GRID_Y / 2 );
		

	}

	public int getGridX(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) { // To the left of the grid
			return -1;
		}
		if (y < 0) { // Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) { // Coordinate
																					// is
																					// at
																					// an
																					// edge;
																					// not
																					// inside
																					// a
																					// cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);

		return x;
	}

	public int getGridY(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) { // To the left of the grid
			return -1;
		}
		if (y < 0) { // Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) { // Coordinate
																					// is
																					// at
																					// an
																					// edge;
																					// not
																					// inside
																					// a
																					// cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		return y;
	}
}