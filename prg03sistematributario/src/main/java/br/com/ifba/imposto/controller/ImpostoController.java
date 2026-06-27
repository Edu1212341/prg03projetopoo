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
    public Imposto salvar(Imposto imposto) {
        return impostoService.salvar(imposto);
    }

    @Override
    public Imposto atualizar(Long id, Imposto imposto) {
        return impostoService.atualizar(id, imposto);
    }

    @Override
    public Imposto buscarPorId(Long id) {
        return impostoService.buscarPorId(id);
    }

    @Override
    public List<Imposto> listarTodos() {
        return impostoService.listarTodos();
    }

    @Override
    public void deletar(Long id) {
        impostoService.deletar(id);
    }
}
