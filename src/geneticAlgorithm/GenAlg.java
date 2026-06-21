package geneticAlgorithm;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import crossover.CrossoverStrategy;
import parentSelection.ParentSelectionStrategy;

public class GenAlg {

  private int population_size;
  private int p_mutation;
  private ArrayList<Individual> population;
  private CrossoverStrategy crossoverStrategy;
  private ParentSelectionStrategy parentSelectionStrategy;


  public GenAlg(int p_mutation, int population_size, CrossoverStrategy crossoverStrategy, ParentSelectionStrategy parentSelectionStrategy){
    this.crossoverStrategy = crossoverStrategy;
    this.parentSelectionStrategy = parentSelectionStrategy;
    this.p_mutation = p_mutation;
    this.population_size = population_size;
    this.generatePopulation();
  }

  private void generatePopulation(){
    this.population = IntStream.range(0, population_size)
    .parallel()
    .mapToObj(i -> new Individual(p_mutation))
    .collect(Collectors.toCollection(ArrayList::new));

    System.out.println(population);
  }

  // TODO
  private void selectParent(){

    return;
  }

  // TODO
  private void crossover(){

    return;
  }

  // TODO
  private void calculateFitness(Individual[] individuals){
    
    return;
  }

  // TODO
  private void executeAlgorithm(){

    return;
  }

  public ArrayList<Individual> getPopulation(){
    return population;
  }
}
