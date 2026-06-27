import Reintegration.PureElitismReintegration;
import crossover.PMXCrossover;
import fitness.FitnessGlobal;
import fitness.FitnessStrategy;
import geneticAlgorithm.CryptoProblem;
import geneticAlgorithm.GenAlg;
import parentSelection.TournamentSelection;

import java.time.Duration;
import java.time.LocalDateTime;

public class Etapa3Comparacao {

    public static void main(String[] args) {
        System.out.println("Iniciando comparacao - Melhor config Etapa 2 (V13) nos 5 problemas da Etapa 3...\n");

        CryptoProblem[] problemas = {
            new CryptoProblem("SEND",   "MORE",   "MONEY"),
            new CryptoProblem("EAT",    "THAT",   "APPLE"),
            new CryptoProblem("CROSS",  "ROADS",  "DANGER"),
            new CryptoProblem("COCA",   "COLA",   "OASIS"),
            new CryptoProblem("DONALD", "GERALD", "ROBERT")
        };

        String[] nomesProblemas = {
            "SEND + MORE = MONEY",
            "EAT + THAT = APPLE",
            "CROSS + ROADS = DANGER",
            "COCA + COLA = OASIS",
            "DONALD + GERALD = ROBERT"
        };

        int numeroDeTestes = 1000;

        // Config base da Etapa 2 - Variacao 13
        // Pop:200, Gen:50, Tor:7, TM:35%, Elit:10%, Fitness Global
        float taxaMutacao   = 0.35f;
        float taxaCrossover = 0.90f;
        int   populacao     = 200;
        int   geracoes      = 50;
        int   tourneio      = 7;
        double elitismo     = 0.10;

        double totalConv = 0;
        double totalTempo = 0;

        System.out.printf("%-28s | %16s | %15s%n", "Problema", "Convergencia (%)", "Tempo Medio (ms)");
        System.out.println("-".repeat(65));

        for (int pi = 0; pi < problemas.length; pi++) {
            CryptoProblem problema = problemas[pi];
            int convergencias = 0;
            long tempoTotal = 0;

            for (int t = 0; t < numeroDeTestes; t++) {
                LocalDateTime inicio = LocalDateTime.now();

                FitnessStrategy fitness = new FitnessGlobal(problema);
                GenAlg ag = new GenAlg(
                    taxaMutacao,
                    populacao,
                    geracoes,
                    taxaCrossover,
                    new PMXCrossover(),
                    new TournamentSelection(tourneio),
                    fitness,
                    new PureElitismReintegration(elitismo)
                );

                ag.executeAlgorithm();

                LocalDateTime fim = LocalDateTime.now();
                tempoTotal += Duration.between(inicio, fim).toMillis();

                if (ag.getBestIndividual().getFitnessValue() == 0) {
                    convergencias++;
                }
            }

            double conv  = convergencias * 100.0 / numeroDeTestes;
            double tempo = tempoTotal / (double) numeroDeTestes;
            totalConv  += conv;
            totalTempo += tempo;

            System.out.printf("%-28s | %15.2f%% | %14.2f%n", nomesProblemas[pi], conv, tempo);
        }

        System.out.println("-".repeat(65));
        System.out.printf("%-28s | %15.2f%% | %14.2f%n",
            "MEDIA (5 problemas)",
            totalConv  / problemas.length,
            totalTempo / problemas.length);
        System.out.println("\nEncerrando!");
    }
}