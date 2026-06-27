package crossover;

import geneticAlgorithm.Individual;

public class CyclicCrossover implements CrossoverStrategy {

  @Override
  public Individual[] crossover(Individual parent1, Individual parent2) {
    int[] p1 = parent1.getChromosome();
    int[] p2 = parent2.getChromosome();
    int size = p1.length;

    int[] child1Chromo = new int[size];
    int[] child2Chromo = new int[size];
    boolean[] visited = new boolean[size];

    // Inicializa com -1 para seguranca
    for (int i = 0; i < size; i++) {
        child1Chromo[i] = -1;
        child2Chromo[i] = -1;
    }

    boolean fromParent1 = true; // Controla de qual pai o filho 1 recebe na iteracao atual

    for (int i = 0; i < size; i++) {
        if (!visited[i]) {
            int current = i;
            while (!visited[current]) {
                visited[current] = true;
                
                if (fromParent1) {
                    child1Chromo[current] = p1[current];
                    child2Chromo[current] = p2[current];
                } else {
                    child1Chromo[current] = p2[current];
                    child2Chromo[current] = p1[current];
                }

                // Encontra a posicao do valor de p2[current] em p1 para continuar o ciclo
                int valToFind = p2[current];
                for (int j = 0; j < size; j++) {
                    if (p1[j] == valToFind) {
                        current = j;
                        break;
                    }
                }
            }
            // Alterna a origem para o proximo ciclo
            fromParent1 = !fromParent1;
        }
    }

    Individual filho1 = new Individual(parent1.getMutationProbability(), child1Chromo);
    Individual filho2 = new Individual(parent2.getMutationProbability(), child2Chromo);

    return new Individual[] { filho1, filho2 };
  }
}
