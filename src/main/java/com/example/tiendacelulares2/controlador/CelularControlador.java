package com.example.tiendacelulares2.controlador;

import com.example.tiendacelulares2.model.Celular;
import com.example.tiendacelulares2.model.Tienda;
import com.example.tiendacelulares2.repositorio.CelularRepositorio;
import com.example.tiendacelulares2.repositorio.TiendaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tiendacelulares/celulares")
public class CelularControlador {

    private final CelularRepositorio celularRepositorio;
    private final TiendaRepositorio tiendaRepositorio;

    @Autowired
    public CelularControlador(CelularRepositorio celularRepositorio, TiendaRepositorio tiendaRepositorio) {
        this.celularRepositorio = celularRepositorio;
        this.tiendaRepositorio = tiendaRepositorio;
    }

    @GetMapping
    public List<Celular> obtenerTodosLosCelulares() {
        return celularRepositorio.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Celular> obtenerCelularPorId(@PathVariable Long id) {
        return celularRepositorio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Celular> crearCelular(@RequestBody Celular celular) {
        if (celular.getTienda() != null) {
            tiendaRepositorio.save(celular.getTienda());
        }
        Celular nuevoCelular = celularRepositorio.save(celular);
        return ResponseEntity.ok(nuevoCelular);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Celular> actualizarCelular(@PathVariable Long id, @RequestBody Celular detallesCelular) {
        return celularRepositorio.findById(id)
                .map(celular -> {
                    celular.setMarca(detallesCelular.getMarca());
                    celular.setModelo(detallesCelular.getModelo());
                    celular.setPrecio(detallesCelular.getPrecio());
                    if (detallesCelular.getTienda() != null) {
                        tiendaRepositorio.save(detallesCelular.getTienda());
                        celular.setTienda(detallesCelular.getTienda());
                    }
                    Celular celularActualizado = celularRepositorio.save(celular);
                    return ResponseEntity.ok(celularActualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarCelular(@PathVariable Long id) {
        return celularRepositorio.findById(id)
                .map(celular -> {
                    celularRepositorio.delete(celular);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
