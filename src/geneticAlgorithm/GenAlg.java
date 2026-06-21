package geneticAlgorithm;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import crossover.CrossoverStrategy;
import fitness.FitnessStrategy;
import parentSelection.ParentSelectionStrategy;

public class GenAlg {

  private int population_size;
  private int p_mutation;
  private ArrayList<Individual> population;
  private CrossoverStrategy crossoverStrategy;
  private ParentSelectionStrategy parentSelectionStrategy;
  private FitnessStrategy fitnessStrategy;
  private int maxGeneration = 100;
  private int crossoverRate = 60;


  public GenAlg(int p_mutation, int population_size, int maxGeneration, int crossoverRate, CrossoverStrategy crossoverStrategy, ParentSelectionStrategy parentSelectionStrategy, FitnessStrategy fitnessStrategy){
    this.crossoverStrategy = crossoverStrategy;
    this.parentSelectionStrategy = parentSelectionStrategy;
    this.fitnessStrategy = fitnessStrategy;
    this.p_mutation = p_mutation;
    this.population_size = population_size;
    this.maxGeneration = maxGeneration;
    this.crossoverRate = crossoverRate;

    this.generatePopulation();
  }

  private void generatePopulation(){
    // Gera população usando paralelismo
    this.population = IntStream.range(0, population_size)
    .parallel()
    .mapToObj(i -> new Individual(p_mutation))
    .collect(Collectors.toCollection(ArrayList::new));
  }

  // TODO
  private void selectParent(){

    return;
  }

  // TODO
  private void crossover(){

    return;
  }

  // Calcula o fitness da população usando paralelismo
  private void calculatePopulationFitness(){
    
    this.population.parallelStream().forEach(individual -> {
      int fitness = fitnessStrategy.calculateFitness(individual.getChromosome());
      individual.setFitnessValue(fitness);
    });

    return;
  }

  // TODO
  public void executeAlgorithm(){
    calculatePopulationFitness();
    return;
  }

  public ArrayList<Individual> getPopulation(){
    return population;
  }

  public String getLettersSequenc(){
    return this.fitnessStrategy.getLettersSequenc();
  }
}
