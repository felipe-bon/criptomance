package fitness;

import java.util.Map;

import geneticAlgorithm.CryptoProblem;

public class FitnessPosicional implements FitnessStrategy {

    private CryptoProblem currentProblem;

    public FitnessPosicional(CryptoProblem currentProblem) {
        this.currentProblem = currentProblem;
    }

    @Override
    public int calculateFitness(int[] chromosome) {
        String t1 = currentProblem.term1;
        String t2 = currentProblem.term2;
        String r = currentProblem.result;

        int error = 0;
        int carry = 0;

        int maxLength = Math.max(Math.max(t1.length(), t2.length()), r.length());

        for (int i = 1; i <= maxLength; i++) {
            int digit1 = 0;
            if (i <= t1.length()) {
                digit1 = getDigitForLetter(t1.charAt(t1.length() - i), chromosome);
            }

            int digit2 = 0;
            if (i <= t2.length()) {
                digit2 = getDigitForLetter(t2.charAt(t2.length() - i), chromosome);
            }

            int digitR = 0;
            if (i <= r.length()) {
                digitR = getDigitForLetter(r.charAt(r.length() - i), chromosome);
            }

            int sum = digit1 + digit2 + carry;
            int resultingDigit = sum % 10;
            carry = sum / 10;

            if (resultingDigit != digitR) {
                // Positional error is the absolute difference between the expected and actual digit
                error += Math.abs(resultingDigit - digitR);
            }
        }
        
        // If there is still a carry but we have no more digits in result, that's an error
        if (carry > 0 && maxLength == r.length()) {
            error += carry; 
        }

        return error;
    }

    private int getDigitForLetter(char letter, int[] chromosome) {
        Map<Character, Integer> letterToIndexMap = currentProblem.letterToIndexMap;
        int index = letterToIndexMap.get(letter);
        return chromosome[index];
    }

    @Override
    public String getLettersSequence() {
      return currentProblem.getLetterSequence();
    }
}
