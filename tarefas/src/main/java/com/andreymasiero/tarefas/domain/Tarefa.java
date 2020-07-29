package com.andreymasiero.tarefas.domain;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@SequenceGenerator(name = "tarefa", sequenceName = "SQ_TAREFA")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tarefa")
    private long codigo;

    @NotBlank(message = "Descrição obrigatória.")
    @Size(max = 50)
    private String descricao;

    private boolean feita;

    @FutureOrPresent
    private LocalDate dataLimite;

    private LocalDate criadaEm = LocalDate.now();
    private LocalDate ultimaAtualizacao = LocalDate.now();;

    public Tarefa() { }

    public Tarefa(String descricao, LocalDate dataLimite) {
        this.descricao = descricao;
        this.dataLimite = dataLimite;
    }

    // Getters and Setters
    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isFeita() {
        return feita;
    }

    public void setFeita(boolean feita) {
        this.feita = feita;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDate dataLimite) {
        this.dataLimite = dataLimite;
    }

    public LocalDate getCriadaEm() {
        return criadaEm;
    }

    public void setCriadaEm(LocalDate criadaEm) {
        this.criadaEm = criadaEm;
    }

    public LocalDate getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(LocalDate ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }
}
