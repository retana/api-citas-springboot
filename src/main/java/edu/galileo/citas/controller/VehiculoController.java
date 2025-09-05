package edu.galileo.citas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.galileo.citas.model.Vehiculo;
import edu.galileo.citas.model.repository.VehiculoRepository;
import edu.galileo.citas.service.VehiculoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/vehiculos")
@SecurityRequirement(name = "bearerAuth")
public class VehiculoController {

    private final VehiculoService vehiculoService;
    private final VehiculoRepository vehiculoRepository;

    @Autowired
    public VehiculoController(VehiculoService vehiculoService, VehiculoRepository vehiculoRepository) {
        this.vehiculoService = vehiculoService;
        this.vehiculoRepository = vehiculoRepository;
    }

    @GetMapping
    public List<Vehiculo> findAll() { return vehiculoService.findAll(); }

    @PostMapping
    public Vehiculo save(@RequestBody Vehiculo v) { return vehiculoService.save(v); }

    @GetMapping("/{id}")
    public Vehiculo getById(@PathVariable Integer id) { return vehiculoRepository.findById(id).orElseThrow(); }

    @PutMapping("/{id}")
    public Vehiculo update(@PathVariable Integer id, @RequestBody Vehiculo v) {
        Vehiculo current = vehiculoRepository.findById(id).orElseThrow();
        current.setMarca(v.getMarca());
        current.setModelo(v.getModelo());
        current.setPlaca(v.getPlaca());
        current.setTipo(v.getTipo());
        current.setUsuario(v.getUsuario());
        return vehiculoRepository.save(current);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { vehiculoRepository.deleteById(id); }
}
