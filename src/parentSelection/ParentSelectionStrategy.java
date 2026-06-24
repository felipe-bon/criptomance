package parentSelection;

import java.util.ArrayList;

import geneticAlgorithm.Individual;

public interface ParentSelectionStrategy {
  ArrayList<Individual> selectParents(ArrayList<Individual> population, int numParents);
}
