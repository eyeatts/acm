package acm;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Test;

public class ScoreboardTest {

	@Test
	public void testExampleScoreboard() {
		
		testScoreboard (

			new String[] {

					"Jason", 
					"Jim", 
					"Rob", 
					"John",

			}, 
			
			new int[][] {

					{4, 6, 3, 2},
					{4, 4, 3, 5},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{5, 8, 4, 6},

			}, 
			
			new int[][] {

					{4, 6, 3, 2},
					{3, 5, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{4, 4, 4, 4},
					{5, 6, 4, 8},

			}
		);
	}

	private void testScoreboard(String[] golfers, int[][] scoreInput, int[][] expectedOutput) {
		
		// Setup Input	
		StringBuilder in = new StringBuilder();
		int maxLength = 0;
		for (String name : golfers) { 
			in.append(String.format("%s%n", name)); 
			if (name.length() > maxLength) { maxLength = name.length(); }
		}
		for (int[] hole : scoreInput) {
			in.append(String.format("%d %d %d %d%n", 
					hole[0], hole[1], hole[2], hole[3]));
		}
		System.setIn(new ByteArrayInputStream(in.toString().getBytes()));
		
		// Setup Output
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outStream));
		
		// Run the solution
		Scoreboard.main(new String[]{});
		
		// Load the output
		Scanner out = new Scanner(outStream.toString());
		
		// Test the header
		for (int i = maxLength; i > 0; i--) {
			for (String name : golfers) {
				if (name.length()-i < 0) {
					continue;
				} else {
					assertEquals(out.next("[^\\s]").charAt(0), name.charAt(name.length()-i));
				}
			}
			out.nextLine();
		}
		assertTrue(out.nextLine().contains("-"));
		
		// Test the holes
		int[] totals = new int[]{0, 0, 0, 0};
		for (int h = 0; h < expectedOutput.length; h++) { 
			int[] hole = expectedOutput[h];
			assertEquals(out.nextInt(), h+1);
			for (int i = 0; i < golfers.length; i++) {
				totals[i] += hole[i];
				assertEquals(out.nextInt(), hole[i]);
			}
			out.nextLine();
		}
		assertTrue(out.nextLine().contains("-"));
		
		// Test the total
		assertEquals(out.next(), "Total");
		for (int total : totals) {
			assertEquals(out.nextInt(), total);
		}
		out.nextLine();
		
		// Cleanup
		out.close();
		System.setIn(null);
		System.setOut(null);
	}

}
