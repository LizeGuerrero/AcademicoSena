package com.academico.academicosena.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "programas_academicos")
public class ProgramaAcademico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_programa")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_institucion", nullable = false)
    private Institucion institucion;
    
    @ManyToOne
    @JoinColumn(name = "id_tipo_programa", nullable = false)
    private TipoPrograma tipoPrograma;
    
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "duracion_niveles", nullable = false)
    private Integer duracionNiveles;
    
    @Column(name = "creditos_totales")
    private Integer creditosTotales;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "modalidad")
    private Modalidad modalidad = Modalidad.presencial;
    
    @Column(name = "activo")
    private Boolean activo = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "programa", cascade = CascadeType.ALL)
    private List<Nivel> niveles;
    
    public enum Modalidad {
        presencial, virtual, distancia, dual
    }
    
    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Institucion getInstitucion() { return institucion; }
    public void setInstitucion(Institucion institucion) { this.institucion = institucion; }
    
    public TipoPrograma getTipoPrograma() { return tipoPrograma; }
    public void setTipoPrograma(TipoPrograma tipoPrograma) { this.tipoPrograma = tipoPrograma; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public Integer getDuracionNiveles() { return duracionNiveles; }
    public void setDuracionNiveles(Integer duracionNiveles) { this.duracionNiveles = duracionNiveles; }
    
    public Integer getCreditosTotales() { return creditosTotales; }
    public void setCreditosTotales(Integer creditosTotales) { this.creditosTotales = creditosTotales; }
    
    public Modalidad getModalidad() { return modalidad; }
    public void setModalidad(Modalidad modalidad) { this.modalidad = modalidad; }
    
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    
    public List<Nivel> getNiveles() { return niveles; }
    public void setNiveles(List<Nivel> niveles) { this.niveles = niveles; }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}