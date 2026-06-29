package geneticAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Individual {

  private static final int CHROMOSOME_SIZE = 10;
  private int[] chromosome = new int[CHROMOSOME_SIZE];
  private float mutationProbability;
  private ArrayList<Integer> possibleGeneValues;
  private int fitnessValue;

  // individuo gerado para população inicial
  public Individual(float mutationProbability) {

    this.mutationProbability = mutationProbability;
    this.possibleGeneValues = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    this.generateChromosome();
  }

  // individuo resultado de um crossover
  public Individual(float mutationProbability, int[] chromosome){

    this.mutationProbability = mutationProbability;
    this.possibleGeneValues = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    this.chromosome = chromosome;

    // pode sofrer mutação sempre que gerado a partir de um crossover
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

    boolean isMutate = mutationProbability >= ThreadLocalRandom.current().nextFloat(0, 1);

    if(!isMutate) return;

    int mutateGen1 = ThreadLocalRandom.current().nextInt(CHROMOSOME_SIZE);
    int mutateGen2 = ThreadLocalRandom.current().nextInt(CHROMOSOME_SIZE);
    int temp = this.chromosome[mutateGen1];
    
    this.chromosome[mutateGen1] = this.chromosome[mutateGen2];
    this.chromosome[mutateGen2] = temp;
    return;
  }

  public float getMutationProbability() {
    return mutationProbability;
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
