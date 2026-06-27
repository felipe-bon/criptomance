import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Reintegration.PureElitismReintegration;
import Reintegration.ReintegrationStrategy;
import crossover.CrossoverStrategy;
import crossover.PMXCrossover;
import fitness.FitnessGlobal;
import geneticAlgorithm.CryptoProblem;
import geneticAlgorithm.GenAlg;
import parentSelection.ParentSelectionStrategy;
import parentSelection.TournamentSelection;

public class Etapa2 {

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
        System.out.println("Iniciando sistema - Etapa 2 (Variacoes sobre a melhor da Etapa 1)...");
        CryptoProblem problemaAtual = new CryptoProblem("SEND", "MORE", "MONEY");
        int numeroDeTestes = 1000;
        
        List<ConfigResult> resultados = new ArrayList<>();
        
        // ==================================================================================================
        // BASE DA ETAPA 2 (Melhor da Etapa 1: Pop 100, Gen 50, TM 20%, Torneio(3), PMX, R Pura Elit 20%)
        // ==================================================================================================
        resultados.add(executarExperimento("Base (Etapa 1)", "Pop:100, Gen:50, TM:20%, Tor:3, Elit:20%", 
            0.2f, new TournamentSelection(3), new PMXCrossover(), new PureElitismReintegration(0.2), 0.8f, 
            problemaAtual, numeroDeTestes, 50, 100));

        // ==================================================================================================
        // AS 10 VARIAÇÕES DA ETAPA 2
        // ==================================================================================================

        // V1: Mais Gerações
        resultados.add(executarExperimento("Variacao 01", "+Geracoes (75)", 
            0.2f, new TournamentSelection(3), new PMXCrossover(), new PureElitismReintegration(0.2), 0.8f, 
            problemaAtual, numeroDeTestes, 75, 100));

        // V2: Mais População
        resultados.add(executarExperimento("Variacao 02", "+Populacao (140)", 
            0.2f, new TournamentSelection(3), new PMXCrossover(), new PureElitismReintegration(0.2), 0.8f, 
            problemaAtual, numeroDeTestes, 50, 140));

        // V3: Mutação Agressiva
        resultados.add(executarExperimento("Variacao 03", "Mutacao Agressiva (30%)", 
            0.3f, new TournamentSelection(3), new PMXCrossover(), new PureElitismReintegration(0.2), 0.8f, 
            problemaAtual, numeroDeTestes, 50, 100));

        // V4: Baixa Pressão Seletiva
        resultados.add(executarExperimento("Variacao 04", "Baixa Pressao (Torneio 2)", 
            0.2f, new TournamentSelection(2), new PMXCrossover(), new PureElitismReintegration(0.2), 0.8f, 
            problemaAtual, numeroDeTestes, 50, 100));

        // V5: Alta Pressão Seletiva
        resultados.add(executarExperimento("Variacao 05", "Alta Pressao (Torneio 5)", 
            0.2f, new TournamentSelection(5), new PMXCrossover(), new PureElitismReintegration(0.2), 0.8f, 
            problemaAtual, numeroDeTestes, 50, 100));

        // V6: Elitismo Reduzido
        resultados.add(executarExperimento("Variacao 06", "Elitismo 10% (Crossover 90%)", 
            0.2f, new TournamentSelection(3), new PMXCrossover(), new PureElitismReintegration(0.1), 0.9f, 
            problemaAtual, numeroDeTestes, 50, 100));

        // V7: Elitismo Aumentado
        resultados.add(executarExperimento("Variacao 07", "Elitismo 30% (Crossover 70%)", 
            0.2f, new TournamentSelection(3), new PMXCrossover(), new PureElitismReintegration(0.3), 0.7f, 
            problemaAtual, numeroDeTestes, 50, 100));

        // V8: Combo (Pressão e Diversidade)
        resultados.add(executarExperimento("Variacao 08", "Pop:120, Tor:5, TM:25%", 
            0.25f, new TournamentSelection(5), new PMXCrossover(), new PureElitismReintegration(0.2), 0.8f, 
            problemaAtual, numeroDeTestes, 50, 120));

        // V9: Combo (Convergência Longa)
        resultados.add(executarExperimento("Variacao 09", "Pop:80, Gen:80", 
            0.2f, new TournamentSelection(3), new PMXCrossover(), new PureElitismReintegration(0.2), 0.8f, 
            problemaAtual, numeroDeTestes, 80, 80));

        // V10: Combo Supremo
        resultados.add(executarExperimento("Variacao 10", "Pop:120, Gen:60, Tor:5, TM:25%, Elit:15%", 
            0.25f, new TournamentSelection(5), new PMXCrossover(), new PureElitismReintegration(0.15), 0.85f, 
            problemaAtual, numeroDeTestes, 60, 120));

        // ==================================================================================================
        // APRESENTAÇÃO DOS RESULTADOS
        // ==================================================================================================
        
        System.out.println("\n================================================= RESULTADOS (ETAPA 2) ==================================================");
        System.out.printf("%-15s | %-42s | %-13s | %-15s\n", "Config.", "Descricao (Alteracao em relacao a base)", "Convergencia", "Tempo Medio(ms)");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------");
        for (ConfigResult resultado : resultados) {
            System.out.printf("%-15s | %-42s | %12.2f%% | %15.2f\n", 
                    resultado.nomeConfiguracao,
                    resultado.descricao, 
                    resultado.percentualConvergencia, 
                    resultado.tempoMedioMs);
        }
        System.out.println("=========================================================================================================================");
        System.out.println("Encerrando sistema!");
    }

    private static ConfigResult executarExperimento(
            String nomeConfiguracao, String descricao,
            float taxaMutacao, ParentSelectionStrategy selecaoPais,
            CrossoverStrategy crossover, ReintegrationStrategy reinsercao, float taxaCrossover,
            CryptoProblem problemaAtual, int numeroDeTestes, int maximoGeracoes, int tamanhoPopulacao) {
        
        System.out.println("Executando " + nomeConfiguracao + ": " + descricao);
        
        int contagemConvergencias = 0;
        long tempoTotalMs = 0;
        
        for (int testeCorrente = 0; testeCorrente < numeroDeTestes; testeCorrente++) {
            LocalDateTime tempoInicio = LocalDateTime.now();
            
            GenAlg algoritmoGenetico = new GenAlg(
                    taxaMutacao, 
                    tamanhoPopulacao, 
                    maximoGeracoes, 
                    taxaCrossover, 
                    crossover, 
                    selecaoPais, 
                    new FitnessGlobal(problemaAtual), 
                    reinsercao);
            
            algoritmoGenetico.executeAlgorithm();
            
            LocalDateTime tempoFim = LocalDateTime.now();
            Duration duracao = Duration.between(tempoInicio, tempoFim);
            tempoTotalMs += duracao.toMillis();
            
            if (algoritmoGenetico.getBestIndividual().getFitnessValue() == 0) {
                contagemConvergencias++;
            }
        }
        
        ConfigResult resultadoFinal = new ConfigResult(nomeConfiguracao, descricao);
        resultadoFinal.percentualConvergencia = (contagemConvergencias / (double) numeroDeTestes) * 100.0;
        resultadoFinal.tempoMedioMs = tempoTotalMs / (double) numeroDeTestes;
        
        return resultadoFinal;
    }
}
