package acm;

import java.util.ArrayList;
import java.util.Scanner;

/*
 * Word Morphing
 * Problem E
 * 
 * 1997 Mid-Atlantic Regional Programming Contest
 */
public class WordMorpher {
	
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		
		int num = input.nextInt();
		ArrayList<String> dic = new ArrayList<String>(num);
		for (int i = 0; i < num; i++)
			dic.add(input.next());
		
		int pairCount = input.nextInt();
		String[][] pairs = new String[pairCount][2];
		for (int i = 0; i < pairCount; i++)
		{
			pairs[i][0] = input.next();
			pairs[i][1] = input.next();
		}
		input.close();
		
		for (int i = 0; i < pairCount; i++)
		{
			String src = pairs[i][0];
			String dest = pairs[i][1];
			
			System.out.printf("PAIR %d: %s %s\n", i+1, src, dest);
			
			ArrayList<String> seq = morph(src, dest, dic);
			if (seq.isEmpty()) {
				System.out.println("NO SEQUENCE");
			} else {
				for (String word : seq)
					System.out.println(word);
			}
			if (pairCount != i+1)
				System.out.println();
				
		}
		System.out.println("END OF OUTPUT");
	}
	
	public static ArrayList<String> morph(String src, String dest, ArrayList<String> dic)
	{
		ArrayList<String> result;
		if (src.equals(dest)) {
			result = new ArrayList<String>();
			result.add(dest);
			return result;
		}
		
		dic = new ArrayList<String>(dic);
		dic.remove(src);
		
		ArrayList<String> subs = findSubstitutions(src, dic);
		for (String sub : subs)
		{	
			result = morph(sub, dest, dic);
			if (!result.isEmpty()) {
				result.add(0, src);
				return result;
			}
		}
		return new ArrayList<String>();
	}

	private static ArrayList<String> findSubstitutions(String src, ArrayList<String> dic) {
		ArrayList<String> subs = new ArrayList<String>();
		for (String word : dic) {
			int dif = 0;
			for (int i = 0; i < src.length(); i++) {
				if (src.charAt(i) != word.charAt(i)) {
					dif++;
					if (dif > 1)
						break;
				}
			}
			if (dif < 2)
				subs.add(word);
		}
		return subs;
	}

}
