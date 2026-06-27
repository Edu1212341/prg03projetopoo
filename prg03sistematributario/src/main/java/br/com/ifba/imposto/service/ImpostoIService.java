/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.imposto.service;

import br.com.ifba.imposto.entity.Imposto;
import java.util.List;

/**
 *
 * @author eduar
 */
public interface ImpostoIService {
    Imposto salvar(Imposto imposto);
    Imposto atualizar(Long id, Imposto imposto);
    Imposto buscarPorId(Long id);
    List<Imposto> listarTodos();
    void deletar(Long id);
}
