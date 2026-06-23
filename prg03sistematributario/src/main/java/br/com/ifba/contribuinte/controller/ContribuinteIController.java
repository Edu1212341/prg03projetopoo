package br.com.ifba.contribuinte.controller;

import br.com.ifba.contribuinte.entity.Contribuinte;
import java.util.List;

/**
 * Contrato de interface para o Controller de Contribuinte.
 * Espelho de UsuarioIController.
 */
public interface ContribuinteIController {

    Contribuinte salvar(Contribuinte contribuinte);

    Contribuinte atualizar(Long id, Contribuinte contribuinte);

    Contribuinte buscarPorId(Long id);

    List<Contribuinte> listarTodos();

    void deletar(Long id);
}
