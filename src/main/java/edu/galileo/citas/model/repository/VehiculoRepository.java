package edu.galileo.citas.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.galileo.citas.model.Vehiculo;

@Repository
public interface VehiculoRepository extends CrudRepository<Vehiculo, Integer>{
    List<Vehiculo> findAll();
    
}
