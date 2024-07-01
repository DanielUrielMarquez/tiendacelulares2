package com.example.tiendacelulares2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "marcas")
public class MarcaCelular {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "celular_id", referencedColumnName = "id")
    @JsonIgnore // Evitará la serialización recursiva
    private Celular celular;

    public MarcaCelular() {}

    // Getters y setters...
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Celular getCelular() {
        return celular;
    }

    public void setCelular(Celular celular) {
        this.celular = celular;
    }
}
