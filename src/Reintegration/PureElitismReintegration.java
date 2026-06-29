package Reintegration;

import java.util.ArrayList;

import geneticAlgorithm.Individual;

public class PureElitismReintegration implements ReintegrationStrategy {

  private double elitismRate;

  // O construtor recebe a taxa de elitismo (ex: 0.20 para 20%)
  public PureElitismReintegration(double elitismRate) {
      this.elitismRate = elitismRate;
  }

  @Override
  public ArrayList<Individual> reeintegration(ArrayList<Individual> currentPopulation, ArrayList<Individual> children) {

    int populationSize = currentPopulation.size();
    
    int eliteCount = (int) (populationSize * elitismRate);

    currentPopulation.sort(
        (individual1, individual2) -> Integer.compare(
            individual1.getFitnessValue(),
            individual2.getFitnessValue()));

    ArrayList<Individual> newPopulation = new ArrayList<>(currentPopulation.subList(0, eliteCount));

    newPopulation.addAll(children);

    return newPopulation;
  }
}