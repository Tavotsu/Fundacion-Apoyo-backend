package com.fundacion.apoyo.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.fundacion.apoyo.restapi.model.Anotacion;
import com.fundacion.apoyo.restapi.model.Resident;
import com.fundacion.apoyo.restapi.repository.AnotacionRepository;
import com.fundacion.apoyo.restapi.repository.ResidentRepository;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/residents")
public class ResidentController {

    @Autowired
    private final ResidentRepository residentRepository;
    @Autowired
    private final AnotacionRepository anotacionRepository;

    public ResidentController(ResidentRepository residentRepository, AnotacionRepository anotacionRepository) {
        this.residentRepository = residentRepository;
        this.anotacionRepository = anotacionRepository;
    }

    // --- Endpoints para Residents ---

    @GetMapping
    public List<Resident> getAllResidents() {
        return residentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Resident getResidentById(@PathVariable Long id) {
        return residentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Residente no encontrado"));
    }

    @PostMapping
    public ResponseEntity<Resident> createResident(@RequestBody Resident resident) {
        Resident nuevoResident = residentRepository.save(resident);
        return new ResponseEntity<>(nuevoResident, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Resident updateResident(@PathVariable Long id, @RequestBody Resident residentDetails) {
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Residente no encontrado"));

        resident.setNombreCompleto(residentDetails.getNombreCompleto());
        resident.setFechaNacimiento(residentDetails.getFechaNacimiento());
        resident.setDatosPersonales(residentDetails.getDatosPersonales());
        resident.setEstadoSaludGeneral(residentDetails.getEstadoSaludGeneral());
        resident.setMedicamentosActuales(residentDetails.getMedicamentosActuales());
        resident.setCuidadosEspeciales(residentDetails.getCuidadosEspeciales());
        
        return residentRepository.save(resident);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResident(@PathVariable Long id) {
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Residente no encontrado"));
        
        residentRepository.delete(resident);
        return ResponseEntity.ok().build();
    }

    // --- Endpoints para Anotaciones de un Resident ---

    @GetMapping("/{residentId}/anotaciones")
    public List<Anotacion> getAnotacionesByResident(@PathVariable Long residentId) {
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Residente no encontrado"));
        return resident.getHistorialAnotaciones();
    }

    @PostMapping("/{residentId}/anotaciones")
    public ResponseEntity<Anotacion> addAnotacion(@PathVariable Long residentId, @RequestBody Anotacion anotacionRequest) {
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Residente no encontrado"));
        
        Anotacion nuevaAnotacion = new Anotacion();
        nuevaAnotacion.setNota(anotacionRequest.getNota());
        nuevaAnotacion.setResponsable(anotacionRequest.getResponsable());
        nuevaAnotacion.setFecha(new Date()); // Establecer la fecha actual en el servidor
        nuevaAnotacion.setResidente(resident);

        anotacionRepository.save(nuevaAnotacion);
        
        return new ResponseEntity<>(nuevaAnotacion, HttpStatus.CREATED);
    }
}
