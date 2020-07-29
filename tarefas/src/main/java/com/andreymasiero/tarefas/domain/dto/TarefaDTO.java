package com.andreymasiero.tarefas.domain.dto;

import com.andreymasiero.tarefas.domain.Tarefa;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class TarefaDTO {

    private Long codigo;
    private String descricao;
    private Boolean feita;
    private LocalDate dataLimite;

    public TarefaDTO(Tarefa tarefa) {
        this.codigo = tarefa.getCodigo();
        this.descricao = tarefa.getDescricao();
        this.feita = tarefa.isFeita();
        this.dataLimite = tarefa.getDataLimite();
    }

    public Long getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Boolean getFeita() {
        return feita;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public static List<TarefaDTO> parseTarefaDTO(List<Tarefa> tarefas) {
        return tarefas.stream()
                .map(TarefaDTO::new)
                .collect(Collectors.toList());
    }
}
