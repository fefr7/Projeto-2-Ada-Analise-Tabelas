/*package br.com.ada.analise.tabelas.model;

import java.time.LocalDate;

public record Partida(
    int id,
    String rodada,
    LocalDate data, 
    String horario,
    String dia,
    String mandante,
    String visitante,
    String vencedor, 
    String arena,
    int placarMandante,
    int placarVisitante,
    String estadoMandante, 
    String estadoVisitante
) {
    
    public int getTotalGols() {
        return placarMandante + placarVisitante;
    }
}*/

package br.com.ada.analise.tabelas.model;

import java.time.LocalDate;

public record Partida(
    int id,
    String rodada,
    LocalDate data,
    String horario,
    String dia,
    String mandante,
    String visitante,
    String vencedor,
    String arena,
    int placarMandante,
    int placarVisitante,
    String estadoMandante,
    String estadoVisitante
) {
    public int getTotalGols() {
        return placarMandante + placarVisitante;
    }
}