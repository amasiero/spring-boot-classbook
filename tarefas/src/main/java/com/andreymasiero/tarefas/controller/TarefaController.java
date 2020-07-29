package com.andreymasiero.tarefas.controller;

import com.andreymasiero.tarefas.domain.Tarefa;
import com.andreymasiero.tarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("tarefa")
public class TarefaController {

    @Autowired
    TarefaRepository tarefaRepository;

    @GetMapping("cadastrar")
    public String index(Model model) {
        List<Tarefa> tarefas = tarefaRepository.findAll(Sort.by(Sort.Direction.ASC, "descricao"));
        model.addAttribute("tarefas", tarefas);
        return "tarefa/form";
    }

    @PostMapping("cadastrar")
    public String store(Tarefa tarefa, Model model) {
        tarefaRepository.save(tarefa);
        model.addAttribute("tarefa", tarefa);
        return "tarefa/sucesso";
    }
}
