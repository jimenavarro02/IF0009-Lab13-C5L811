package com.medpharm.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="receta_medica")
public class RecetaMedica {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="codigo_receta", nullable=false, unique=true, length=30)
    private String codigoReceta;
    @Column(name="paciente_nombre", nullable=false, length=120)
    private String pacienteNombre;
    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="medico_id", nullable=false)
    private Usuario medico;
    @Column(nullable=false, length=20)
    private String estado = "PENDIENTE";
    @Column(name="fecha_emision", nullable=false)
    private LocalDateTime fechaEmision;
    @OneToMany(mappedBy="receta", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<DetalleReceta> detalles = new ArrayList<>();

    public RecetaMedica() {}
    @PrePersist void prePersist(){ if(fechaEmision==null) fechaEmision=LocalDateTime.now(); }
    public Long getId(){return id;}
    public String getCodigoReceta(){return codigoReceta;}
    public String getPacienteNombre(){return pacienteNombre;}
    public Usuario getMedico(){return medico;}
    public String getEstado(){return estado;}
    public LocalDateTime getFechaEmision(){return fechaEmision;}
    public List<DetalleReceta> getDetalles(){return detalles;}
    public void setId(Long v){id=v;}
    public void setCodigoReceta(String v){codigoReceta=v;}
    public void setPacienteNombre(String v){pacienteNombre=v;}
    public void setMedico(Usuario v){medico=v;}
    public void setEstado(String v){estado=v;}
    public void setFechaEmision(LocalDateTime v){fechaEmision=v;}
    public void setDetalles(List<DetalleReceta> v){detalles=v;}
}
