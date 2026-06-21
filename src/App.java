import crossover.CrossoverStrategy;
import crossover.CyclicCrossover;
import geneticAlgorithm.GenAlg;
import parentSelection.ParentSelectionStrategy;
import parentSelection.TournamentSelection;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Iniciando sistema!");

        CrossoverStrategy crossoverMethod = new CyclicCrossover();
        ParentSelectionStrategy selectionParentMethod = new TournamentSelection();

        GenAlg algoritmoGenetico = new GenAlg(20, 100, crossoverMethod, selectionParentMethod);
        algoritmoGenetico.getPopulation();

        System.out.println("Encerrando sistema!");
    }
}
