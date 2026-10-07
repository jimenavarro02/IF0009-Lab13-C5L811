package com.medpharm.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, unique=true, length=50)
    private String username;
    @Column(nullable=false, length=100)
    private String password;
    @Column(name="nombre_completo", nullable=false, length=120)
    private String nombreCompleto;
    @Column(nullable=false, length=30)
    private String rol;

    public Usuario() {}
    public Long getId(){ return id; }
    public String getUsername(){ return username; }
    public String getPassword(){ return password; }
    public String getNombreCompleto(){ return nombreCompleto; }
    public String getRol(){ return rol; }
    public void setId(Long id){ this.id=id; }
    public void setUsername(String v){ this.username=v; }
    public void setPassword(String v){ this.password=v; }
    public void setNombreCompleto(String v){ this.nombreCompleto=v; }
    public void setRol(String v){ this.rol=v; }
}
