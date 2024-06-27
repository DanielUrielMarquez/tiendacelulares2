package com.example.tiendacelulares2.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;

@Entity
@Table(name = "celulares")
public class Celular {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String marca;

    @Column
    private Double precio;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tienda_id", referencedColumnName = "id")
    @JsonManagedReference
    private Tienda tienda;

    @OneToMany(mappedBy = "celular", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MarcaCelular> marcas;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

    public List<MarcaCelular> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<MarcaCelular> marcas) {
        this.marcas = marcas;
    }
}
