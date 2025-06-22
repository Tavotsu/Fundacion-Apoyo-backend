package com.fundacion.apoyo.restapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "aportadores")
public class Aportador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private Double cantidadDonada;

    //Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getCantidadDonada() {
        return cantidadDonada;
    }

    public void setCantidadDonada(Double cantidadDonada) {
        this.cantidadDonada = cantidadDonada;
    }
}
