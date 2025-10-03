package br.com.ada.analise.tabelas.service;

import br.com.ada.analise.tabelas.model.Cartao;
import br.com.ada.analise.tabelas.model.Gol;
import br.com.ada.analise.tabelas.model.Partida;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnaliseService {

    private final List<Partida> partidas;
    private final List<Gol> gols;
    private final List<Cartao> cartoes;

    public AnaliseService(List<Partida> partidas, List<Gol> gols, List<Cartao> cartoes) {
        this.partidas = partidas;
        this.gols = gols;
        this.cartoes = cartoes;
    }

    public Optional<Map.Entry<String, Long>> timeComMaisVitoriasEm(int ano) {
        return partidas.stream()
                .filter(p -> p.data() != null && p.data().getYear() == ano)
                .filter(p -> p.vencedor() != null && !p.vencedor().trim().isEmpty() && !"-".equals(p.vencedor()))
                .collect(Collectors.groupingBy(Partida::vencedor, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue());
    }

    public Optional<Map.Entry<String, Long>> estadoComMenosJogos() {
        return partidas.stream()
                .flatMap(p -> Stream.of(p.estadoMandante(), p.estadoVisitante()))
                .filter(estado -> estado != null && !estado.isEmpty())
                .collect(Collectors.groupingBy(estado -> estado, Collectors.counting()))
                .entrySet().stream()
                .min(Map.Entry.comparingByValue());
    }

    public Optional<Partida> partidaComMaisGols() {
        return partidas.stream()
                .filter(p -> p.visitante() != null && !p.visitante().trim().isEmpty())
                .max(Comparator.comparingInt(Partida::getTotalGols));
    }

    public Optional<Map.Entry<String, Long>> jogadorComMaisGols(String tipoGol) {
        return gols.stream()
                .filter(g -> g.atleta() != null && !g.atleta().trim().isEmpty())
                .filter(g -> g.tipoDeGol().equalsIgnoreCase(tipoGol))
                .collect(Collectors.groupingBy(Gol::atleta, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue());
    }

    public Optional<Map.Entry<String, Long>> jogadorComMaisCartoes(String tipoCartao) {
        return cartoes.stream()
                .filter(c -> c.atleta() != null && !c.atleta().trim().isEmpty())
                .filter(c -> c.cartao().equalsIgnoreCase(tipoCartao))
                .collect(Collectors.groupingBy(Cartao::atleta, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue());
    }
}