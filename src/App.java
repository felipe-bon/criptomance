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
import parentSelection.RouletteSelection;

public class App {
    public static void main(String[] args) throws Exception {

        LocalDateTime startTime;
        LocalDateTime endTime;
        int numberOfTests = 1;
        int[] executionTime;
        int test = 0;
        System.out.println("Iniciando sistema!");
        CryptoProblem currenProblem = new CryptoProblem("SEND", "MORE", "MONEY");

        // CONFIGURAÇÃO 1

        while(test < numberOfTests){

            startTime = LocalDateTime.now();
            GenAlg algoritmoGenetico = new GenAlg
                                (0.2f, 
                                100, 
                                50, 
                                0.6f, 
                                new PMXCrossover(), 
                                new RouletteSelection(), 
                                new FitnessGlobal(currenProblem), 
                                new PureElitismReintegration(0.2));

            algoritmoGenetico.executeAlgorithm();
            endTime = LocalDateTime.now();
            Duration duration = Duration.between(startTime, endTime);  
            // executionTime[test] = duration;
            // System.out.println("Tempo de execução: " + duration.toMillis() + " ms");    
            test++;
        }
        

        // algoritmoGenetico.getPopulation().forEach(individual->{
        //     System.out.println(individual);
        // });

        // algoritmoGenetico.getChildren().forEach(individual->{
        //     System.out.println(individual);
        // });

        System.out.println("Encerrando sistema!");
    }
}
