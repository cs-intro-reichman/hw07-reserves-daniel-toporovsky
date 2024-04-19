
public class SpellChecker {


	public static void main(String[] args) {
		String word = args[0];
		int threshold = Integer.parseInt(args[1]);
		String[] dictionary = readDictionary("dictionary.txt");
		String correction = spellChecker(word, threshold, dictionary);
		System.out.println(correction);
	}

	public static String tail(String str) {
		if (str.length() == 1) return "";
		else return str.substring(1);
	}

	public static int levenshtein(String word1, String word2) {
		// Pre-formatting to lowercase
		word1 = word1.toLowerCase();
		word2 = word2.toLowerCase();

		// Checks if one of the strings is empty
		if (word1.length() == 0) {
			return word2.length();
		}		
		else if (word2.length() == 0) {
			return word1.length();
		}

		else if (word1.charAt(0) == word2.charAt(0)) {
			return levenshtein(tail(word1), tail(word2));
		}

		else {
			int i = levenshtein(tail(word1), word2);
			int j = levenshtein(word1, tail(word2));
			int k = levenshtein(tail(word1), tail(word2));

			int min = Math.min(i, Math.min(j, k));

			return 1 + min;
		}
	}

	public static String[] readDictionary(String fileName) {
		String[] dictionary = new String[3000];

		In in = new In(fileName);

		for (int i = 0; i < dictionary.length; i++) {
			dictionary[i] = in.readString();
		}

		return dictionary;
	}

	public static String spellChecker(String word, int threshold, String[] dictionary) {
		int min = word.length();
		String resembles = " ";
		for (int i = 0; i < dictionary.length; i++) {
			int lengthOfLevenstein = levenshtein(word, dictionary[i]);
			if ((min >= lengthOfLevenstein) && (resembles.length() != word.length() || min != lengthOfLevenstein)) {
				min = lengthOfLevenstein;
				resembles = dictionary[i];	
			}
		}

		if (min > threshold) {
			return "“" + word+ "” since the most similar word from the dictionary is at a distance greater than " + threshold;
		}

		else {
			return "“" + resembles + "” since levenshtein(“" + word + "”, “" + resembles + "”) == " + min;
		}
	}

}
