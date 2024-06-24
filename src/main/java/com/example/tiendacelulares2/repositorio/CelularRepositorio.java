package com.example.tiendacelulares2.repositorio;

import com.example.tiendacelulares2.model.Celular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CelularRepositorio extends JpaRepository<Celular, Long> {
}

