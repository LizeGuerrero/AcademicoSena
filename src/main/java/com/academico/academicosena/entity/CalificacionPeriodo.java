package com.academico.academicosena.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "calificaciones_periodo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_matricula", "id_asignatura", "id_periodo"})
})
public class CalificacionPeriodo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_calificacion_periodo")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_matricula", nullable = false)
    private Matricula matricula;
    
    @ManyToOne
    @JoinColumn(name = "id_asignatura", nullable = false)
    private Asignatura asignatura;
    
    @ManyToOne
    @JoinColumn(name = "id_periodo", nullable = false)
    private PeriodoAcademico periodo;
    
    @Column(name = "nota_calculada")
    private BigDecimal notaCalculada;
    
    @Column(name = "nota_final", nullable = false)
    private BigDecimal notaFinal;
    
    @Column(name = "porcentaje_alcanzado")
    private BigDecimal porcentajeAlcanzado;
    
    @Column(name = "fallas")
    private Integer fallas = 0;
    
    @Column(name = "observaciones_generales", columnDefinition = "TEXT")
    private String observacionesGenerales;
    
    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;
    
    @ManyToOne
    @JoinColumn(name = "cerrado_por")
    private Docente cerradoPor;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado = Estado.abierto;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum Estado { abierto, cerrado, modificado }
    
    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Matricula getMatricula() { return matricula; }
    public void setMatricula(Matricula matricula) { this.matricula = matricula; }
    
    public Asignatura getAsignatura() { return asignatura; }
    public void setAsignatura(Asignatura asignatura) { this.asignatura = asignatura; }
    
    public PeriodoAcademico getPeriodo() { return periodo; }
    public void setPeriodo(PeriodoAcademico periodo) { this.periodo = periodo; }
    
    public BigDecimal getNotaCalculada() { return notaCalculada; }
    public void setNotaCalculada(BigDecimal notaCalculada) { this.notaCalculada = notaCalculada; }
    
    public BigDecimal getNotaFinal() { return notaFinal; }
    public void setNotaFinal(BigDecimal notaFinal) { this.notaFinal = notaFinal; }
    
    public BigDecimal getPorcentajeAlcanzado() { return porcentajeAlcanzado; }
    public void setPorcentajeAlcanzado(BigDecimal porcentajeAlcanzado) { this.porcentajeAlcanzado = porcentajeAlcanzado; }
    
    public Integer getFallas() { return fallas; }
    public void setFallas(Integer fallas) { this.fallas = fallas; }
    
    public String getObservacionesGenerales() { return observacionesGenerales; }
    public void setObservacionesGenerales(String observacionesGenerales) { this.observacionesGenerales = observacionesGenerales; }
    
    public LocalDateTime getFechaCierre() { return fechaCierre; }
    public void setFechaCierre(LocalDateTime fechaCierre) { this.fechaCierre = fechaCierre; }
    
    public Docente getCerradoPor() { return cerradoPor; }
    public void setCerradoPor(Docente cerradoPor) { this.cerradoPor = cerradoPor; }
    
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}