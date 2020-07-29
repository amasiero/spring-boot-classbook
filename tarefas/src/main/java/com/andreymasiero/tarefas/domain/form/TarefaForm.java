package com.andreymasiero.tarefas.domain.form;

import com.andreymasiero.tarefas.domain.Tarefa;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class TarefaForm {

    @NotNull
    @NotEmpty(message = "Descrição obrigatória.")
    @Length(min = 5)
    private String descricao;

    @NotNull @FutureOrPresent
    private LocalDate dataLimite;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDate dataLimite) {
        this.dataLimite = dataLimite;
    }

    public Tarefa toTarefa() {
        return new Tarefa(descricao, dataLimite);
    }
}
