package com.academico.academicosena.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "niveles", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_programa", "numero_nivel"})
})
public class Nivel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nivel")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_programa", nullable = false)
    private ProgramaAcademico programa;
    
    @Column(name = "numero_nivel", nullable = false)
    private Integer numeroNivel;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "orden", nullable = false)
    private Integer orden;
    
    @OneToMany(mappedBy = "nivel", cascade = CascadeType.ALL)
    private List<Asignatura> asignaturas;
    
    @OneToMany(mappedBy = "nivel", cascade = CascadeType.ALL)
    private List<PeriodoAcademico> periodos;
    
    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public ProgramaAcademico getPrograma() { return programa; }
    public void setPrograma(ProgramaAcademico programa) { this.programa = programa; }
    
    public Integer getNumeroNivel() { return numeroNivel; }
    public void setNumeroNivel(Integer numeroNivel) { this.numeroNivel = numeroNivel; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }
    
    public List<Asignatura> getAsignaturas() { return asignaturas; }
    public void setAsignaturas(List<Asignatura> asignaturas) { this.asignaturas = asignaturas; }
    
    public List<PeriodoAcademico> getPeriodos() { return periodos; }
    public void setPeriodos(List<PeriodoAcademico> periodos) { this.periodos = periodos; }
}
