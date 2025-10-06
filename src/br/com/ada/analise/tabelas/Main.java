package br.com.ada.analise.tabelas;

import br.com.ada.analise.tabelas.model.Cartao;
import br.com.ada.analise.tabelas.model.Gol;
import br.com.ada.analise.tabelas.model.Partida;
import br.com.ada.analise.tabelas.service.AnaliseService;
import br.com.ada.analise.tabelas.util.CSVReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Projeto 2 Ada Analise tabelas: Campeonato Brasileiro ---");
        try {
            List<Partida> partidas = CSVReader.read("campeonato-brasileiro-full.csv", 
                columns -> new Partida(
                    CSVReader.parseInt(columns[0]),
                    columns[1],
                    CSVReader.parseDate(columns[2]),
                    columns[3],
                    columns[4],
                    columns[5],
                    columns[6],
                    columns[10],
                    columns[11],
                    CSVReader.parseInt(columns[12]),
                    CSVReader.parseInt(columns[13]),
                    columns[14],
                    columns[15]
                )
            );

            List<Gol> gols = CSVReader.read("campeonato-brasileiro-gols.csv", 
                columns -> new Gol(
                    CSVReader.parseInt(columns[0]),
                    columns[1],
                    columns[2],
                    columns[3],
                    columns[4],
                    columns[5]
                )
            );

            List<Cartao> cartoes = CSVReader.read("campeonato-brasileiro-cartoes.csv", 
                columns -> new Cartao(
                    CSVReader.parseInt(columns[0]),
                    columns[1],
                    columns[2],
                    columns[3],
                    columns[4],
                    columns[5],
                    columns[6],
                    columns[7]
                )
            );
            
            System.out.printf("\nDados carregados: %d partidas, %d gols, %d cartões.\n", 
                                partidas.size(), gols.size(), cartoes.size());

            AnaliseService analiseService = new AnaliseService(partidas, gols, cartoes);
            
            System.out.println("\n--- RESULTADOS DA ANÁLISE ---");

            analiseService.timeComMaisVitoriasEm(2008).ifPresent(e -> 
                System.out.printf("Time que mais venceu em 2008: **%s** com %d vitórias.\n", 
                                  e.getKey(), e.getValue()));

            analiseService.estadoComMenosJogos().ifPresent(e -> 
                System.out.printf("Estado com menos jogos: **%s** com %d jogos.\n", 
                                  e.getKey(), e.getValue()));
                                  
            imprimirResultado(analiseService.jogadorComMaisGolsGerais(), "Jogador que mais fez gols"); 
            
            imprimirResultado(analiseService.jogadorComMaisGols("Penalty"), "Jogador que mais fez gols de pênalti");
            imprimirResultado(analiseService.jogadorComMaisGols("Gol Contra"), "Jogador que mais fez gols contras");

            imprimirResultado(analiseService.jogadorComMaisCartoes("Amarelo"), "Jogador que mais recebeu cartões amarelos");
            imprimirResultado(analiseService.jogadorComMaisCartoes("Vermelho"), "Jogador que mais recebeu cartões vermelhos");

            analiseService.partidaComMaisGols().ifPresent(p -> 
                System.out.printf("Placar da partida com mais gols (%d): **%s %d x %d %s** (Data: %s).\n", 
                                  p.getTotalGols(), p.mandante(), p.placarMandante(), 
                                  p.placarVisitante(), p.visitante(), p.data()));
                                  
        } catch (IOException e) {
            System.err.println("\nERRO: Falha ao carregar arquivos CSV. Verifique se eles estão na pasta 'resources' e se o delimitador está correto (';').");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("\nERRO: Ocorreu um erro durante a análise: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void imprimirResultado(Optional<Map.Entry<String, Long>> resultado, String titulo) {
        resultado.ifPresent(e -> 
            System.out.printf("%s: **%s** com %d ocorrências.\n", 
                              titulo, e.getKey(), e.getValue()));
    }
}