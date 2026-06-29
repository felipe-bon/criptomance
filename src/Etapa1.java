import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Reintegration.PureElitismReintegration;
import Reintegration.ReintegrationStrategy;
import Reintegration.SortededReintegration;
import crossover.CrossoverStrategy;
import crossover.CyclicCrossover;
import crossover.PMXCrossover;
import fitness.FitnessGlobal;
import geneticAlgorithm.CryptoProblem;
import geneticAlgorithm.GenAlg;
import parentSelection.ParentSelectionStrategy;
import parentSelection.TournamentSelection;
import parentSelection.RouletteSelection;

public class Etapa1 {

    // Classe auxiliar para guardar os resultados de cada configuração
    static class ConfigResult {
        String nomeConfiguracao;
        String taxaMutacao;
        String selecaoPais;
        String crossover;
        String reinsercao;
        double percentualConvergencia;
        double tempoMedioMs;
        
        ConfigResult(String nomeConfiguracao, String taxaMutacao, String selecaoPais, String crossover, String reinsercao) {
            this.nomeConfiguracao = nomeConfiguracao;
            this.taxaMutacao = taxaMutacao; 
            this.selecaoPais = selecaoPais; 
            this.crossover = crossover; 
            this.reinsercao = reinsercao;
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Iniciando sistema - Etapa 1 (Declarando as 16 combinacoes manualmente)");
        CryptoProblem problemaAtual = new CryptoProblem("SEND", "MORE", "MONEY");

        int numeroDeTestes = 1000;
        int maximoGeracoes = 50;
        int tamanhoPopulacao = 100;
        
        List<ConfigResult> resultados = new ArrayList<>();
        
        // ==================================================================================================
        // DECLARAÇÃO DAS 16 CONFIGURAÇÕES (1 a 1)
        // ==================================================================================================
        
        // CONFIGURAÇÃO 1: TM1 (10%), S1 (Torneio), C1 (Ciclico), R1 (Ordenada - 60% crossover)
        resultados.add(executarExperimento("Config 01", 
            "TM1 (10%)", 0.1f, 
            "S1 (Torneio)", new TournamentSelection(), 
            "C1 (Ciclico)", new CyclicCrossover(), 
            "R1 (Ordenada)", new SortededReintegration(), 0.6f, 
            problemaAtual, numeroDeTestes, maximoGeracoes, tamanhoPopulacao));
            
        // CONFIGURAÇÃO 2: TM2 (20%), S1 (Torneio), C1 (Ciclico), R1 (Ordenada - 60% crossover)
        resultados.add(executarExperimento("Config 02", 
            "TM2 (20%)", 0.2f, 
            "S1 (Torneio)", new TournamentSelection(), 
            "C1 (Ciclico)", new CyclicCrossover(), 
            "R1 (Ordenada)", new SortededReintegration(), 0.6f, 
            problemaAtual, numeroDeTestes, maximoGeracoes, tamanhoPopulacao));

        // CONFIGURAÇÃO 3: TM1 (10%), S2 (Roleta), C1 (Ciclico), R1 (Ordenada - 60% crossover)
        resultados.add(executarExperimento("Config 03", 
            "TM1 (10%)", 0.1f, 
            "S2 (Roleta)", new RouletteSelection(), 
            "C1 (Ciclico)", new CyclicCrossover(), 
            "R1 (Ordenada)", new SortededReintegration(), 0.6f, 
            problemaAtual, numeroDeTestes, maximoGeracoes, tamanhoPopulacao));

        // CONFIGURAÇÃO 4: TM2 (20%), S2 (Roleta), C1 (Ciclico), R1 (Ordenada - 60% crossover)
        resultados.add(executarExperimento("Config 04", 
            "TM2 (20%)", 0.2f, 
            "S2 (Roleta)", new RouletteSelection(), 
            "C1 (Ciclico)", new CyclicCrossover(), 
            "R1 (Ordenada)", new SortededReintegration(), 0.6f, 
            problemaAtual, numeroDeTestes, maximoGeracoes, tamanhoPopulacao));

        // CONFIGURAÇÃO 5: TM1 (10%), S1 (Torneio), C2 (PMX), R1 (Ordenada - 60% crossover)
        resultados.add(executarExperimento("Config 05", 
            "TM1 (10%)", 0.1f, 
            "S1 (Torneio)", new TournamentSelection(), 
            "C2 (PMX)", new PMXCrossover(), 
            "R1 (Ordenada)", new SortededReintegration(), 0.6f, 
            problemaAtual, numeroDeTestes, maximoGeracoes, tamanhoPopulacao));

        // CONFIGURAÇÃO 6: TM2 (20%), S1 (Torneio), C2 (PMX), R1 (Ordenada - 60% crossover)
        resultados.add(executarExperimento("Config 06", 
            "TM2 (20%)", 0.2f, 
            "S1 (Torneio)", new TournamentSelection(), 
            "C2 (PMX)", new PMXCrossover(), 
            "R1 (Ordenada)", new SortededReintegration(), 0.6f, 
            problemaAtual, numeroDeTestes, maximoGeracoes, tamanhoPopulacao));

        // CONFIGURAÇÃO 7: TM1 (10%), S2 (Roleta), C2 (PMX), R1 (Ordenada - 60% crossover)
        resultados.add(executarExperimento("Config 07", 
            "TM1 (10%)", 0.1f, 
            "S2 (Roleta)", new RouletteSelection(), 
            "C2 (PMX)", new PMXCrossover(), 
            "R1 (Ordenada)", new SortededReintegration(), 0.6f, 
            problemaAtual, numeroDeTestes, maximoGeracoes, tamanhoPopulacao));

        // CONFIGURAÇÃO 8: TM2 (20%), S2 (Roleta), C2 (PMX), R1 (Ordenada - 60% crossover)
        resultados.add(executarExperimento("Config 08", 
            "TM2 (20%)", 0.2f, 
            "S2 (Roleta)", new RouletteSelection(), 
            "C2 (PMX)", new PMXCrossover(), 
            "R1 (Ordenada)", new SortededReintegration(), 0.6f, 
            problemaAtual, numeroDeTestes, maximoGeracoes, tamanhoPopulacao));

        // CONFIGURAÇÃO 9: TM1 (10%), S1 (Torneio), C1 (Ciclico), R2 (Pura c/ Elitismo 20% - 80% crossover)
        resultados.add(executarExperimento("Config 09", 
            "TM1 (10%)", 0.1f, 
            "S1 (Torneio)", new TournamentSelection(), 
            "C1 (Ciclico)", new CyclicCrossover(), 
            "R2 (Pura c/ Elitismo)", new PureElitismReintegration(0.2), 0.8f, 
            problemaAtual, numeroDeTestes, maximoGeracoes, tamanhoPopulacao));

        // CONFIGURAÇÃO 10: TM2 (20%), S1 (Torneio), C1 (Ciclico), R2 (Pura c/ Elitismo 20% - 80% crossover)
        resultados.add(executarExperimento("Config 10", 
            "TM2 (20%)", 0.2f, 
            "S1 (Torneio)", new TournamentSelection(), 
            "C1 (Ciclico)", new CyclicCrossover(), 
            "R2 (Pura c/ Elitismo)", new PureElitismReintegration(0.2), 0.8f, 
            problemaAtual, numeroDeTestes, maximoGeracoes, tamanhoPopulacao));

        // CONFIGURAÇÃO 11: TM1 (10%), S2 (Roleta), C1 (Ciclico), R2 (Pura c/ Elitismo 20% - 80% crossover)
        resultados.add(executarExperimento("Config 11", 
            "TM1 (10%)", 0.1f, 
            "S2 (Roleta)", new RouletteSelection(), 
            "C1 (Ciclico)", new CyclicCrossover(), 
            "R2 (Pura c/ Elitismo)", new PureElitismReintegration(0.2), 0.8f, 
            problemaAtual, numeroDeTestes, maximoGeracoes, tamanhoPopulacao));

        // CONFIGURAÇÃO 12: TM2 (20%), S2 (Roleta), C1 (Ciclico), R2 (Pura c/ Elitismo 20% - 80% crossover)
        resultados.add(executarExperimento("Config 12", 
            "TM2 (20%)", 0.2f, 
            "S2 (Roleta)", new RouletteSelection(), 
            "C1 (Ciclico)", new CyclicCrossover(), 
            "R2 (Pura c/ Elitismo)", new PureElitismReintegration(0.2), 0.8f, 
            problemaAtual, numeroDeTestes, maximoGeracoes, tamanhoPopulacao));

        // CONFIGURAÇÃO 13: TM1 (10%), S1 (Torneio), C2 (PMX), R2 (Pura c/ Elitismo 20% - 80% crossover)
        resultados.add(executarExperimento("Config 13", 
            "TM1 (10%)", 0.1f, 
            "S1 (Torneio)", new TournamentSelection(), 
            "C2 (PMX)", new PMXCrossover(), 
            "R2 (Pura c/ Elitismo)", new PureElitismReintegration(0.2), 0.8f, 
            problemaAtual, numeroDeTestes, maximoGeracoes, tamanhoPopulacao));

        // CONFIGURAÇÃO 14: TM2 (20%), S1 (Torneio), C2 (PMX), R2 (Pura c/ Elitismo 20% - 80% crossover)
        resultados.add(executarExperimento("Config 14", 
            "TM2 (20%)", 0.2f, 
            "S1 (Torneio)", new TournamentSelection(), 
            "C2 (PMX)", new PMXCrossover(), 
            "R2 (Pura c/ Elitismo)", new PureElitismReintegration(0.2), 0.8f, 
            problemaAtual, numeroDeTestes, maximoGeracoes, tamanhoPopulacao));

        // CONFIGURAÇÃO 15: TM1 (10%), S2 (Roleta), C2 (PMX), R2 (Pura c/ Elitismo 20% - 80% crossover)
        resultados.add(executarExperimento("Config 15", 
            "TM1 (10%)", 0.1f, 
            "S2 (Roleta)", new RouletteSelection(), 
            "C2 (PMX)", new PMXCrossover(), 
            "R2 (Pura c/ Elitismo)", new PureElitismReintegration(0.2), 0.8f, 
            problemaAtual, numeroDeTestes, maximoGeracoes, tamanhoPopulacao));

        // CONFIGURAÇÃO 16: TM2 (20%), S2 (Roleta), C2 (PMX), R2 (Pura c/ Elitismo 20% - 80% crossover)
        resultados.add(executarExperimento("Config 16", 
            "TM2 (20%)", 0.2f, 
            "S2 (Roleta)", new RouletteSelection(), 
            "C2 (PMX)", new PMXCrossover(), 
            "R2 (Pura c/ Elitismo)", new PureElitismReintegration(0.2), 0.8f, 
            problemaAtual, numeroDeTestes, maximoGeracoes, tamanhoPopulacao));

        // ==================================================================================================
        // APRESENTAÇÃO DOS RESULTADOS
        // ==================================================================================================
        
        System.out.println("\n============================================================= RESULTADOS (ETAPA 1) =============================================================");
        System.out.printf("%-10s | %-12s | %-15s | %-15s | %-28s | %-13s | %-15s\n", "Config.", "Mutacao", "Selecao", "Crossover", "Reinsercao", "Convergencia", "Tempo Medio(ms)");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
        for (ConfigResult resultado : resultados) {
            System.out.printf("%-10s | %-12s | %-15s | %-15s | %-28s | %12.2f%% | %15.2f\n", 
                    resultado.nomeConfiguracao,
                    resultado.taxaMutacao, 
                    resultado.selecaoPais, 
                    resultado.crossover, 
                    resultado.reinsercao, 
                    resultado.percentualConvergencia, 
                    resultado.tempoMedioMs);
        }
        System.out.println("================================================================================================================================================");
        System.out.println("Encerrando sistema!");
    }

    // ==================================================================================================
    // METODO AUXILIAR PARA EXECUTAR 1 COMBINAÇÃO
    // ==================================================================================================
    private static ConfigResult executarExperimento(
            String nomeConfiguracao,
            String nomeTaxaMutacao, float taxaMutacao,
            String nomeSelecao, ParentSelectionStrategy selecaoPais,
            String nomeCrossover, CrossoverStrategy crossover,
            String nomeReinsercao, ReintegrationStrategy reinsercao, float taxaCrossover,
            CryptoProblem problemaAtual, int numeroDeTestes, int maximoGeracoes, int tamanhoPopulacao) {
        
        System.out.println("Executando " + nomeConfiguracao + ": " + nomeTaxaMutacao + " | " + nomeSelecao + " | " + nomeCrossover + " | " + nomeReinsercao);
        
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
        
        ConfigResult resultadoFinal = new ConfigResult(nomeConfiguracao, nomeTaxaMutacao, nomeSelecao, nomeCrossover, nomeReinsercao);
        resultadoFinal.percentualConvergencia = (contagemConvergencias / (double) numeroDeTestes) * 100.0;
        resultadoFinal.tempoMedioMs = tempoTotalMs / (double) numeroDeTestes;
        
        return resultadoFinal;
    }
}
