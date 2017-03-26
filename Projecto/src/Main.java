import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
	
		JFrame myFrame = new JFrame("Color Grid");
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setLocation(400, 150);
		myFrame.setSize(400, 400);
		MyPanel myPanel = new MyPanel();
		myFrame.add(myPanel);
		MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
		myFrame.addMouseListener(myMouseAdapter);
		myFrame.setVisible(true);
	}

}
//04

/* Que falta? 
 * falta que abran todos los espacios grises que no tienes numero si estan pegados
 * falta que si explotan una mine se revelan todas
 * falta verificar el numero de minas que se esta exigiendo 
 * dar una ultima revisada a los requisitos y creo que ya. jejeje
 * ELIMINAR TODOS LOS COMMENTS RAROS
 */