package crossover;

import java.util.concurrent.ThreadLocalRandom;

import geneticAlgorithm.Individual;

public class PMXCrossover implements CrossoverStrategy {

  @Override
  public Individual[] crossover(Individual parent1, Individual parent2) {
    int[] p1 = parent1.getChromosome();
    int[] p2 = parent2.getChromosome();
    int size = p1.length; // Como discutimos, tamanho fixo de 10 posições

    int[] child1Chromo = new int[size];
    int[] child2Chromo = new int[size];

    // Passo 1: Seleciona uma seção do cromossomo (2 pontos de corte) [2]
    int cut1 = ThreadLocalRandom.current().nextInt(0, size - 1);
    int cut2 = ThreadLocalRandom.current().nextInt(cut1 + 1, size);

    // Passo 2: Troca todo o material genético da seção entre os 2 pais [2]
    // O filho 1 herda o miolo do pai 2, e o filho 2 herda o miolo do pai 1
    for (int i = cut1; i <= cut2; i++) {
      child1Chromo[i] = p2[i];
      child2Chromo[i] = p1[i];
    }

    // Passo 3: Faz mudanças para eliminar repetições nos filhos (gene a gene) [2]

    // Preenchendo as extremidades do Filho 1
    for (int i = 0; i < size; i++) {
      if (i >= cut1 && i <= cut2)
        continue; // Pula a seção central que já foi preenchida

      int candidato = p1[i]; // O gene original que o filho deveria herdar do Pai 1

      // Verifica se o gene original já foi inserido no miolo (conflito)
      while (contains(child1Chromo, candidato, cut1, cut2)) {
        // Mapeamento: Encontra onde esse candidato está no miolo do Pai 2
        int indexInP2 = indexOf(p2, candidato, cut1, cut2);
        // Pega o valor na mesma posição correspondente do Pai 1 para substituir
        candidato = p1[indexInP2];
      }
      child1Chromo[i] = candidato;
    }

    // Preenchendo as extremidades do Filho 2 (mesma lógica, invertendo os pais)
    for (int i = 0; i < size; i++) {
      if (i >= cut1 && i <= cut2)
        continue;

      int candidato = p2[i];
      while (contains(child2Chromo, candidato, cut1, cut2)) {
        int indexInP1 = indexOf(p1, candidato, cut1, cut2);
        candidato = p2[indexInP1];
      }
      child2Chromo[i] = candidato;
    }

    Individual filho1 = new Individual(parent1.getMutationProbability(), child1Chromo);
    Individual filho2 = new Individual(parent2.getMutationProbability(), child2Chromo);

    return new Individual[] { filho1, filho2 };
  }

  // ==========================================
  // Funções auxiliares exigidas pela lógica PMX
  // ==========================================

  /**
   * Verifica se um valor existe em um determinado segmento do vetor
   */
  private boolean contains(int[] array, int value, int start, int end) {
    for (int i = start; i <= end; i++) {
      if (array[i] == value)
        return true;
    }
    return false;
  }

  /**
   * Retorna o índice de um determinado valor dentro de um segmento do vetor
   */
  private int indexOf(int[] array, int value, int start, int end) {
    for (int i = start; i <= end; i++) {
      if (array[i] == value)
        return i;
    }
    return -1;
  }
}
