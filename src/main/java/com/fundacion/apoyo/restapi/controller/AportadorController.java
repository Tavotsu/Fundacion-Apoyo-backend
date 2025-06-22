package com.fundacion.apoyo.restapi.controller;


import com.fundacion.apoyo.restapi.model.Aportador;
import com.fundacion.apoyo.restapi.repository.AportadorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/aportadores")
public class AportadorController {

    @Autowired
    private final AportadorRepository aportadorRepository;

    public AportadorController(AportadorRepository aportadorRepository) {
        this.aportadorRepository = aportadorRepository;
    }

    @GetMapping
    public List<Aportador> getAllAportadores() {
        return aportadorRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Aportador> createAportador(@RequestBody Aportador aportador) {
        Aportador nuevoAportador = aportadorRepository.save(aportador);
        return new ResponseEntity<>(nuevoAportador, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Aportador updateAportador(@PathVariable Long id, @RequestBody Aportador aportadorDetails) {
        Aportador aportador = aportadorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aportador no encontrado"));

        aportador.setNombre(aportadorDetails.getNombre());
        aportador.setDescripcion(aportadorDetails.getDescripcion());
        aportador.setCantidadDonada(aportadorDetails.getCantidadDonada());

        return aportadorRepository.save(aportador);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAportador(@PathVariable Long id) {
        Aportador aportador = aportadorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aportador no encontrado"));
        
        aportadorRepository.delete(aportador);
        return ResponseEntity.ok().build();
    }
}