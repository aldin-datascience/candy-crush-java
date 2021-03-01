package konzola;

import java.util.Scanner;
import logika.CandyCrush;

/**
 * Klasa za pokretanje igre Candy Crush unutar konzole
 * 
 * @author Aldin Ahmethodžić
 *
 */
public class IgrajCandyCrush {

	/**
	 * Učita informacije od korisnika i pokreće igru, potom ispisuje rezultate igre
	 * @param args string args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("Unesite velicinu tabele i broj poteza:\n");
		int n = sc.nextInt();
		int ukupnoPoteza = sc.nextInt();

		CandyCrush candy_crush = new CandyCrush(n, ukupnoPoteza);

		System.out.println(pripremiTabeluStanja(candy_crush.vratiTrenutnoStanje()));

		while (!candy_crush.krajIgre()) {
			int[] potez = ucitajPotez();
			odigrajPotez(potez, candy_crush);
			if(!candy_crush.bioIspravan())
			System.out.println(pripremiTabeluStanja(candy_crush.vratiTrenutnoStanje()));
		}

		System.out.println("Kraj igre!\n");
		System.out.println("Osvojeno bodova: " + candy_crush.vratiBodove());
	}

	/**
	 * Odigra potez igrača za datu poziciju polja
	 */
	private static void odigrajPotez(int[] koordinate, CandyCrush candy_crush) {
		// TODO Auto-generated method stub
		int x1 = koordinate[0];
		int y1 = koordinate[1];
		int x2 = koordinate[2];
		int y2 = koordinate[3];

		candy_crush.odradiPromjenu(x1, y1, x2, y2);
	}

	/**
	 * Učitavanje poteza igrača
	 */
	private static int[] ucitajPotez() {
		Scanner sc = new Scanner(System.in);
		int[] koordinate = new int[4];
		System.out.println("Unesite koordinate dva polja:\n");
		for (int i = 0; i < koordinate.length; i++)
			koordinate[i] = sc.nextInt();

		return koordinate;
	}

	/**
	 * Priprema trenutnog stanja u polju za igru za ispis igraču
	 */

	private static String pripremiTabeluStanja(int[][] vratiTrenutnoStanje) {
		String stanje = "";
		int n = vratiTrenutnoStanje[0].length;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				stanje += vratiTrenutnoStanje[i][j] + " ";
			}
			stanje += "\n";
		}

		return stanje;
	}
}
