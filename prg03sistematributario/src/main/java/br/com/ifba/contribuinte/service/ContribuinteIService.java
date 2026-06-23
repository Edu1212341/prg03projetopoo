package br.com.ifba.contribuinte.service;

import br.com.ifba.contribuinte.entity.Contribuinte;
import java.util.List;

/**
 * Contrato de negócio para as operações de Contribuinte.
 */
public interface ContribuinteIService {

    Contribuinte salvar(Contribuinte contribuinte);
    Contribuinte atualizar(Long id, Contribuinte contribuinte);
    Contribuinte buscarPorId(Long id);
    List<Contribuinte> listarTodos();
    void deletar(Long id);
}
