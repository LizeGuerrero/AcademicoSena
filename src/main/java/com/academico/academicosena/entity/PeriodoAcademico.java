package com.academico.academicosena.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "periodos_academicos")
public class PeriodoAcademico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_periodo")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_configuracion", nullable = false)
    private ConfiguracionPeriodo configuracion;
    
    @ManyToOne
    @JoinColumn(name = "id_nivel", nullable = false)
    private Nivel nivel;
    
    @Column(name = "numero_periodo", nullable = false)
    private Integer numeroPeriodo;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "porcentaje_nota", nullable = false)
    private BigDecimal porcentajeNota;
    
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;
    
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;
    
    @Column(name = "semanas_duracion")
    private Integer semanasDuracion;
    
    @Column(name = "es_activo")
    private Boolean esActivo = false;
    
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public ConfiguracionPeriodo getConfiguracion() { return configuracion; }
    public void setConfiguracion(ConfiguracionPeriodo configuracion) { this.configuracion = configuracion; }
    
    public Nivel getNivel() { return nivel; }
    public void setNivel(Nivel nivel) { this.nivel = nivel; }
    
    public Integer getNumeroPeriodo() { return numeroPeriodo; }
    public void setNumeroPeriodo(Integer numeroPeriodo) { this.numeroPeriodo = numeroPeriodo; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public BigDecimal getPorcentajeNota() { return porcentajeNota; }
    public void setPorcentajeNota(BigDecimal porcentajeNota) { this.porcentajeNota = porcentajeNota; }
    
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    
    public Integer getSemanasDuracion() { return semanasDuracion; }
    public void setSemanasDuracion(Integer semanasDuracion) { this.semanasDuracion = semanasDuracion; }
    
    public Boolean getEsActivo() { return esActivo; }
    public void setEsActivo(Boolean esActivo) { this.esActivo = esActivo; }
    
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}