import java.util.Random;

public class Minesweeper {
	Random generator = new Random();
	MyMouseAdapter g = new MyMouseAdapter();
	
	public Minesweeper(){
	}

	public void makeBombs(int[] arrayX, int[] arrayY) {
		int i;
		for (i = 0; i < g.getNumberOfBombs(); i++)
		{
			do {
				arrayX[i] = generator.nextInt(10);
				arrayY[i] = generator.nextInt(10);
			} while (arrayX[i] == 0 && arrayY[i] == 0);
		}

	}
}
