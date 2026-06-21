package fitness;

import java.util.Map;

import geneticAlgorithm.CryptoProblem;

public class FitnessGlobal implements FitnessStrategy {

    private CryptoProblem currentProblem;

    // Constructor receives the current problem (e.g., SEND, MORE, MONEY)
    public FitnessGlobal(CryptoProblem currentProblem) {
        this.currentProblem = currentProblem;
    }

    @Override
    public int calculateFitness(int[] chromosome) {

        // Converts words into integers using the individual's genes
        int valueTerm1 = convertWordToNumber(currentProblem.term1, chromosome);
        int valueTerm2 = convertWordToNumber(currentProblem.term2, chromosome);
        int valueResult = convertWordToNumber(currentProblem.result, chromosome);

        // Solves: |(Term1 + Term2) - Result|
        return Math.abs((valueTerm1 + valueTerm2) - valueResult);
    }

    private int convertWordToNumber(String word, int[] chromosome) {

        int number = 0;
        Map<Character, Integer> letterToIndexMap = currentProblem.letterToIndexMap;

        // Iterate through each letter from left to right
        for (int i = 0; i < word.length(); i++) {

            char letter = word.charAt(i);

            // Find which index in the chromosome this letter maps to
            int indexInVector = letterToIndexMap.get(letter);

            // Get the digit assigned by the individual for this position
            int digit = chromosome[indexInVector];

            // Build the number (shift left and add digit)
            number = (number * 10) + digit;
        }

        return number;
    }

    @Override
    public String getLettersSequenc() {
      return currentProblem.getLetterSequence();
    }
}