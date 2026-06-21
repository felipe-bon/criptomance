package geneticAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Individual {

  private static final int CHROMOSOME_SIZE = 10;
  private int[] chromosome = new int[CHROMOSOME_SIZE];
  private int p_mutation;
  private ArrayList<Integer> possibleGeneValues;
  private int fitnessValue;
  // private int crossoverType = 0;

  // individuo gerado para população inicial
  public Individual(int p_mutation) {

    this.p_mutation = p_mutation;
    this.possibleGeneValues = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    this.generateChromosome();
  }

  // individuo resultado de um crossover
  public Individual(int p_mutation, int[] chromosome){

    this.p_mutation = p_mutation;
    this.possibleGeneValues = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    this.chromosome = chromosome;
    this.mutate();
  }

  private void generateChromosome(){
    for(int i = 0; i < CHROMOSOME_SIZE; i++){
      int pos = ThreadLocalRandom.current().nextInt(0, this.possibleGeneValues.size());
      this.chromosome[i] = possibleGeneValues.get(pos);
      possibleGeneValues.remove(pos);
    }
  }

  public int[] getChromosome(){
    return this.chromosome;
  }

  public int getChromosomeSize(){
    return CHROMOSOME_SIZE;
  }

  public int getFitnessValue(){
    return this.fitnessValue;
  }

  public void setFitnessValue(int fitnessValue){
    this.fitnessValue = fitnessValue;
  }

  private void mutate(){

    boolean isMutate = p_mutation >= ThreadLocalRandom.current().nextInt(0, 100);

    if(!isMutate) return;

    int mutateGen1 = ThreadLocalRandom.current().nextInt(CHROMOSOME_SIZE);
    int mutateGen2 = ThreadLocalRandom.current().nextInt(CHROMOSOME_SIZE);
    int temp = this.chromosome[mutateGen1];
    
    this.chromosome[mutateGen1] = this.chromosome[mutateGen2];
    this.chromosome[mutateGen2] = temp;
    return;
  }

    @Override
    public String toString() {
        return String.format(
            "Individual{fitness=%d, chromosome=%s}",
            fitnessValue,
            Arrays.toString(chromosome)
        );
    }
}
