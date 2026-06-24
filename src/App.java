import java.time.Duration;
import java.time.LocalDateTime;

import Reintegration.ReintegrationStrategy;
import Reintegration.SortededReintegration;
import crossover.CrossoverStrategy;
import crossover.PMXCrossover;
import fitness.FitnessGlobal;
import fitness.FitnessStrategy;
import geneticAlgorithm.CryptoProblem;
import geneticAlgorithm.GenAlg;
import parentSelection.ParentSelectionStrategy;
import parentSelection.TournamentSelection;

public class App {
    public static void main(String[] args) throws Exception {

        LocalDateTime startTime;
        LocalDateTime endTime;
        System.out.println("Iniciando sistema!");

        CryptoProblem currenProblem = new CryptoProblem("send", "more", "money");

        CrossoverStrategy crossoverMethod = new PMXCrossover();
        ParentSelectionStrategy selectionParentMethod = new TournamentSelection();
        FitnessStrategy fitnessMethod = new FitnessGlobal(currenProblem);
        ReintegrationStrategy reintegrationMethod = new SortededReintegration();

        GenAlg algoritmoGenetico = new GenAlg
                                        (20, 
                                        100, 
                                        50, 
                                        60, 
                                        crossoverMethod, 
                                        selectionParentMethod, 
                                        fitnessMethod, 
                                        reintegrationMethod);

        startTime = LocalDateTime.now();                                    
        algoritmoGenetico.executeAlgorithm();
        endTime = LocalDateTime.now();

        System.out.println(algoritmoGenetico.getLettersSequenc());
        
        System.out.println(algoritmoGenetico.getBestIndividual());
        Duration duration = Duration.between(startTime, endTime);
        System.out.println("Tempo de execução: " + duration.toMillis() + " ms");
        

        // algoritmoGenetico.getPopulation().forEach(individual->{
        //     System.out.println(individual);
        // });

        // algoritmoGenetico.getChildren().forEach(individual->{
        //     System.out.println(individual);
        // });

        System.out.println("Encerrando sistema!");
    }
}
