

package br.com.ada.analise.tabelas.model;

public record Gol(
    int partidaId,
    String rodada, 
    String clube, 
    String atleta, 
    String minuto, 
    String tipoDeGol 
) {}