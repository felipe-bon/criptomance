package geneticAlgorithm;

import java.util.HashMap;
import java.util.Map;

public class CryptoProblem {

    public String term1;
    public String term2;
    public String result;

    public Map<Character, Integer> letterToIndexMap;

    public CryptoProblem(String term1, String term2, String result) {
        this.term1 = term1;
        this.term2 = term2;
        this.result = result;
        this.letterToIndexMap = new HashMap<>();

        mapUniqueLetters();
    }

    private void mapUniqueLetters() {
        int currentIndex = 0;

        String allLetters = this.term1 + this.term2 + this.result;

        for (char letter : allLetters.toCharArray()) {
            if (!this.letterToIndexMap.containsKey(letter)) {
                this.letterToIndexMap.put(letter, currentIndex);
                currentIndex++;
            }
        }
    }
}