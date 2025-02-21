package com.example.competicao.controller;


import com.example.competicao.model.Times;
import com.example.competicao.repository.TimesRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/times")
public class TimesController {

    private final TimesRepository timesRepository;

    @Autowired
    public TimesController(@NonNull TimesRepository timesRepository) {
        this.timesRepository = timesRepository;
    }

    @PostMapping
    public Times salvarTimes(@RequestBody Times times) {
        System.out.println("Time salvo: " + times);
        timesRepository.save(times);
        return times;
    }

    @GetMapping("/{id}")
    public Times obterPorIdTimes(@PathVariable("id") Long id) {
        return timesRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable("id") Long id, @RequestBody Times times) {
        timesRepository.save(times);
    }
}