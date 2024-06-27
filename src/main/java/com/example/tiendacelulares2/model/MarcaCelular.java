package com.example.tiendacelulares2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "marca")
public class MarcaCelular {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String modelo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "celular_id")
    @JsonBackReference
    private Celular celular;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public String getModelo() {
        return modelo;
    }

    public Celular getCelular() {
        return celular;
    }

    public void setCelular(Celular celular) {
        this.celular = celular;
    }
}
