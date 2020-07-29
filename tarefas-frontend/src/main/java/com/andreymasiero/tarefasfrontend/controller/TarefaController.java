package com.andreymasiero.tarefasfrontend.controller;

import com.andreymasiero.tarefasfrontend.domain.Tarefa;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tarefa")
public class TarefaController {

    @GetMapping
    public String index(Model model) {
        RestTemplate template = new RestTemplate();
        List<?> tarefas = template.getForObject("http://localhost/tarefas", List.class);
        model.addAttribute("tarefas", tarefas);
        return "tarefa/form";
    }

    @PostMapping("/cadastrar")
    public String store(Tarefa tarefa, RedirectAttributes redirectAttributes) {
        RestTemplate template = new RestTemplate();
        Tarefa tarefaResultado =
                template.postForObject("http://localhost/tarefas",
                        tarefa, Tarefa.class);
        redirectAttributes.addFlashAttribute("msg", "Tarefa "
                        + tarefaResultado.getDescricao()
                        + " cadastrada com sucesso." );
        return "redirect:/tarefa";
    }

    @GetMapping("/feita/{id}")
    public String done(@PathVariable("id") long codigo, RedirectAttributes redirectAttributes) {
        RestTemplate template = new RestTemplate();

        Tarefa tarefa = template.getForObject("http://localhost/tarefas/" + codigo, Tarefa.class);
        tarefa.setFeita(true);

        if(tarefa.getDataLimite() == null || tarefa.getDataLimite().isBefore(LocalDate.now())) {
            tarefa.setDataLimite(LocalDate.now());
        }

        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(codigo));

        template.put("http://localhost/tarefas/{id}", tarefa, params);

        redirectAttributes.addFlashAttribute("msg", "Tarefa realizada!");
        return "redirect:/tarefa";
    }

    @PostMapping("/excluir")
    public String delete(long codigo, RedirectAttributes redirectAttributes) {
        RestTemplate template = new RestTemplate();

        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(codigo));

        template.delete("http://localhost/tarefas/{id}", params);

        redirectAttributes.addFlashAttribute("msg", "Tarefa excluida com sucesso!");
        return "redirect:/tarefa";
    }
}
