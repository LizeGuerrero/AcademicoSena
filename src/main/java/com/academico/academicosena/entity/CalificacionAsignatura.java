package com.academico.academicosena.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "calificaciones_asignatura", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_matricula", "id_asignatura"})
})
public class CalificacionAsignatura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_calificacion_asignatura")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_matricula", nullable = false)
    private Matricula matricula;
    
    @ManyToOne
    @JoinColumn(name = "id_asignatura", nullable = false)
    private Asignatura asignatura;
    
    @Column(name = "nota_final", nullable = false)
    private BigDecimal notaFinal;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_asignatura")
    private EstadoAsignatura estadoAsignatura = EstadoAsignatura.cursando;
    
    @Column(name = "requiere_recuperacion")
    private Boolean requiereRecuperacion = false;
    
    @Column(name = "total_fallas")
    private Integer totalFallas = 0;
    
    @Column(name = "observaciones_finales", columnDefinition = "TEXT")
    private String observacionesFinales;
    
    @Column(name = "fecha_finalizacion")
    private LocalDate fechaFinalizacion;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum EstadoAsignatura { cursando, aprobada, reprobada, validada, homologada }
    
    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Matricula getMatricula() { return matricula; }
    public void setMatricula(Matricula matricula) { this.matricula = matricula; }
    
    public Asignatura getAsignatura() { return asignatura; }
    public void setAsignatura(Asignatura asignatura) { this.asignatura = asignatura; }
    
    public BigDecimal getNotaFinal() { return notaFinal; }
    public void setNotaFinal(BigDecimal notaFinal) { this.notaFinal = notaFinal; }
    
    public EstadoAsignatura getEstadoAsignatura() { return estadoAsignatura; }
    public void setEstadoAsignatura(EstadoAsignatura estadoAsignatura) { this.estadoAsignatura = estadoAsignatura; }
    
    public Boolean getRequiereRecuperacion() { return requiereRecuperacion; }
    public void setRequiereRecuperacion(Boolean requiereRecuperacion) { this.requiereRecuperacion = requiereRecuperacion; }
    
    public Integer getTotalFallas() { return totalFallas; }
    public void setTotalFallas(Integer totalFallas) { this.totalFallas = totalFallas; }
    
    public String getObservacionesFinales() { return observacionesFinales; }
    public void setObservacionesFinales(String observacionesFinales) { this.observacionesFinales = observacionesFinales; }
    
    public LocalDate getFechaFinalizacion() { return fechaFinalizacion; }
    public void setFechaFinalizacion(LocalDate fechaFinalizacion) { this.fechaFinalizacion = fechaFinalizacion; }
    
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