package acm;

import java.util.ArrayList;
import java.util.Scanner;

/*
 * Joeball
 * Problem #3
 * 
 * !998 ACM Mid Atlantic Programming Contest
 */
public class Joeball {

	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		
		int gameCount = in.nextInt();
		int[] games = new int[gameCount];
		for (int i = 0; i < gameCount; i++) {
			games[i] = in.nextInt();
		}
		in.close();
		
		for (int i = 0; i < gameCount; i++) {
			int[][] combinations = getCombinations(games[i]);
			System.out.printf(
					"Game #%d\t%d total points\tNumber of possible combinations: %d\n", 
					i, games[i], combinations.length
				);
			
			if (combinations.length > 0) {
				System.out.printf("%-5s%-15s%-10s%-15s%-15s\n", 
						"Comb", "Dbl. Turnball", "Turnball", "Def. Grumpling", "Extra Pts.");
				for (int c = 0; c < combinations.length; c++) {
					int[] comb = combinations[c];
					System.out.printf("%-5d%-15d%-10d%-15d%-15d\n", 
							c+1, comb[0], comb[1], comb[2], comb[3]);
				}
			} else {
				System.out.println("No possible combinations");
			}
			System.out.println();
		}
		
		System.out.println("End of question 3.");
	}

	private static int[][] getCombinations(int score) {
		ArrayList<ArrayList<Integer>> combinations = new ArrayList<ArrayList<Integer>>();
		
		int maxDblTurnballs = score / 14;
		for (int dt = 0; dt <= maxDblTurnballs; dt++) {
			ArrayList<Integer> dtComb = new ArrayList<Integer>(4);
			dtComb.add(dt);
			
			int dtRem = score - dt*14;
			if (dtRem == 0) {
				combinations.add(dtComb);
			}
			
			int maxTurnballs = dtRem / 6;
			for (int tb = 0; tb <= maxTurnballs; tb++) {
				ArrayList<Integer> tbComb = new ArrayList<Integer>(dtComb);
				tbComb.add(tb);
				
				int tbRem = dtRem - tb*6;
				if (tbRem == 0) {
					combinations.add(tbComb);
				}
				
				int maxDefGrumplings = tbRem / 2;
				for (int dg = 0; dg <= maxDefGrumplings; dg++) {
					ArrayList<Integer> dgComb = new ArrayList<Integer>(tbComb);
					dgComb.add(dg);
					
					int rem = tbRem - dg*2;
					if (rem <= tb) {
						dgComb.add(rem);
						combinations.add(dgComb);
					}
				}
			}
		}
		
		int[][] ary = new int[combinations.size()][4];
		for (int i = 0; i < combinations.size(); i++) {
			ArrayList<Integer> comb = combinations.get(i);
			for (int v = 0; v < comb.size(); v++) {
				ary[i][v] = comb.get(v);
			}
		}
		
		return ary;
	}
	
}
