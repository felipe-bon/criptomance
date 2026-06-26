package fitness;

public interface FitnessStrategy {
  int calculateFitness(int[] cromossomo);
  String getLettersSequence();
}
