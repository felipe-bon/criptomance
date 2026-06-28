
import java.time.Duration;
import java.time.LocalDateTime;

import Reintegration.PureElitismReintegration;
import Reintegration.ReintegrationStrategy;
import Reintegration.SortededReintegration;
import crossover.CrossoverStrategy;
import crossover.PMXCrossover;
import fitness.FitnessGlobal;
import fitness.FitnessPositional;
import fitness.FitnessStrategy;
import geneticAlgorithm.CryptoProblem;
import geneticAlgorithm.GenAlg;
import parentSelection.ParentSelectionStrategy;
import parentSelection.TournamentSelection;

public class Etapa3Teste {

  public static void main(String[] args) throws Exception {

    LocalDateTime startTime;
    LocalDateTime endTime;
    System.out.println("Iniciando sistema!");

    CryptoProblem currenProblem = new CryptoProblem("DONALD", "GERALD", "ROBERT");

    // CrossoverStrategy crossoverMethod = new PMXCrossover();
    // ParentSelectionStrategy selectionParentMethod = new TournamentSelection();
    // FitnessStrategy fitnessMethod = new FitnessGlobal(currenProblem);
    // ReintegrationStrategy reintegrationMethod = new SortededReintegration();

    GenAlg algoritmoGenetico = new GenAlg(0.2f,
        300,
        80,
        0.5f,
        new PMXCrossover(),
        new TournamentSelection(5),
        new FitnessPositional(currenProblem),
        new PureElitismReintegration(0.3));

    startTime = LocalDateTime.now();
    algoritmoGenetico.executeAlgorithm();
    endTime = LocalDateTime.now();

    System.out.println(algoritmoGenetico.getLettersSequenc());

    System.out.println(algoritmoGenetico.getBestIndividual());
    Duration duration = Duration.between(startTime, endTime);
    System.out.println("Tempo de execução: " + duration.toMillis() + " ms");

    // algoritmoGenetico.getPopulation().forEach(individual->{
    // System.out.println(individual);
    // });

    // algoritmoGenetico.getChildren().forEach(individual->{
    // System.out.println(individual);
    // });
    System.out.println(algoritmoGenetico.getBestFitnessPerGeneration());
    System.out.println("Encerrando sistema!");
  }
}