package com.example.tiendacelulares2.repositorio;

import com.example.tiendacelulares2.model.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TiendaRepositorio extends JpaRepository<Tienda, Long> {
    Tienda findByNombre(String nombre);
}
