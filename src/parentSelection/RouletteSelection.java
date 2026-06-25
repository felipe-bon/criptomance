package parentSelection;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import geneticAlgorithm.Individual;

public class RouletteSelection implements ParentSelectionStrategy {

  @Override
  public ArrayList<Individual> selectParents(ArrayList<Individual> population, int numParents) {

    ArrayList<Individual> parents = new ArrayList<>(numParents);

    double totalFitness = 0.0;

    for (Individual individual : population) {
      totalFitness += 1.0 / (1.0 + individual.getFitnessValue());
    }

    for (int i = 0; i < numParents; i++) {

      double randomValue = ThreadLocalRandom.current().nextDouble(totalFitness);

      double accumulatedFitness = 0.0;

      for (Individual individual : population) {

        accumulatedFitness += 1.0 / (1.0 + individual.getFitnessValue());

        if (accumulatedFitness >= randomValue) {
          parents.add(individual);
          break;
        }
      }
    }

    return parents;
  }
}