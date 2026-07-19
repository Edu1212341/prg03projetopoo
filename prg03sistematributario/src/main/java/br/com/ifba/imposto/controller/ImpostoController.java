/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.imposto.controller;

import br.com.ifba.imposto.entity.Imposto;
import br.com.ifba.imposto.service.ImpostoIService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

/**
 *
 * @author eduar
 */
@Controller
@RequiredArgsConstructor
public class ImpostoController implements ImpostoIController {

    private final ImpostoIService impostoService;

    @Override
    public Imposto save(Imposto imposto) {
        return impostoService.save(imposto);
    }

    @Override
    public Imposto update(Long id, Imposto imposto) {
        return impostoService.update(id, imposto);
    }

    @Override
    public Imposto findById(Long id) {
        return impostoService.findById(id);
    }

    @Override
    public List<Imposto> findByAtivoTrue() {
        return impostoService.findByAtivoTrue();
    }

    @Override
    public void delete(Long id) {
        impostoService.delete(id);
    }
}
