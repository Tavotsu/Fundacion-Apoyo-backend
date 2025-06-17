package com.fundacion.apoyo.restapi.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Resident")
public class Resident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreCompleto;

    private Date fechaNacimiento;

    @Column(columnDefinition = "TEXT")
    private String datosPersonales; // RUT, Familiar de contacto, etc.

    @Column(columnDefinition = "TEXT")
    private String estadoSaludGeneral;

    // Relación: Un residente puede tener muchas anotaciones
    @OneToMany(mappedBy = "residente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Anotacion> historialAnotaciones;

    @Column(columnDefinition = "TEXT")
    private String medicamentosActuales;

    @Column(columnDefinition = "TEXT")
    private String cuidadosEspeciales;
    public Resident(Long id, String nombreCompleto, Date fechaNacimiento, String datosPersonales, String estadoSaludGeneral, List<Anotacion> historialAnotaciones) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.datosPersonales = datosPersonales;
        this.estadoSaludGeneral = estadoSaludGeneral;
        this.historialAnotaciones = historialAnotaciones;
    }

    public Resident(Long id, String nombreCompleto, Date fechaNacimiento, String datosPersonales, String estadoSaludGeneral, List<Anotacion> historialAnotaciones, String medicamentosActuales, String cuidadosEspeciales) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.datosPersonales = datosPersonales;
        this.estadoSaludGeneral = estadoSaludGeneral;
        this.historialAnotaciones = historialAnotaciones;
        this.medicamentosActuales = medicamentosActuales;
        this.cuidadosEspeciales = cuidadosEspeciales;
    }

    public Resident() {
    }

    //Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDatosPersonales() {
        return datosPersonales;
    }

    public void setDatosPersonales(String datosPersonales) {
        this.datosPersonales = datosPersonales;
    }

    public String getEstadoSaludGeneral() {
        return estadoSaludGeneral;
    }

    public void setEstadoSaludGeneral(String estadoSaludGeneral) {
        this.estadoSaludGeneral = estadoSaludGeneral;
    }

    public List<Anotacion> getHistorialAnotaciones() {
        return historialAnotaciones;
    }

    public void setHistorialAnotaciones(List<Anotacion> historialAnotaciones) {
        this.historialAnotaciones = historialAnotaciones;
    }

    public String getMedicamentosActuales() {
        return this.medicamentosActuales;
    }

    public void setMedicamentosActuales(String medicamentosActuales) {
        this.medicamentosActuales = medicamentosActuales;
    }
    public String getCuidadosEspeciales() {
        return this.cuidadosEspeciales;
    }

    public void setCuidadosEspeciales(String cuidadosEspeciales) {
        this.cuidadosEspeciales = cuidadosEspeciales;
    }
}