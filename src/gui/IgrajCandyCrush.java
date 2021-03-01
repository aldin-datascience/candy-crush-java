package gui;

import javax.swing.JFrame;

/**
 * Klasa za pokretanje igre sa uređenim GUI-em
 * 
 * @author Aldin Ahmethodžić
 * 
 */
public class IgrajCandyCrush {

	/**
	 * Kreira prozor za igru
	 * @param args string args
	 */
	public static void main(String[] args) {

		JFrame jf = new JFrame("Candy Crush");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MojPanel mojPanel = new MojPanel(9, 5);
		jf.setSize(850, 900);
		jf.setContentPane(mojPanel);
		// jf.setResizable(false);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);

	}
}
