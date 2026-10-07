package com.medpharm.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="medicamento")
public class Medicamento {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, unique=true, length=30)
    private String codigo;
    @Column(nullable=false, length=100)
    private String nombre;
    @Column(nullable=false)
    private Integer stock;
    @Column(name="precio_unitario", nullable=false, precision=10, scale=2)
    private BigDecimal precioUnitario;

    public Medicamento() {}
    public Long getId(){return id;}
    public String getCodigo(){return codigo;}
    public String getNombre(){return nombre;}
    public Integer getStock(){return stock;}
    public BigDecimal getPrecioUnitario(){return precioUnitario;}
    public void setId(Long v){id=v;}
    public void setCodigo(String v){codigo=v;}
    public void setNombre(String v){nombre=v;}
    public void setStock(Integer v){stock=v;}
    public void setPrecioUnitario(BigDecimal v){precioUnitario=v;}
}
