package com.example.tiendacelulares2.controlador;

import com.example.tiendacelulares2.model.Celular;
import com.example.tiendacelulares2.model.MarcaCelular;
import com.example.tiendacelulares2.repositorio.CelularRepositorio;
import com.example.tiendacelulares2.repositorio.TiendaRepositorio;
import com.example.tiendacelulares2.repositorio.MarcaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return celularRepositorio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Celular> crearCelular(@RequestBody Celular celular) {
        if (celular.getTienda() != null) {
            tiendaRepositorio.save(celular.getTienda());
        }
        if (celular.getMarcas() != null) {
            celular.getMarcas().forEach(marca -> marca.setCelular(celular));
        }
        Celular nuevoCelular = celularRepositorio.save(celular);
        return ResponseEntity.ok(nuevoCelular);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Celular> actualizarCelular(@PathVariable Long id, @RequestBody Celular detallesCelular) {
        return celularRepositorio.findById(id)
                .map(celular -> {
                    celular.setMarca(detallesCelular.getMarca());
                    celular.setPrecio(detallesCelular.getPrecio());

                    if (detallesCelular.getTienda() != null) {
                        tiendaRepositorio.save(detallesCelular.getTienda());
                        celular.setTienda(detallesCelular.getTienda());
                    }

                    if (detallesCelular.getMarcas() != null) {
                        // Limpiar marcas actuales
                        for (MarcaCelular marcaCelular : celular.getMarcas()) {
                            marcaCelular.setCelular(null);
                            marcaRepositorio.save(marcaCelular);
                        }

                        // Asociar nuevas marcas
                        for (MarcaCelular marca : detallesCelular.getMarcas()) {
                            marca.setCelular(celular);
                             marcaRepositorio.save(marca);
                        }

                        // Establecer las nuevas marcas en el celular
                        celular.setMarcas(detallesCelular.getMarcas());
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
