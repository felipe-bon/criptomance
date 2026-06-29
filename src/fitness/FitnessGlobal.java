package fitness;

import java.util.Map;

import geneticAlgorithm.CryptoProblem;

public class FitnessGlobal implements FitnessStrategy {

    private CryptoProblem currentProblem;

    // Construtor recebe o problema (exemplo: send+more=money)
    public FitnessGlobal(CryptoProblem currentProblem) {
        this.currentProblem = currentProblem;
    }

    @Override
    public int calculateFitness(int[] chromosome) {

        // Converte palavras para inteiros usando genes individuais
        int valueTerm1 = convertWordToNumber(currentProblem.term1, chromosome);
        int valueTerm2 = convertWordToNumber(currentProblem.term2, chromosome);
        int valueResult = convertWordToNumber(currentProblem.result, chromosome);

        // resolve |(Term1 + Term2) - Result|
        return Math.abs((valueTerm1 + valueTerm2) - valueResult);
    }

    private int convertWordToNumber(String word, int[] chromosome) {

        int number = 0;
        Map<Character, Integer> letterToIndexMap = currentProblem.letterToIndexMap;

        for (int i = 0; i < word.length(); i++) {

            char letter = word.charAt(i);
            int indexInVector = letterToIndexMap.get(letter);
            int digit = chromosome[indexInVector];
            number = (number * 10) + digit;
        }

        return number;
    }

    @Override
    public String getLettersSequence() {
      return currentProblem.getLetterSequence();
    }
}