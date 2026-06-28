package geneticAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import Reintegration.ReintegrationStrategy;
import crossover.CrossoverStrategy;
import fitness.FitnessStrategy;
import parentSelection.ParentSelectionStrategy;

public class GenAlg {

  private int population_size;
  private float p_mutation;
  private CrossoverStrategy crossoverStrategy;
  private ParentSelectionStrategy parentSelectionStrategy;
  private FitnessStrategy fitnessStrategy;
  private ReintegrationStrategy reintegrationStrategy;
  private int maxGeneration = 100;
  private float crossoverRate = 0.6f;
  private ArrayList<Individual> population;
  private ArrayList<Individual> parents;
  private ArrayList<Individual> children;
  private ArrayList<Integer> bestFitnessPerGeneration;

  // VARIÁVEIS PARA O CONTROLE ADAPTATIVO
  private boolean useAdaptiveMutation;
  private float currentMutationRate;

  // CONSTRUTOR ORIGINAL (Mantido para testes sem adaptação)
  public GenAlg(float p_mutation, int population_size, int maxGeneration, float crossoverRate,
      CrossoverStrategy crossoverStrategy, ParentSelectionStrategy parentSelectionStrategy,
      FitnessStrategy fitnessStrategy, ReintegrationStrategy reintegrationStrategy) {

    // Chama o construtor sobrecarregado passando "false" para a adaptação por
    // padrão
    this(p_mutation, population_size, maxGeneration, crossoverRate, crossoverStrategy,
        parentSelectionStrategy, fitnessStrategy, reintegrationStrategy, false);
  }

  // NOVO CONSTRUTOR SOBRECARREGADO (Opcionalidade do Controle Adaptativo)
  public GenAlg(float p_mutation, int population_size, int maxGeneration, float crossoverRate,
      CrossoverStrategy crossoverStrategy, ParentSelectionStrategy parentSelectionStrategy,
      FitnessStrategy fitnessStrategy, ReintegrationStrategy reintegrationStrategy,
      boolean useAdaptiveMutation) {

    this.crossoverStrategy = crossoverStrategy;
    this.parentSelectionStrategy = parentSelectionStrategy;
    this.fitnessStrategy = fitnessStrategy;
    this.reintegrationStrategy = reintegrationStrategy;
    this.p_mutation = p_mutation;
    this.population_size = population_size;
    this.maxGeneration = maxGeneration;
    this.crossoverRate = crossoverRate;
    this.bestFitnessPerGeneration = new ArrayList<>(maxGeneration);

    // Configura o controle adaptativo
    this.useAdaptiveMutation = useAdaptiveMutation;
    this.currentMutationRate = p_mutation; // A taxa atual começa igual à taxa base (ex: 25%)

    this.generatePopulation();
  }

  private void generatePopulation() {
    // Gera população usando paralelismo
    this.population = IntStream.range(0, population_size)
        .parallel()
        .mapToObj(i -> new Individual(p_mutation))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  private void selectParent() {
    int numberOfParents = (int) (population_size * crossoverRate);
    this.parents = this.parentSelectionStrategy.selectParents(population, numberOfParents);
  }

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

    // APLICAÇÃO EFETIVA DO CONTROLE ADAPTATIVO
    // Aqui usamos a variável currentMutationRate nos filhos recém-gerados
    this.children.parallelStream().forEach(child -> {
        // Você precisará garantir que a sua classe Individual (ou sua estratégia de mutação)
        // possua um método que receba a taxa atual para realizar a perturbação
        child.mutate(this.currentMutationRate); 
    });

    // Só depois de sofrerem mutação (com a taxa base de 25% ou a agressiva de 50%) 
    // é que os filhos são avaliados matematicamente
    calculatePopulationFitness(this.children);
  }

  private void reeintegration() {
    this.population = reintegrationStrategy.reeintegration(population, children);
  }

  private void calculatePopulationFitness(ArrayList<Individual> population) {
    population.parallelStream().forEach(individual -> {
      int fitness = fitnessStrategy.calculateFitness(individual.getChromosome());
      individual.setFitnessValue(fitness);
    });
  }

  public void executeAlgorithm() {
    int currentGenration = 0;
    calculatePopulationFitness(population);

    int bestFiteness = getBestIndividual().getFitnessValue();
    bestFitnessPerGeneration.add(bestFiteness);

    // Variáveis locais de controle de estagnação
    int previousBestFitness = bestFiteness;
    int generationsWithoutImprovement = 0;
    int stagnationLimit = 10; // Limite de gerações estagnado (Regra Fixa)

    while (currentGenration < maxGeneration && bestFiteness > 0) {

      // ----------- AVALIAÇÃO DO CONTROLE ADAPTATIVO -----------
      if (useAdaptiveMutation) {
        if (bestFiteness >= previousBestFitness) {
          generationsWithoutImprovement++; // Algoritmo estagnou
        } else {
          generationsWithoutImprovement = 0; // Algoritmo evoluiu!
          this.currentMutationRate = this.p_mutation; // Reseta para a taxa base
        }

        // Regra de perturbação: se estagnar, aumenta drasticamente a mutação
        if (generationsWithoutImprovement >= stagnationLimit) {
          this.currentMutationRate = 0.50f; // Sobe para 50% para forçar fuga do ótimo local
        }
      }
      // --------------------------------------------------------

      selectParent();

      // O método crossover agora usará indiretamente a this.currentMutationRate
      crossover();

      reeintegration();

      previousBestFitness = bestFiteness;
      bestFiteness = getBestIndividual().getFitnessValue();

      // Se não for a geração 0 (já adicionada antes do loop), adiciona o melhor da
      // geração atual
      if (currentGenration > 0) {
        bestFitnessPerGeneration.add(bestFiteness);
      }
      if (bestFiteness < 50) {
        aplicarMacroMutacaoNaPopulacao();
        Individual melhor = getBestIndividual();
        applyLocalSearch(melhor);
      }
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
    return this.fitnessStrategy.getLettersSequence();
  }

  public ArrayList<Individual> getChildren() {
    return children;
  }

  public ArrayList<Integer> getBestFitnessPerGeneration() {
    return this.bestFitnessPerGeneration;
  }

  private void applyLocalSearch(Individual individual) {
    int[] chromosome = individual.getChromosome();
    int currentFitness = individual.getFitnessValue();

    // Double loop to test all possible swaps of 2 genes
    // For a vector of size 10, this tests exactly the 45 possible combinations
    for (int i = 0; i < chromosome.length - 1; i++) {
      for (int j = i + 1; j < chromosome.length; j++) {

        // Creates a temporary copy of the chromosome to test the neighborhood without breaking the original
        int[] tempChromosome = chromosome.clone();

        // Performs the swap between position i and j
        int tempGene = tempChromosome[i];
        tempChromosome[i] = tempChromosome[j];
        tempChromosome[j] = tempGene;

        // Evaluates the fitness of this modified "neighbor"
        int neighborFitness = fitnessStrategy.calculateFitness(tempChromosome);

        // If the swap resulted in a better fitness (e.g., dropping from 1 to 0)
        if (neighborFitness < currentFitness) {
          
          // The individual learned and improved. We update its genetic material permanently.
          // Note: ensure your Individual class has a setChromosome(int[] chromosome) method.
          individual.setChromosome(tempChromosome);
          individual.setFitnessValue(neighborFitness);
          
          // As the goal is just the final push (refinement) to escape a local optimum, we end the search
          return;
        }
      }
    }
  }

    private void aplicarMacroMutacaoNaPopulacao() {
      // Ignoramos o indivíduo  para preservar a melhor solução atual por garantia
      for (int i = 1; i < population.size(); i++) {
          int[] chromo = population.get(i).getChromosome();
          
          // Realiza 3 trocas aleatórias (afetando até 6 genes diferentes no mesmo indivíduo)
          for(int trocas = 0; trocas < 3; trocas++) {
              int pos1 = (int) (Math.random() * chromo.length);
              int pos2 = (int) (Math.random() * chromo.length);
              
              int temp = chromo[pos1];
              chromo[pos1] = chromo[pos2];
              chromo[pos2] = temp;
          }
          // Atualiza o cromossomo
          population.get(i).setChromosome(chromo);
          population.get(i).setFitnessValue(fitnessStrategy.calculateFitness(chromo));
      }
  }

}