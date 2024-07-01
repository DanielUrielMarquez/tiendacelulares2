package com.example.tiendacelulares2.repositorio;

import com.example.tiendacelulares2.model.MarcaCelular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaRepositorio extends JpaRepository<MarcaCelular, Long> {
    MarcaCelular findByNombre(String nombre);
}
