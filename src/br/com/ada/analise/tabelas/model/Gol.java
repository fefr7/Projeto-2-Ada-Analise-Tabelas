
package br.com.ada.analise.tabelas.model;

public record Gol(
    int partidaId,
    String rodada, 
    String clube, 
    String atleta, // Campo usado no lugar de 'jogador'
    String minuto, 
    String tipoDeGol // Campo usado no lugar de 'tipo'
) {}