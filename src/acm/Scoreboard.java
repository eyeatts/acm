package acm;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/*
 * What's my score?
 * Problem #1
 * 
 * 1998 ACM Mid Atlantic Programming Contest
 */
public class Scoreboard {
	
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		
		int maxLength = 0;
		String[] names = new String[4];
		for (int i = 0; i < 4; i++) {
			String name = in.next();
			int len = name.length();
			if (len > maxLength) {
				maxLength = len;
			}
			names[i] = name;
		}
		
		int sb[][] = new int[18][4];
		Integer order[] = {0, 1, 2, 3};
		
		for (int i = 0; i < 18; i++)
		{
			for (int s = 0; s < 4; s++) {
				sb[i][order[s]] = in.nextInt();
			}
			Arrays.sort(order,  new ScoreComparator(sb[i]));
		}
		in.close();
		
		for (int i = maxLength; i > 0; i--) {
			System.out.printf("%8s", "");
			for (String name : names) {
				System.out.printf("\t%3s", (name.length()-i < 0 ? ' ' : name.charAt(name.length()-i)));
			}
			System.out.println();
		}
		
		System.out.println("-------------------------------------------");
		
		for (int i = 0; i < sb.length; i++)
		{
			System.out.printf("%8d\t", i+1);
			System.out.printf("%3d\t%3d\t%3d\t%3d\n", sb[i][0], sb[i][1], sb[i][2], sb[i][3]);
		}
		
		System.out.println("-------------------------------------------");
		
		System.out.printf("%8s\t", "Total");
		System.out.printf("%3d\t%3d\t%3d\t%3d\n", total(sb, 0), total(sb, 1), total(sb, 2), total(sb,3));
		
	}
	
	private static int total(int[][] sb, int player) {
		int total = 0;
		for (int i = 0; i < 18; i++) {
			total += sb[i][player];
		}
		return total;
	}
	
	static class ScoreComparator implements Comparator<Integer> {
		
		private int[] scores;
		
		public ScoreComparator(int[] scores) {
			super();
			this.scores = scores;
		}

		@Override
	    public int compare(Integer i1, Integer i2) {
	        return ((Integer)this.scores[i1]).compareTo(this.scores[i2]);
	    }
	}

}
