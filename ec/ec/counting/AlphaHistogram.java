package cs250.ec.counting;

/*  Responsible for counting the frequency of each letter in a given sentence,
    ignoring case, and then outputting the frequencies in descending order. */

public class AlphaHistogram {

//  Processes the input sentence and prints a histogram of letter frequencies.

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java cs250.ec.counting.AlphaHistogram \"<sentence>\"");
            return;
        }

        String input = args[0].toLowerCase();
        int[] counts = new int[26]; // For letters 'a' to 'z'

        for (char c : input.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                counts[c - 'a']++;
            }
        }

//  Bubble sort the array to order letters by frequency
        bubbleSort(counts);

        // Output the results
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > 0) {
                System.out.println((char)('a' + i) + ": " + counts[i]);
            }
        }
    }

//  A simple bubble sort method to sort the frequency array in descending order.
    private static void bubbleSort(int[] counts) {
        int n = counts.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (counts[j] < counts[j + 1]) {
                    int temp = counts[j];
                    counts[j] = counts[j + 1];
                    counts[j + 1] = temp;
                }
            }
        }
    }
}
