package logika;

import java.util.Random;

/**
 * Klasa za implementaciju logike igre Candy Crush
 * 
 * @author Aldin Ahmethodžić
 *
 */

public class CandyCrush {

	/**
	 * Slatkiši koji se generišu u igri
	 */
	public final static int[] slatkisi = { 1, 2, 3, 4, 5, 6 };
	/**
	 * Broj bodova za poništena tri polja
	 */
	public final static int bodoviZaTriPonistena = 3;
	/**
	 * Broj bodova za poništena četiri polja
	 */
	public final static int bodoviZaCetiriPonistena = 4;
	/**
	 * Broj bodova za poništenih pet polja
	 */
	public final static int bodoviZaPetPonistenih = 5;

	private int n; // dimenzija

	private int tabelaStanja[][];
	private boolean kraj;

	private int brojPonistavanja;
	private int brojPoteza;
	private int ukupnoPoteza;
	private int bodovi;

	private boolean ispravanPotez;

	/**
	 * Konstruktor sa dva parametra koji postavlja igru
	 * 
	 * @param n            veličina polja za igru - generiše polje veličine n x n
	 * @param ukupnoPoteza broj poteza do završetka igre
	 */

	public CandyCrush(int n, int ukupnoPoteza) {
		super();
		this.kraj = false;
		this.ukupnoPoteza = ukupnoPoteza;

		ispravanPotez = false;
		this.n = n;
		bodovi = 0;
		brojPonistavanja = 0;
		tabelaStanja = new int[this.n][this.n];

		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++) {
				int rnd = new Random().nextInt(slatkisi.length);
				tabelaStanja[i][j] = slatkisi[rnd];
			}
		}

		// Početno poništavanje

		while (true) {
			izvrsiProvjeru();
			if (brojPonistavanja == 0 && !imaPraznih()) {
				break;
			}
			brojPonistavanja = 0;
		}

		brojPoteza = 0;
	}

	/**
	 * Vraća trenutno stanje igre
	 * 
	 * @return matrica trenutnog stanja igre
	 */

	public int[][] vratiTrenutnoStanje() {
		return tabelaStanja;
	}

	/**
	 * Da li bi zamjenom došlo do poništenja reda
	 */

	private Boolean triBiSePonistilaRed(int x1, int y1, int x2, int y2) {
		int[][] pomocna = new int[n][];
		for (int i = 0; i < n; i++)
			pomocna[i] = tabelaStanja[i].clone();

		int temp = pomocna[x1][y1];
		pomocna[x1][y1] = pomocna[x2][y2];
		pomocna[x2][y2] = temp;

		if (y1 + 2 < n) {
			if (pomocna[x1][y1] == pomocna[x1][y1 + 1] && pomocna[x1][y1 + 1] == pomocna[x1][y1 + 2]) {
				return true;
			}
		}

		if (y1 - 2 >= 0) {
			if (pomocna[x1][y1] == pomocna[x1][y1 - 1] && pomocna[x1][y1 - 1] == pomocna[x1][y1 - 2]) {
				return true;
			}
		}

		if (y2 + 2 < n) {
			if (pomocna[x2][y2] == pomocna[x2][y2 + 1] && pomocna[x2][y2 + 1] == pomocna[x2][y2 + 2]) {
				return true;
			}
		}

		if (y2 - 2 >= 0) {
			if (pomocna[x2][y2] == pomocna[x2][y2 - 1] && pomocna[x2][y2 - 1] == pomocna[x2][y2 - 2]) {
				return true;
			}
		}

		if (y1 + 1 < n && y1 - 1 >= 0) {
			if (pomocna[x1][y1] == pomocna[x1][y1 - 1] && pomocna[x1][y1] == pomocna[x1][y1 + 1]) {
				return true;
			}
		}

		if (y2 + 1 < n && y2 - 1 >= 0) {
			if (pomocna[x2][y2] == pomocna[x2][y2 - 1] && pomocna[x2][y2] == pomocna[x2][y2 + 1]) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Da li bi zamjenom došlo do poništenja kolone
	 */

	private Boolean triBiSePonistilaKolona(int x1, int y1, int x2, int y2) {
		int[][] pomocna = new int[n][];
		for (int i = 0; i < n; i++)
			pomocna[i] = tabelaStanja[i].clone();

		int temp = pomocna[x1][y1];
		pomocna[x1][y1] = pomocna[x2][y2];
		pomocna[x2][y2] = temp;

		if (x1 + 2 < n) {
			if (pomocna[x1][y1] == pomocna[x1 + 1][y1] && pomocna[x1 + 1][y1] == pomocna[x1 + 2][y1]) {
				return true;
			}
		}

		if (x1 - 2 >= 0) {
			if (pomocna[x1][y1] == pomocna[x1 - 1][y1] && pomocna[x1 - 1][y1] == pomocna[x1 - 2][y1]) {
				return true;
			}
		}

		if (x2 + 2 < n) {
			if (pomocna[x2][y2] == pomocna[x2 + 1][y2] && pomocna[x2 + 1][y2] == pomocna[x2 + 2][y2]) {
				return true;
			}
		}

		if (x2 - 2 >= 0) {
			if (pomocna[x2][y2] == pomocna[x2 - 1][y2] && pomocna[x2 - 1][y2] == pomocna[x2 - 2][y2]) {
				return true;
			}
		}

		if (x1 + 1 < n && x1 - 1 >= 0) {
			if (pomocna[x1][y1] == pomocna[x1 - 1][y1] && pomocna[x1][y1] == pomocna[x1 + 1][y1]) {
				return true;
			}
		}

		if (x2 + 1 < n && x2 - 1 >= 0) {
			if (pomocna[x2][y2] == pomocna[x2 - 1][y2] && pomocna[x2][y2] == pomocna[x2 + 1][y2]) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Da li bi zamjenom došlo do poništenja polja (red ili kolona)
	 */

	private Boolean baremJednoPonistavanje(int x1, int y1, int x2, int y2) {
		if (triBiSePonistilaRed(x1, y1, x2, y2) || triBiSePonistilaKolona(x1, y1, x2, y2)) {
			return true;
		}

		return false;
	}

	/**
	 * Provjera ispravnosti poteza za datu zamjenu polja
	 */

	private Boolean validanPotez(int x1, int y1, int x2, int y2) {

		if ((x2 == x1 + 1 || x2 == x1 - 1) && (y1 == y2) && baremJednoPonistavanje(x1, y1, x2, y2)) {
			return true;
		}

		if ((y2 == y1 + 1 || y2 == y1 - 1) && (x1 == x2) && baremJednoPonistavanje(x1, y1, x2, y2)) {
			return true;
		}

		return false;
	}

	/**
	 * Provjera za poništenje tri slatkiša u redu i adekvatna dodjela bodova ukoliko
	 * do njega dođe
	 */

	private void provjeriRedZaTri() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (j > n - 3) {
					continue;
				}

				if (tabelaStanja[i][j] == tabelaStanja[i][j + 1] && tabelaStanja[i][j + 1] == tabelaStanja[i][j + 2]) {
					tabelaStanja[i][j] = 0;
					tabelaStanja[i][j + 1] = 0;
					tabelaStanja[i][j + 2] = 0;

					bodovi += bodoviZaTriPonistena;
					brojPonistavanja++;
				}
			}
		}
	}

	/**
	 * Provjera za poništenje četiri slatkiša u redu i adekvatna dodjela bodova
	 * ukoliko do njega dođe
	 */

	private void provjeriRedZaCetiri() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (j > n - 4) {
					continue;
				}

				if (tabelaStanja[i][j] == tabelaStanja[i][j + 1] && tabelaStanja[i][j + 1] == tabelaStanja[i][j + 2]
						&& tabelaStanja[i][j + 2] == tabelaStanja[i][j + 3]) {
					tabelaStanja[i][j] = 0;
					tabelaStanja[i][j + 1] = 0;
					tabelaStanja[i][j + 2] = 0;
					tabelaStanja[i][j + 3] = 0;

					bodovi += bodoviZaCetiriPonistena;
					brojPonistavanja++;
				}
			}
		}
	}

	/**
	 * Provjera za poništenje tri slatkiša u koloni i adekvatna dodjela bodova
	 * ukoliko do njega dođe
	 */

	private void provjeriKolonuZaTri() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i > n - 3) {
					continue;
				}
				if (tabelaStanja[i][j] == tabelaStanja[i + 1][j] && tabelaStanja[i + 1][j] == tabelaStanja[i + 2][j]) {
					tabelaStanja[i][j] = 0;
					tabelaStanja[i + 1][j] = 0;
					tabelaStanja[i + 2][j] = 0;

					bodovi += bodoviZaTriPonistena;
					brojPonistavanja++;
				}
			}
		}
	}

	/**
	 * Provjera za poništenje četiri slatkiša u koloni i adekvatna dodjela bodova
	 * ukoliko do njega dođe
	 */

	private void provjeriKolonuZaCetiri() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i > n - 4) {
					continue;
				}
				if (tabelaStanja[i][j] == tabelaStanja[i + 1][j] && tabelaStanja[i + 1][j] == tabelaStanja[i + 2][j]
						&& tabelaStanja[i + 2][j] == tabelaStanja[i + 3][j]) {
					tabelaStanja[i][j] = 0;
					tabelaStanja[i + 1][j] = 0;
					tabelaStanja[i + 2][j] = 0;
					tabelaStanja[i + 3][j] = 0;

					bodovi += bodoviZaCetiriPonistena;
					brojPonistavanja++;
				}
			}
		}
	}

	/**
	 * Provjera za poništenje pet slatkiša u redu i adekvatna dodjela bodova ukoliko
	 * do njega dođe
	 */

	private void provjeriRedZaPet() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (j > n - 5) {
					continue;
				}

				if (tabelaStanja[i][j] == tabelaStanja[i][j + 1] && tabelaStanja[i][j + 1] == tabelaStanja[i][j + 2]
						&& tabelaStanja[i][j + 2] == tabelaStanja[i][j + 3]
						&& tabelaStanja[i][j + 3] == tabelaStanja[i][j + 4]) {
					tabelaStanja[i][j] = 0;
					tabelaStanja[i][j + 1] = 0;
					tabelaStanja[i][j + 2] = 0;
					tabelaStanja[i][j + 3] = 0;
					tabelaStanja[i][j + 4] = 0;

					bodovi += bodoviZaPetPonistenih;
					brojPonistavanja++;

				}
			}
		}
	}

	/**
	 * Provjera za poništenje pet slatkiša u koloni i adekvatna dodjela bodova
	 * ukoliko do njega dođe
	 */

	private void provjeriKolonuZaPet() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i > n - 5) {
					continue;
				}
				if (tabelaStanja[i][j] == tabelaStanja[i + 1][j] && tabelaStanja[i + 1][j] == tabelaStanja[i + 2][j]
						&& tabelaStanja[i + 2][j] == tabelaStanja[i + 3][j]
						&& tabelaStanja[i + 3][j] == tabelaStanja[i + 4][j]) {
					tabelaStanja[i][j] = 0;
					tabelaStanja[i + 1][j] = 0;
					tabelaStanja[i + 2][j] = 0;
					tabelaStanja[i + 3][j] = 0;
					tabelaStanja[i + 4][j] = 0;

					bodovi += bodoviZaPetPonistenih;
					brojPonistavanja++;

				}
			}
		}
	}

	/**
	 * Pomjera sve poništene slatkiše na vrh polja, zatim ih mijenja novim
	 */

	private void pomjeriDole() {
		for (int i = 0; i < n - 1; i++) {
			for (int j = 0; j < n; j++) {
				if (tabelaStanja[i + 1][j] == 0) {

					tabelaStanja[i + 1][j] = tabelaStanja[i][j];
					tabelaStanja[i][j] = 0;

					boolean uPrvomRedu = i == 0;

					if (uPrvomRedu && tabelaStanja[i][j] == 0) {
						int rnd = new Random().nextInt(slatkisi.length);
						tabelaStanja[i][j] = slatkisi[rnd];
					}
				}
			}
		}
	}

	/**
	 * Sva poništena polja u prvom redu mijenja novim slatkišima
	 */

	private void srediPrviRed() {
		int i = 0;
		for (int j = 0; j < n; j++) {
			if (tabelaStanja[i][j] == 0) {
				int rnd = new Random().nextInt(slatkisi.length);
				tabelaStanja[i][j] = (slatkisi[rnd]);
			}
		}
	}

	/**
	 * Izvršava provjeru za poništavanje slatkiša, poništava ih ukoliko je moguće,
	 * potom generiše nove slatkiše
	 */

	private void izvrsiProvjeru() {
		pomjeriDole();
		srediPrviRed();
		provjeriRedZaPet();
		provjeriKolonuZaPet();
		provjeriRedZaCetiri();
		provjeriKolonuZaCetiri();
		provjeriRedZaTri();
		provjeriKolonuZaTri();
	}

	/**
	 * Provjerava postoji li poništenih slatkiša u polju
	 */

	private Boolean imaPraznih() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (tabelaStanja[i][j] == 0)
					return true;
			}
		}
		return false;
	}

	/**
	 * Vrši zamjenu slatkiša ukoliko je potez validan, potom poništava slatkiše i
	 * generiše nove dokle god je potrebno
	 * 
	 * @param x1 red prvog slatkiša
	 * @param y1 kolona prvog slatkiša
	 * @param x2 red drugog slatkiša
	 * @param y2 kolona drugog slatkiša
	 */
	public void odradiPromjenu(int x1, int y1, int x2, int y2) {
		if (validanPotez(x1, y1, x2, y2)) {
			ispravanPotez = true;

			int temp = tabelaStanja[x1][y1];
			tabelaStanja[x1][y1] = tabelaStanja[x2][y2];
			tabelaStanja[x2][y2] = temp;

			while (true) {
				izvrsiProvjeru();
				if (brojPonistavanja == 0 && !imaPraznih())
					break;
				brojPonistavanja = 0;
			}

			brojPoteza++;
			if (brojPoteza == ukupnoPoteza) {
				kraj = true;
			}

		} else {
			ispravanPotez = false;
			System.out.println("\nNedozvoljen potez. Pokušajte ponovo.\n");
		}
	}

	/**
	 * Vraća trenutni broj bodova
	 * 
	 * @return trenutni bodovi
	 */
	public int vratiBodove() {
		return bodovi;
	}

	/**
	 * Vraća informaciju o tome da li je posljednji potez bio ispravan
	 * 
	 * @return ispravnost posljednjeg poteza
	 */
	public boolean bioIspravan() {
		return ispravanPotez;
	}

	/**
	 * Vraća informaciju o tome da li je igra završena
	 * 
	 * @return završetak igre
	 */
	public boolean krajIgre() {
		return kraj;
	}

}
