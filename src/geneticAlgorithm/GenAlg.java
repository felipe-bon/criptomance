package geneticAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import Reintegration.ReintegrationStrategy;
import crossover.CrossoverStrategy;
import fitness.FitnessStrategy;
import parentSelection.ParentSelectionStrategy;

public class GenAlg {

  private int population_size;
  private int p_mutation;
  private CrossoverStrategy crossoverStrategy;
  private ParentSelectionStrategy parentSelectionStrategy;
  private FitnessStrategy fitnessStrategy;
  private ReintegrationStrategy reintegrationStrategy;
  private int maxGeneration = 100;
  private int crossoverRate = 60; // em porcentagem
  private ArrayList<Individual> population;
  private ArrayList<Individual> parents;
  private ArrayList<Individual> children;

  public GenAlg(int p_mutation, int population_size, int maxGeneration, int crossoverRate,
      CrossoverStrategy crossoverStrategy, ParentSelectionStrategy parentSelectionStrategy,
      FitnessStrategy fitnessStrategy, ReintegrationStrategy reintegrationStrategy) {
    this.crossoverStrategy = crossoverStrategy;
    this.parentSelectionStrategy = parentSelectionStrategy;
    this.fitnessStrategy = fitnessStrategy;
    this.reintegrationStrategy = reintegrationStrategy;
    this.p_mutation = p_mutation;
    this.population_size = population_size;
    this.maxGeneration = maxGeneration;
    this.crossoverRate = crossoverRate;

    this.generatePopulation();
  }

  private void generatePopulation() {
    // Gera população usando paralelismo
    this.population = IntStream.range(0, population_size)
        .parallel()
        .mapToObj(i -> new Individual(p_mutation))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  // TODO
  private void selectParent() {
    this.parents = this.parentSelectionStrategy.selectParents(population, crossoverRate);
    return;
  }

  // TODO
  private void crossover() {
    this.children = IntStream.range(0, parents.size() / 2)
        .parallel()
        .mapToObj(i -> {
          Individual parent1 = population.get(i * 2);
          Individual parent2 = population.get(i * 2 + 1);

          return crossoverStrategy.crossover(parent1, parent2);
        })
        .flatMap(Arrays::stream)
        .collect(Collectors.toCollection(ArrayList::new));

    calculatePopulationFitness(this.children);
  }

  private void reeintegration() {
    this.population = reintegrationStrategy.reeintegration(population, children);
  }

  // Calcula o fitness da população usando paralelismo
  private void calculatePopulationFitness(ArrayList<Individual> population) {

    population.parallelStream().forEach(individual -> {
      int fitness = fitnessStrategy.calculateFitness(individual.getChromosome());
      individual.setFitnessValue(fitness);
    });

    return;
  }

  // TODO
  public void executeAlgorithm() {

    int currentGenration = 0;

    calculatePopulationFitness(population);
    while (currentGenration < maxGeneration && getBestIndividual().getFitnessValue() > 0) {
      selectParent();
      crossover();
      reeintegration();
      currentGenration++;
    }
  }

  public ArrayList<Individual> getPopulation() {
    return population;
  }

  public Individual getBestIndividual() {
    return population.stream()
        .min((i1, i2) -> Integer.compare(
            i1.getFitnessValue(),
            i2.getFitnessValue()))
        .orElse(null);
  }

  public String getLettersSequenc() {
    return this.fitnessStrategy.getLettersSequenc();
  }

  public ArrayList<Individual> getChildren() {
    return children;
  }
}
