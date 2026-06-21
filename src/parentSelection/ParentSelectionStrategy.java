package parentSelection;

import geneticAlgorithm.Individual;

public interface ParentSelectionStrategy {
  Individual[] selectParents(Individual[] population);
}
