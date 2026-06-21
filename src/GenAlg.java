import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GenAlg {

  int population_size;
  int p_mutation;
  ArrayList<Individual> population;


  public GenAlg(int p_mutation, int population_size){
    this.p_mutation = p_mutation;
    this.population_size = population_size;
  }

  public void generatePopulation(){
    this.population = IntStream.range(0, population_size)
    .parallel()
    .mapToObj(i -> new Individual(p_mutation))
    .collect(Collectors.toCollection(ArrayList::new));

    System.out.println(population);
  }

  public ArrayList<Individual> getPopulation(){
    return population;
  }
}
