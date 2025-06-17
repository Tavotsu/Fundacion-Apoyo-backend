package com.fundacion.apoyo.restapi.model;

import jakarta.persistence.*;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Anotacion")
public class Anotacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String nota;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    private String responsable;

    // Relación: Muchas anotaciones pertenecen a un residente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "residente_id", nullable = false)
    @JsonBackReference
    private Resident residente;

    //Constructores
    
    public Anotacion(Long id, String nota, Date fecha, String responsable, Resident residente) {
        this.id = id;
        this.nota = nota;
        this.fecha = fecha;
        this.responsable = responsable;
        this.residente = residente;
    }

    public Anotacion() {
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public Resident getResidente() {
        return residente;
    }

    public void setResidente(Resident residente) {
        this.residente = residente;
    }


    
}