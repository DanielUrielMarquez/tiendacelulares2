package com.example.tiendacelulares2.model;

import jakarta.persistence.*;
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
    private String modelo;

    @Column
    private Double precio;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tienda_id", referencedColumnName = "id")
    private Tienda tienda;

    @OneToMany(mappedBy = "celular", cascade = CascadeType.ALL)
    private List<MarcaCelular> marcas;

    public Celular() {}

    // Getters y setters...
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

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
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
