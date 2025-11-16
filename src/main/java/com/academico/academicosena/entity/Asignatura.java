package com.academico.academicosena.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "asignaturas")
public class Asignatura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignatura")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_nivel", nullable = false)
    private Nivel nivel;
    
    @Column(name = "codigo", unique = true, nullable = false, length = 20)
    private String codigo;
    
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "intensidad_horaria_semanal", nullable = false)
    private Integer intensidadHorariaSemanal;
    
    @Column(name = "creditos")
    private Integer creditos;
    
    @Column(name = "area_conocimiento", length = 100)
    private String areaConocimiento;
    
    @Column(name = "es_obligatoria")
    private Boolean esObligatoria = true;
    
    @Column(name = "activo")
    private Boolean activo = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Nivel getNivel() { return nivel; }
    public void setNivel(Nivel nivel) { this.nivel = nivel; }
    
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public Integer getIntensidadHorariaSemanal() { return intensidadHorariaSemanal; }
    public void setIntensidadHorariaSemanal(Integer intensidadHorariaSemanal) { this.intensidadHorariaSemanal = intensidadHorariaSemanal; }
    
    public Integer getCreditos() { return creditos; }
    public void setCreditos(Integer creditos) { this.creditos = creditos; }
    
    public String getAreaConocimiento() { return areaConocimiento; }
    public void setAreaConocimiento(String areaConocimiento) { this.areaConocimiento = areaConocimiento; }
    
    public Boolean getEsObligatoria() { return esObligatoria; }
    public void setEsObligatoria(Boolean esObligatoria) { this.esObligatoria = esObligatoria; }
    
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}