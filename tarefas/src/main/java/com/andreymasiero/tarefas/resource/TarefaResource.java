package com.andreymasiero.tarefas.resource;

import com.andreymasiero.tarefas.domain.Tarefa;
import com.andreymasiero.tarefas.domain.dto.TarefaDTO;
import com.andreymasiero.tarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("tarefas")
public class TarefaResource {

    @Autowired
    private TarefaRepository tarefaRepository;

    @GetMapping
    public List<TarefaDTO> lista(String descricao) {
        List<Tarefa> tarefas = descricao == null ?
                tarefaRepository.findAll(Sort.by(Sort.Direction.ASC, "feita")) :
                tarefaRepository.findByDescricaoContaining(descricao);
        return TarefaDTO.parseTarefaDTO(tarefas);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TarefaDTO> salva(
            @RequestBody @Valid Tarefa tarefa,
            UriComponentsBuilder uriBuilder
    ) {
        tarefaRepository.save(tarefa);

        URI uri = uriBuilder
                .path("/tarefas/{id}")
                .buildAndExpand(tarefa.getCodigo())
                .toUri();
        return ResponseEntity
                .created(uri)
                .body(new TarefaDTO(tarefa));
    }

    @GetMapping("{id}")
    public ResponseEntity<TarefaDTO> detalhe(@PathVariable Long id) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        return tarefa
                .map(t -> ResponseEntity.ok(new TarefaDTO(t)))
                .orElse(ResponseEntity.notFound().build());

    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<TarefaDTO> atualiza(
            @PathVariable Long id,
            @RequestBody @Valid Tarefa tarefaAtualizada
    ) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        return tarefa
                .map(t -> {
                    t.setDescricao(tarefaAtualizada.getDescricao());
                    t.setFeita(tarefaAtualizada.isFeita());
                    t.setDataLimite(tarefaAtualizada.getDataLimite());
                    t.setUltimaAtualizacao(LocalDate.now());
                    tarefaRepository.save(t);
                    return ResponseEntity.ok(new TarefaDTO(t));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<?> remove(@PathVariable Long id) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        return tarefa
                .map(t -> {
                    tarefaRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
