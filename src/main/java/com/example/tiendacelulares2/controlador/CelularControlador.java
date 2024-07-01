package com.example.tiendacelulares2.controlador;

import com.example.tiendacelulares2.model.Celular;
import com.example.tiendacelulares2.model.MarcaCelular;
import com.example.tiendacelulares2.model.Tienda;
import com.example.tiendacelulares2.repositorio.CelularRepositorio;
import com.example.tiendacelulares2.repositorio.TiendaRepositorio;
import com.example.tiendacelulares2.repositorio.MarcaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tiendacelulares/celulares")
public class CelularControlador {

    private final CelularRepositorio celularRepositorio;
    private final TiendaRepositorio tiendaRepositorio;
    private final MarcaRepositorio marcaRepositorio;

    @Autowired
    public CelularControlador(CelularRepositorio celularRepositorio, TiendaRepositorio tiendaRepositorio, MarcaRepositorio marcaRepositorio) {
        this.celularRepositorio = celularRepositorio;
        this.tiendaRepositorio = tiendaRepositorio;
        this.marcaRepositorio = marcaRepositorio;
    }

    @GetMapping
    public List<Celular> obtenerTodosLosCelulares() {
        return celularRepositorio.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Celular> obtenerCelularPorId(@PathVariable Long id) {
        Optional<Celular> celularOptional = celularRepositorio.findById(id);
        return celularOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Celular> crearCelular(@RequestBody Celular celular) {
        // Guardar la tienda si no existe en la base de datos
        if (celular.getTienda() != null) {
            Tienda tiendaExistente = tiendaRepositorio.findByNombre(celular.getTienda().getNombre());
            if (tiendaExistente == null) {
                tiendaExistente = tiendaRepositorio.save(celular.getTienda());
            }
            celular.setTienda(tiendaExistente);
        }

        // Guardar las marcas si no existen en la base de datos
        if (celular.getMarcas() != null) {
            for (MarcaCelular marca : celular.getMarcas()) {
                MarcaCelular marcaExistente = marcaRepositorio.findByNombre(marca.getNombre());
                if (marcaExistente == null) {
                    marcaExistente = marcaRepositorio.save(marca);
                }
                marca.setNombre(marcaExistente.getNombre());
                marca.setCelular(celular);
            }
        }

        // Guardar el celular
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

                    if (detallesCelular.getMarcas() != null) {
                        celular.getMarcas().forEach(marca -> marca.setCelular(null));
                        marcaRepositorio.saveAll(celular.getMarcas());
                        detallesCelular.getMarcas().forEach(marca -> marca.setCelular(celular));
                        marcaRepositorio.saveAll(detallesCelular.getMarcas());
                        celular.setMarcas(detallesCelular.getMarcas());
                    }

                    Celular celularActualizado = celularRepositorio.save(celular);
                    return ResponseEntity.ok(celularActualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarCelular(@PathVariable Long id) {
        try {
            return celularRepositorio.findById(id)
                    .map(celular -> {
                        celularRepositorio.delete(celular);
                        return ResponseEntity.ok().build();
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Manejo básico de excepciones
        }
    }
}
