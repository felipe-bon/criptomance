import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Reintegration.PureElitismReintegration;
import Reintegration.ReintegrationStrategy;
import crossover.CrossoverStrategy;
import crossover.PMXCrossover;
import fitness.FitnessGlobal;
import fitness.FitnessPosicional;
import fitness.FitnessStrategy;
import geneticAlgorithm.CryptoProblem;
import geneticAlgorithm.GenAlg;
import parentSelection.ParentSelectionStrategy;
import parentSelection.TournamentSelection;

public class Etapa3 {

    static class ConfigResult {
        String nomeConfiguracao;
        String descricao;
        double percentualConvergencia;
        double tempoMedioMs;
        
        ConfigResult(String nomeConfiguracao, String descricao) {
            this.nomeConfiguracao = nomeConfiguracao;
            this.descricao = descricao;
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Iniciando sistema - Etapa 3 (Generalizacao sobre 5 problemas)...");
        
        CryptoProblem[] problemas = new CryptoProblem[] {
            new CryptoProblem("SEND", "MORE", "MONEY"),
            new CryptoProblem("EAT", "THAT", "APPLE"),
            new CryptoProblem("CROSS", "ROADS", "DANGER"),
            new CryptoProblem("COCA", "COLA", "OASIS"),
            new CryptoProblem("DONALD", "GERALD", "ROBERT")
        };
        
        int numeroDeTestes = 1000;
        List<ConfigResult> resultados = new ArrayList<>();
        
        // ==================================================================================================
        // BASE DA ETAPA 3 (Melhor da Etapa 2: V13 -> Pop 200, Gen 50, Tor 7, TM 35%, Elit 10%, Global)
        // ==================================================================================================
        resultados.add(executarExperimentoMultiplosProblemas("Base (Etapa 2 - V13)", "Pop:200, Gen:50, TM:35%, Tor:7, Elit:10% (GLOBAL)", 
            0.35f, new TournamentSelection(7), new PMXCrossover(), new PureElitismReintegration(0.10), 0.90f, 
            problemas, numeroDeTestes, 50, 200, false));

        // ==================================================================================================
        // AS 5 VARIAÇÕES DA ETAPA 3
        // ==================================================================================================

        // V1: Fitness Posicional na Base
        resultados.add(executarExperimentoMultiplosProblemas("Variacao 01", "Base V13 usando FITNESS POSICIONAL", 
            0.35f, new TournamentSelection(7), new PMXCrossover(), new PureElitismReintegration(0.10), 0.90f, 
            problemas, numeroDeTestes, 50, 200, true));

        // V2: Ajuste Fino Posicional (+Gerações)
        resultados.add(executarExperimentoMultiplosProblemas("Variacao 02", "Pop:200, Gen:75, Tor:7 (POSICIONAL)", 
            0.35f, new TournamentSelection(7), new PMXCrossover(), new PureElitismReintegration(0.10), 0.90f, 
            problemas, numeroDeTestes, 75, 200, true));

        // V3: Diversidade Posicional (Super Pop)
        resultados.add(executarExperimentoMultiplosProblemas("Variacao 03", "Pop:300, Gen:50, Tor:5 (POSICIONAL)", 
            0.35f, new TournamentSelection(5), new PMXCrossover(), new PureElitismReintegration(0.10), 0.90f, 
            problemas, numeroDeTestes, 50, 300, true));

        // V4: Retorno à Média Global (Menos pressão)
        resultados.add(executarExperimentoMultiplosProblemas("Variacao 04", "Pop:200, Tor:3, TM:35% (GLOBAL)", 
            0.35f, new TournamentSelection(3), new PMXCrossover(), new PureElitismReintegration(0.10), 0.90f, 
            problemas, numeroDeTestes, 50, 200, false));

        // V5: Alta Exploração Global
        resultados.add(executarExperimentoMultiplosProblemas("Variacao 05", "Pop:250, Tor:5, TM:40% (GLOBAL)", 
            0.40f, new TournamentSelection(5), new PMXCrossover(), new PureElitismReintegration(0.10), 0.90f, 
            problemas, numeroDeTestes, 50, 250, false));

        // ==================================================================================================
        // APRESENTAÇÃO DOS RESULTADOS
        // ==================================================================================================
        
        System.out.println("\n================================================= RESULTADOS (ETAPA 3) ==================================================");
        System.out.printf("%-20s | %-45s | %-16s | %-15s\n", "Config.", "Descricao (Alteracao em relacao a base)", "Media Converg(%)", "Media Tempo(ms)");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------");
        for (ConfigResult resultado : resultados) {
            System.out.printf("%-20s | %-45s | %15.2f%% | %15.2f\n", 
                    resultado.nomeConfiguracao,
                    resultado.descricao, 
                    resultado.percentualConvergencia, 
                    resultado.tempoMedioMs);
        }
        System.out.println("=========================================================================================================================");
        System.out.println("Encerrando sistema!");
    }

    private static ConfigResult executarExperimentoMultiplosProblemas(
            String nomeConfiguracao, String descricao,
            float taxaMutacao, ParentSelectionStrategy selecaoPais,
            CrossoverStrategy crossover, ReintegrationStrategy reinsercao, float taxaCrossover,
            CryptoProblem[] problemas, int numeroDeTestesPorProblema, int maximoGeracoes, int tamanhoPopulacao, boolean usarFitnessPosicional) {
        
        System.out.println("Executando " + nomeConfiguracao + ": " + descricao);
        
        int contagemConvergenciasTotal = 0;
        long tempoTotalMsTotal = 0;
        
        for (CryptoProblem problemaAtual : problemas) {
            int convergenciasDesteProblema = 0;
            
            for (int testeCorrente = 0; testeCorrente < numeroDeTestesPorProblema; testeCorrente++) {
                LocalDateTime tempoInicio = LocalDateTime.now();
                
                FitnessStrategy fitness = usarFitnessPosicional ? new FitnessPosicional(problemaAtual) : new FitnessGlobal(problemaAtual);
                
                GenAlg algoritmoGenetico = new GenAlg(
                        taxaMutacao, 
                        tamanhoPopulacao, 
                        maximoGeracoes, 
                        taxaCrossover, 
                        crossover, 
                        selecaoPais, 
                        fitness, 
                        reinsercao);
                
                algoritmoGenetico.executeAlgorithm();
                
                LocalDateTime tempoFim = LocalDateTime.now();
                Duration duracao = Duration.between(tempoInicio, tempoFim);
                tempoTotalMsTotal += duracao.toMillis();
                
                if (algoritmoGenetico.getBestIndividual().getFitnessValue() == 0) {
                    convergenciasDesteProblema++;
                    contagemConvergenciasTotal++;
                }
            }
        }
        
        int testesTotais = numeroDeTestesPorProblema * problemas.length;
        
        ConfigResult resultadoFinal = new ConfigResult(nomeConfiguracao, descricao);
        resultadoFinal.percentualConvergencia = (contagemConvergenciasTotal / (double) testesTotais) * 100.0;
        resultadoFinal.tempoMedioMs = tempoTotalMsTotal / (double) testesTotais;
        
        return resultadoFinal;
    }
}
