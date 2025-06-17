package com.fundacion.apoyo.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fundacion.apoyo.restapi.model.Anotacion;

@Repository
public interface AnotacionRepository extends JpaRepository<Anotacion, Long>{
    
}
