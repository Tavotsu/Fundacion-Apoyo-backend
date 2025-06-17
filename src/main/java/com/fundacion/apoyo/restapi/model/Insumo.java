package com.fundacion.apoyo.restapi.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "insumos")
public class Insumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private Integer stockActual;

    @Column(nullable = false)
    private String unidad; 

    private String proveedorSugerido;

    @Temporal(TemporalType.DATE)
    private Date fechaUltimaCompra;

    // Constructores

    public Insumo(Long id, String nombre, String descripcion, Integer stockActual, String unidad, String proveedorSugerido, Date fechaUltimaCompra) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.stockActual = stockActual;
        this.unidad = unidad;
        this.proveedorSugerido = proveedorSugerido;
        this.fechaUltimaCompra = fechaUltimaCompra;
    }

    public Insumo() {
    }

    // Getters y Setters
    
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

    public Integer getStockActual() {
        return stockActual;
    }

    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getProveedorSugerido() {
        return proveedorSugerido;
    }

    public void setProveedorSugerido(String proveedorSugerido) {
        this.proveedorSugerido = proveedorSugerido;
    }

    public Date getFechaUltimaCompra() {
        return fechaUltimaCompra;
    }

    public void setFechaUltimaCompra(Date fechaUltimaCompra) {
        this.fechaUltimaCompra = fechaUltimaCompra;
    }
}