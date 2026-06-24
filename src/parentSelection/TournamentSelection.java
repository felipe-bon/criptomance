package parentSelection;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import geneticAlgorithm.Individual;

public class TournamentSelection implements ParentSelectionStrategy {

  @Override
  public ArrayList<Individual> selectParents(ArrayList<Individual> population, int numParents) {
    int populationSize = population.size();

    // Parâmetro fixo exigido pela Etapa 1 do trabalho: torneio com tour de 3
    int tourSize = 3;

    // Cria um fluxo paralelo que executará simultaneamente 'numParents' vezes
    ArrayList<Individual> parents = IntStream.range(0, numParents)
        .parallel() // Ativa o processamento em múltiplas threads
        .mapToObj(i -> {
          Individual bestInTour = null;

          // Lógica do Torneio (executada de forma isolada em cada thread)
          for (int j = 0; j < tourSize; j++) {
            // ThreadLocalRandom é altamente eficiente e seguro para paralelismo
            int randomIndex = ThreadLocalRandom.current().nextInt(populationSize);
            Individual candidate = population.get(randomIndex);

            // Minimização: o melhor indivíduo é o que tem o menor erro (fitness mais
            // próximo de 0)
            if (bestInTour == null || candidate.getFitnessValue() < bestInTour.getFitnessValue()) {
              bestInTour = candidate;
            }
          }

          // Retorna o vencedor do torneio desta thread específica
          return bestInTour;
        })
        // Coleta todos os vencedores gerados paralelamente e os agrupa em um ArrayList
        .collect(Collectors.toCollection(ArrayList::new));

    return parents;
  }
}
