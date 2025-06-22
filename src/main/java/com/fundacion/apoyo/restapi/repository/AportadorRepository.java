package com.fundacion.apoyo.restapi.repository;

import com.fundacion.apoyo.restapi.model.Aportador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AportadorRepository extends JpaRepository<Aportador, Long> {
}