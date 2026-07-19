/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.imposto.controller;

import br.com.ifba.imposto.entity.Imposto;
import java.util.List;
import org.springframework.stereotype.Controller;

/**
 *
 * @author eduar
 */
@Controller
public interface ImpostoIController {
    Imposto save(Imposto imposto);
    Imposto update(Long id, Imposto imposto);
    Imposto findById(Long id);
    List<Imposto> findByAtivoTrue();
    void delete(Long id);
}
