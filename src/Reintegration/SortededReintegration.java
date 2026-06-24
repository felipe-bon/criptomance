package Reintegration;

import java.util.ArrayList;

import geneticAlgorithm.Individual;

public class SortededReintegration implements ReintegrationStrategy {

  @Override
  public ArrayList<Individual> reeintegration(ArrayList<Individual> currentPopulation, ArrayList<Individual> children) {

    ArrayList<Individual> combinedPopulation = new ArrayList<>(currentPopulation);
    combinedPopulation.addAll(children);

    combinedPopulation.sort(
        (individual1, individual2) -> Integer.compare(
            individual1.getFitnessValue(),
            individual2.getFitnessValue()));

    ArrayList<Individual> newPopulation = new ArrayList<>(combinedPopulation.subList(0, currentPopulation.size()));
    return newPopulation;
  }
}
