import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JFrame;

public class MyMouseAdapter extends MouseAdapter {
	private Color check = Color.RED;

	public boolean isRed(Color gridcolor) {
		return (gridcolor.equals(this.check));
	}

	public void mousePressed(MouseEvent e) {

		switch (e.getButton()) {
		case 1: // Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame) c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		case 3: // Flags red
			c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			myFrame = (JFrame) c;
			myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
			myInsets = myFrame.getInsets();
			x1 = myInsets.left;
			y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			x = e.getX();
			y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		default: // Some other button (2 = Middle mouse button, etc.)
			// Do nothing
			break;
		}
	}

	public void mouseReleased(MouseEvent e) {
		MyMouseAdapter p = new MyMouseAdapter();
		Component c = e.getComponent();
		while (!(c instanceof JFrame)) {
			c = c.getParent();
			if (c == null) {
				return;
			}
		}
		JFrame myFrame = (JFrame) c;
		MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
		Insets myInsets = myFrame.getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		e.translatePoint(-x1, -y1);
		int x = e.getX();
		int y = e.getY();
		myPanel.x = x;
		myPanel.y = y;
		int gridX = myPanel.getGridX(x, y);
		int gridY = myPanel.getGridY(x, y);
		switch (e.getButton()) {
		case 1: // Left mouse button
			if (!(myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == check)) {
				if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
					// Had pressed outside
					// Do nothing
				} else {
					if ((gridX == -1) || (gridY == -1)) {
						// Is releasing outside
						// Do nothing
					} else {
						if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
							// Released the mouse button on a different cell
							// where it was pressed
							// Do nothing
						} else {
							// Released the mouse button on the same cell
							// where
							// it was pressed
							if (myPanel.haveBomb(myPanel.mouseDownGridX, myPanel.mouseDownGridY)) {
								// Si tiene una bomba
								Color newColor = Color.BLACK;
								myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
								myPanel.letterColor[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
								for (int j = 0; j < myPanel.getTotalRows(); j++) {
									for (int i = 0; i < myPanel.getTotalColumns(); i++) {
										if (myPanel.haveBomb(i, j)) {
											newColor = Color.BLACK;
											myPanel.colorArray[i][j] = newColor;
											myPanel.repaint();
											myPanel.letterColor[i][j] = newColor;
										}
									}
								}
								myPanel.youLose = Color.RED;
								myPanel.repaint();
							
							} else {
								myPanel.chainOpener(myPanel.mouseDownGridX, myPanel.mouseDownGridY);
								
								/*
								myPanel.paintCell(myPanel.mouseDownGridX, myPanel.mouseDownGridY);
							

							
							int i, j;
							for (i =myPanel.mouseDownGridY; i < myPanel.getTotalRows(); i++) {
								for (j = myPanel.mouseDownGridX; j < myPanel.getTotalColumns(); j++) {
									try {
										if (myPanel.colorArray[j][i].equals(Color.GRAY)) {
											continue;
										} 
										else if (i == myPanel.mouseDownGridY && j == myPanel.mouseDownGridX) {
											continue;
										} else if (myPanel.haveBomb(j, i)) {
											break;
										} else {
											myPanel.paintCell(j, i);
										}
									} catch (IndexOutOfBoundsException ex) {
										continue;

									}

								}
								if (myPanel.haveBomb(myPanel.mouseDownGridX, i+1))
										break;
								
							}
							
							
							*/}

							
						}

					}

				}
			}

			myPanel.repaint();
			break;

		case 3: {
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				// Had pressed outside
				// Do nothing
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					// Is releasing outside
					// Do nothing
				} else {
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						// Released the mouse button on a different cell where
						// it was pressed
						// Do nothing
					} else {
						// Released the mouse button on the same cell where it
						// was pressed
						Color newColor = null;
						if (p.isRed(myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY])) {
							newColor = Color.WHITE;
						} else {

							newColor = Color.RED;
						}

						myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
						myPanel.letterColor[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
						myPanel.repaint();
					}

					int check = 0;
					for (int j = 0; j < myPanel.getTotalRows(); j++) {
						for (int i = 0; i < myPanel.getTotalColumns(); i++) {
							if (myPanel.winOrLose(i, j)) {
								check++;
							}

						}
					}
					if (check == myPanel.numberOfBombs) {
						// Que hacer si se acaba el juego..
						myPanel.youWin = new Color(0x136808);
						myPanel.repaint();

					}

				}
			}
			myPanel.repaint();

			break;
		}
		default: // Some other button (2 = Middle mouse button, etc.)
			// Do nothing
			break;
		}
	}

}