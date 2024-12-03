package com.br.trabalhouegn2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.br.trabalhouegn2.exception.ResourceNotFoundException;
import com.br.trabalhouegn2.model.Tarefa;
import com.br.trabalhouegn2.repository.TarefaRepository;

public class TarefaController {

        @Autowired
    private TarefaRepository repository;

    @PostMapping("/tarefa")
    public Tarefa inserir(@RequestBody Tarefa tarefa) {
        if (tarefa.getId() != null && repository.findById(tarefa.getId()).isPresent()) {
            throw new IllegalArgumentException("Erro ao criar: Tarefa com ID já existente.");
        }
        return repository.save(tarefa);
    }

    @GetMapping("/tarefa")
    public List<Tarefa> listar() {
        return repository.findAll();
    }

    @DeleteMapping("/tarefa/{id}")
    public ResponseEntity<Map<String, Boolean>> deletar(@PathVariable Integer id) {
        Tarefa tarefaRecuperada = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada: " + id));

        repository.delete(tarefaRecuperada);

        Map<String, Boolean> resposta = new HashMap<>();
        resposta.put("Tarefa excluída com sucesso", true);

        return ResponseEntity.ok(resposta);
    }
}
