package crossover;

import geneticAlgorithm.Individual;

public interface CrossoverStrategy {
  Individual[] crossover(Individual parent1, Individual parent2);
}
