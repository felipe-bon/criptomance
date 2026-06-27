package Reintegration;

import java.util.ArrayList;

import geneticAlgorithm.Individual;

public class PureElitismReintegration implements ReintegrationStrategy {

  private double elitismRate;

  // O construtor recebe a taxa de elitismo (ex: 0.20 para 20%)
  // Isso mantém a classe flexível caso você queira testar outras taxas na Etapa 2
  public PureElitismReintegration(double elitismRate) {
      this.elitismRate = elitismRate;
  }

  @Override
  public ArrayList<Individual> reeintegration(ArrayList<Individual> currentPopulation, ArrayList<Individual> children) {

    int populationSize = currentPopulation.size();
    
    // Calcula a quantidade exata de indivíduos da elite (Ex: 20% de 100 = 20 indivíduos)
    int eliteCount = (int) (populationSize * elitismRate);

    // Passo 1: Ordena APENAS a população atual (pais) para encontrar a "Elite"
    // Mantemos a minimização: o melhor é quem tem o menor erro matemático
    currentPopulation.sort(
        (individual1, individual2) -> Integer.compare(
            individual1.getFitnessValue(),
            individual2.getFitnessValue()));

    // Passo 2: Salva a elite. Corta o topo da lista de pais e os coloca na nova geração
    ArrayList<Individual> newPopulation = new ArrayList<>(currentPopulation.subList(0, eliteCount));

    // Passo 3: Reinserção Pura. Preenche o restante da população com TODOS os filhos gerados
    // Como a taxa de crossover (80%) é complementar ao elitismo (20%), os filhos preenchem a lista com exatidão.
    newPopulation.addAll(children);

    return newPopulation;
  }
}