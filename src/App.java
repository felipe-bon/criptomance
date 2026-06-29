import Reintegration.PureElitismReintegration;
import crossover.PMXCrossover;
import fitness.FitnessGlobal;
import geneticAlgorithm.CryptoProblem;
import geneticAlgorithm.GenAlg;
import parentSelection.TournamentSelection;

public class App {
  public static void main(String[] args) {

    System.out.println("Iniciando Sistema!");
    CryptoProblem currentProblem = new CryptoProblem("SEND", "MORE", "MONEY");
    System.out
        .println("Problema: " + currentProblem.term1 + " + " + currentProblem.term2 + " = " + currentProblem.result);

    GenAlg algoritmoGenetico = new GenAlg(
        0.35f,
        200,
        50,
        0.9f,
        new PMXCrossover(),
        new TournamentSelection(7),
        new FitnessGlobal(currentProblem),
        new PureElitismReintegration(0.1));

    System.out.println("Resultado: ");

    algoritmoGenetico.executeAlgorithm();
    System.out.println(algoritmoGenetico.getResult());
    System.out.println(algoritmoGenetico.getBestIndividual());

    System.out.println("Encerrando Sistema!");
  }
}
