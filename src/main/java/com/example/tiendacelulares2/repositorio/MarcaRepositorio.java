package com.example.tiendacelulares2.repositorio;

import com.example.tiendacelulares2.model.Celular;
import com.example.tiendacelulares2.model.MarcaCelular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MarcaRepositorio extends JpaRepository<Celular, Long> {
}