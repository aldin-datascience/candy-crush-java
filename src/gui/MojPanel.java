package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import logika.CandyCrush;

/**
 * Klasa za kreiranje panela za igru, uključuje traku za bodove i panel za
 * dugmad
 * 
 * @author Aldin Ahmethodžić
 *
 */

public class MojPanel extends JPanel {
	CandyCrush candy_crush;
	Traka traka;
	JButton[][] tabelaDugmadi;
	JPanel prikazTabele;
	private int brojacKlika = 0;
	private JButton prvoPolje, drugoPolje;

	/**
	 * Konstruktor sa dva parametra koji postavlja panel za igru
	 * 
	 * @param n            veličina polja za igru - generiše polje veličine n x n
	 * @param ukupnoPoteza broj poteza do završetka igre
	 */
	public MojPanel(int n, int ukupnoPoteza) {
		super();

		candy_crush = new CandyCrush(n, ukupnoPoteza);

		setLayout(new GridBagLayout());
		prikazTabele = new JPanel();
		traka = new Traka();

		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.1;
		c.weighty = 0.1;
		c.fill = GridBagConstraints.BOTH;
		add(traka, c);
		traka.dodajBodove(candy_crush.vratiBodove());

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.9;
		c.weighty = 0.9;
		c.fill = GridBagConstraints.BOTH;

		prikazTabele.setLayout(new GridLayout(candy_crush.vratiTrenutnoStanje()[0].length,
				candy_crush.vratiTrenutnoStanje()[0].length));
		tabelaDugmadi = new JButton[candy_crush.vratiTrenutnoStanje()[0].length][candy_crush
				.vratiTrenutnoStanje()[0].length];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				tabelaDugmadi[i][j] = new JButton();
				tabelaDugmadi[i][j].setName(i + " " + j);
				prikazTabele.add(tabelaDugmadi[i][j]);
				tabelaDugmadi[i][j].setBackground(getBoja(candy_crush.vratiTrenutnoStanje()[i][j]));

				// Ukloni border, content i fokus sa dugmadi
				tabelaDugmadi[i][j].setBorder(BorderFactory.createEmptyBorder());
				tabelaDugmadi[i][j].setContentAreaFilled(false);
				tabelaDugmadi[i][j].setBorderPainted(false);
				tabelaDugmadi[i][j].setFocusPainted(false);

				// Postavi sliku ovisno od boje pozadine
				promjeniSliku(tabelaDugmadi[i][j]);

				// Dodaj action listener za svako dugme
				tabelaDugmadi[i][j].addActionListener(new MojActionListener());
			}
		}
		add(prikazTabele, c);
	}

	/**
	 * Vrati boju ovisno od broja koji predstavlja slatkiš
	 */
	private Color getBoja(int boja) {
		if (boja == 1) {
			return Color.BLUE;
		}
		if (boja == 2) {
			return Color.RED;
		}
		if (boja == 3) {
			return Color.MAGENTA;
		}
		if (boja == 4) {
			return Color.YELLOW;
		}
		if (boja == 5) {
			return Color.ORANGE;
		}
		if (boja == 6) {
			return Color.GREEN;
		}

		return Color.WHITE;
	}

	/**
	 * Mijenja sliku za slatkiš ovisno od boje dugmeta koje ga predstavlja
	 */
	private void promjeniSliku(JButton dugme) {
		try {
			if (dugme.getBackground() == Color.BLUE) {
				Image img = ImageIO.read(getClass().getResource("/slike/blue-candy.png"));
				dugme.setIcon(new ImageIcon(img));
			}
			if (dugme.getBackground() == Color.RED) {
				Image img = ImageIO.read(getClass().getResource("/slike/red-candy.png"));
				dugme.setIcon(new ImageIcon(img));
			}
			if (dugme.getBackground() == Color.YELLOW) {
				Image img = ImageIO.read(getClass().getResource("/slike/yellow-candy.png"));
				dugme.setIcon(new ImageIcon(img));
			}
			if (dugme.getBackground() == Color.MAGENTA) {
				Image img = ImageIO.read(getClass().getResource("/slike/purple-candy.png"));
				dugme.setIcon(new ImageIcon(img));
			}
			if (dugme.getBackground() == Color.GREEN) {
				Image img = ImageIO.read(getClass().getResource("/slike/green-candy.png"));
				dugme.setIcon(new ImageIcon(img));
			}
			if (dugme.getBackground() == Color.ORANGE) {
				Image img = ImageIO.read(getClass().getResource("/slike/orange-candy.png"));
				dugme.setIcon(new ImageIcon(img));
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	/**
	 * Osvježi trenutno stanje igre, promjeni boju slatkiša ovisno od stanja, potom
	 * i sliku
	 */
	public void osvjeziStanjeTabele() {
		int n = tabelaDugmadi[0].length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				tabelaDugmadi[i][j].setBackground(getBoja(candy_crush.vratiTrenutnoStanje()[i][j]));
				promjeniSliku(tabelaDugmadi[i][j]);
			}
		}
	}

	/**
	 * Moj action listener za dugmad
	 * 
	 * @author Aldin Ahmethodžić
	 *
	 */
	class MojActionListener implements ActionListener {

		/**
		 * Pamti koji smo put kliknuli i na drugi klik vrši promjenu u igri
		 */
		@Override
		public void actionPerformed(ActionEvent e) {

			brojacKlika++;
			if (brojacKlika == 1) {
				prvoPolje = (JButton) e.getSource();
			}
			if (brojacKlika == 2) {
				drugoPolje = (JButton) e.getSource();
				String[] koordinatePrvogPolja = prvoPolje.getName().split(" ");
				String[] koordinateDrugogPolja = drugoPolje.getName().split(" ");

				int x1 = Integer.parseInt(koordinatePrvogPolja[0]);
				int y1 = Integer.parseInt(koordinatePrvogPolja[1]);
				int x2 = Integer.parseInt(koordinateDrugogPolja[0]);
				int y2 = Integer.parseInt(koordinateDrugogPolja[1]);

				candy_crush.odradiPromjenu(x1, y1, x2, y2);
				brojacKlika = 0;
				osvjeziStanjeTabele();

				if (candy_crush.bioIspravan())
					traka.dodajBodove(candy_crush.vratiBodove());

				if (candy_crush.krajIgre()) {
					int n = tabelaDugmadi[0].length;
					for (int i = 0; i < n; i++) {
						for (int j = 0; j < n; j++) {
							tabelaDugmadi[i][j].setEnabled(false);
						}
					}

					traka.promjeniTekst("Kraj! Bodovi:");
				}
			}
		}

	}
}
