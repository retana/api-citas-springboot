package edu.galileo.citas.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.galileo.citas.model.Cita;

@Repository
public interface CitaRepository extends CrudRepository<Cita, Integer> {
    List<Cita> findAll();
}
