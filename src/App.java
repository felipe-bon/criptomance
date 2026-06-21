import java.util.ArrayList;
import java.util.Arrays;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Iniciando sistema!");

        ArrayList<Individual> population = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            Individual individual = new Individual(0.2);
            population.add(individual);
        }

        for(int i = 0; i < population.size(); i++){
            System.out.println(Arrays.toString(population.get(i).getChromosome()));
        }

        System.out.println("Encerrando sistema!");
    }
}
