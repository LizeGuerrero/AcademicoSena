package com.academico.academicosena.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "escalas_calificacion")
public class EscalaCalificacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_escala")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_institucion", nullable = false)
    private Institucion institucion;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "nota_minima", nullable = false)
    private BigDecimal notaMinima;
    
    @Column(name = "nota_maxima", nullable = false)
    private BigDecimal notaMaxima;
    
    @Column(name = "nota_aprobatoria", nullable = false)
    private BigDecimal notaAprobatoria;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "activo")
    private Boolean activo = true;
    
    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Institucion getInstitucion() { return institucion; }
    public void setInstitucion(Institucion institucion) { this.institucion = institucion; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public BigDecimal getNotaMinima() { return notaMinima; }
    public void setNotaMinima(BigDecimal notaMinima) { this.notaMinima = notaMinima; }
    
    public BigDecimal getNotaMaxima() { return notaMaxima; }
    public void setNotaMaxima(BigDecimal notaMaxima) { this.notaMaxima = notaMaxima; }
    
    public BigDecimal getNotaAprobatoria() { return notaAprobatoria; }
    public void setNotaAprobatoria(BigDecimal notaAprobatoria) { this.notaAprobatoria = notaAprobatoria; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}