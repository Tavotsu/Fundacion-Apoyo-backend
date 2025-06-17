package com.fundacion.apoyo.restapi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.fundacion.apoyo.restapi.model.Insumo;
import com.fundacion.apoyo.restapi.repository.InsumoRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/insumos")
public class InsumoController {

    @Autowired
    private final InsumoRepository insumoRepository;

    public InsumoController(InsumoRepository insumoRepository) {
        this.insumoRepository = insumoRepository;
    }

    @GetMapping
    public List<Insumo> getAllInsumos() {
        return insumoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Insumo> createInsumo(@RequestBody Insumo insumo) {
        insumo.setFechaUltimaCompra(new Date()); // Asignar fecha de creación/compra inicial
        Insumo nuevoInsumo = insumoRepository.save(insumo);
        return new ResponseEntity<>(nuevoInsumo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Insumo updateInsumo(@PathVariable Long id, @RequestBody Insumo insumoDetails) {
        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Insumo no encontrado"));

        insumo.setNombre(insumoDetails.getNombre());
        insumo.setDescripcion(insumoDetails.getDescripcion());
        insumo.setUnidad(insumoDetails.getUnidad());
        insumo.setProveedorSugerido(insumoDetails.getProveedorSugerido());
        // El stock no se actualiza por aquí, para eso tenemos un endpoint específico

        return insumoRepository.save(insumo);
    }
    
    @PatchMapping("/{id}/stock")
    public Insumo updateStock(@PathVariable Long id, @RequestBody Map<String, Integer> payload) {
        Integer cantidad = payload.get("cantidad");
        if (cantidad == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe proveer una 'cantidad'");
        }

        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Insumo no encontrado"));

        int nuevoStock = insumo.getStockActual() + cantidad;
        if (nuevoStock < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El stock no puede ser negativo");
        }
        
        insumo.setStockActual(nuevoStock);
        if (cantidad > 0) { // Si se agrega stock, actualizamos la fecha de compra
            insumo.setFechaUltimaCompra(new Date());
        }

        return insumoRepository.save(insumo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInsumo(@PathVariable Long id) {
        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Insumo no encontrado"));
        
        insumoRepository.delete(insumo);
        return ResponseEntity.ok().build();
    }
}
