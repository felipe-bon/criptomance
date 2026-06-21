import crossover.CrossoverStrategy;
import crossover.CyclicCrossover;
import fitness.FitnessGlobal;
import fitness.FitnessStrategy;
import geneticAlgorithm.CryptoProblem;
import geneticAlgorithm.GenAlg;
import parentSelection.ParentSelectionStrategy;
import parentSelection.TournamentSelection;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Iniciando sistema!");

        CryptoProblem currenProblem = new CryptoProblem("send", "more", "money");

        CrossoverStrategy crossoverMethod = new CyclicCrossover();
        ParentSelectionStrategy selectionParentMethod = new TournamentSelection();
        FitnessStrategy fitnessMethod = new FitnessGlobal(currenProblem);

        GenAlg algoritmoGenetico = new GenAlg(20, 100, 50, 60, crossoverMethod, selectionParentMethod, fitnessMethod);
        algoritmoGenetico.executeAlgorithm();

        System.out.println(algoritmoGenetico.getLettersSequenc());

        algoritmoGenetico.getPopulation().forEach(individual->{
            System.out.println(individual);
        });

        System.out.println("Encerrando sistema!");
    }
}
