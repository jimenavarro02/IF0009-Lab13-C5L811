package com.medpharm.model;

import jakarta.persistence.*;

@Entity
@Table(name="detalle_receta")
public class DetalleReceta {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="receta_id", nullable=false)
    private RecetaMedica receta;
    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="medicamento_id", nullable=false)
    private Medicamento medicamento;
    @Column(nullable=false)
    private Integer cantidad;
    @Column(name="dosis_indicada", nullable=false, length=255)
    private String dosisIndicada;

    public DetalleReceta() {}
    public Long getId(){return id;}
    public RecetaMedica getReceta(){return receta;}
    public Medicamento getMedicamento(){return medicamento;}
    public Integer getCantidad(){return cantidad;}
    public String getDosisIndicada(){return dosisIndicada;}
    public void setId(Long v){id=v;}
    public void setReceta(RecetaMedica v){receta=v;}
    public void setMedicamento(Medicamento v){medicamento=v;}
    public void setCantidad(Integer v){cantidad=v;}
    public void setDosisIndicada(String v){dosisIndicada=v;}
}
