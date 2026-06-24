package Reintegration;

import java.util.ArrayList;

import geneticAlgorithm.Individual;

public interface ReintegrationStrategy {
  public ArrayList<Individual> reeintegration(ArrayList<Individual> parents, ArrayList<Individual> children);
}
