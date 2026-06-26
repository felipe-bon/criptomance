package fitness;

import java.util.Map;

import geneticAlgorithm.CryptoProblem;

public class FitnessPositional implements FitnessStrategy {

  private CryptoProblem currentProblem;

  // Constructor receives the current problem (e.g., SEND, MORE, MONEY)
  public FitnessPositional(CryptoProblem currentProblem) {
    this.currentProblem = currentProblem;
  }

  @Override
  public int calculateFitness(int[] chromosome) {

    String term1 = currentProblem.term1;
    String term2 = currentProblem.term2;
    String result = currentProblem.result;

    int totalError = 0;
    int carry = 0; // The "vai-um"

    // Find the maximum number of columns to process based on the longest word
    int maxColumns = Math.max(result.length(), Math.max(term1.length(), term2.length()));

    // Iterate from right to left (index 0 is units, 1 is tens, 2 is hundreds, etc.)
    for (int i = 0; i < maxColumns; i++) {

      // Get the digit for each word in the current column (returns 0 if the word
      // ended)
      int digitT1 = extractDigitFromColumn(term1, i, chromosome);
      int digitT2 = extractDigitFromColumn(term2, i, chromosome);
      int digitRes = extractDigitFromColumn(result, i, chromosome);

      // Actual mathematical sum for the current column including the carry from
      // previous column
      int columnSum = digitT1 + digitT2 + carry;

      // The digit that should correctly be at the bottom is the remainder of division
      // by 10
      int correctDigit = columnSum % 10;

      // The error for this column is the absolute difference between the correct math
      // and the individual's proposal
      totalError += Math.abs(correctDigit - digitRes);

      // Update the carry for the next iteration (next column to the left)
      carry = columnSum / 10;
    }

    // If there's a leftover carry after processing all columns, it means the result
    // "overflowed", which is also an error
    totalError += carry;

    return totalError;
  }

  /**
   * Helper method to extract the digit of a specific column (from right to left).
   * If the column index exceeds the word length (e.g., trying to read the 4th
   * column of "EAT"), returns 0.
   */
  private int extractDigitFromColumn(String word, int rightColumnIndex, int[] chromosome) {

    // Translate the right-to-left column index into the standard left-to-right
    // string index
    int stringIndex = word.length() - 1 - rightColumnIndex;

    // If the column goes beyond the length of the word, it counts as a 0 in the
    // math operation
    if (stringIndex < 0) {
      return 0;
    }

    char letter = word.charAt(stringIndex);
    Map<Character, Integer> letterToIndexMap = currentProblem.letterToIndexMap;

    // Find which index in the chromosome this letter maps to
    int indexInVector = letterToIndexMap.get(letter);

    // Return the digit assigned by the individual for this position
    return chromosome[indexInVector];
  }

  @Override
  public String getLettersSequence() {
    return currentProblem.getLetterSequence();
  }
}