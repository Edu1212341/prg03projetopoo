/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.imposto.repository;

import br.com.ifba.imposto.entity.Imposto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author eduar
 */
@Repository
public interface ImpostoRepository extends JpaRepository<Imposto, Long> {
    
    List<Imposto> findByAtivoTrue();
    
    // Busca impostos ativos por ano de exercício
    List<Imposto> findByAnoExercicioAndAtivoTrue(Integer anoExercicio);
}
