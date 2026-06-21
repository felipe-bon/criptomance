import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Individual {

  private int c_size = 10;
  private double p_mutation;
  private ArrayList<Integer> possibleGeneValues;
  private int[] chromosome = new int[c_size];
  private int fitnessValue;
  private int crossoverType = 0;

  public Individual(double p_mutation) {
    this.p_mutation = p_mutation;
    this.possibleGeneValues = new ArrayList<>(List.of(-2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    this.generateChromosome();
  }

  private void generateChromosome(){
    
    Random gerador = new Random();

    for(int i = 0; i < this.c_size; i++){
      int pos = gerador.nextInt(0, this.possibleGeneValues.size());
      this.chromosome[i] = possibleGeneValues.get(pos);
      possibleGeneValues.remove(pos);
    }
  }

  public int[] getChromosome(){
    return this.chromosome;
  }

  public int getChromosomeSize(){
    return c_size;
  }

  public int getFitnessValue(){
    return this.fitnessValue;
  }

  public void setFitnessValue(int fitnessValue){
    this.fitnessValue = fitnessValue;
  }

  private void mutate(){
    return;
  }

  // TODO
  // private Individual[] crossover(Individual parent){

  //   if (this.crossoverType == 0) {


      
  //   }

  //   return suns;
  // }
}
