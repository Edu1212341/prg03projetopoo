package br.com.ifba.imovel.controller;

import br.com.ifba.imovel.entity.Imovel;
import java.util.List;

/**
 * Contrato de interface para o Controller de Imovel.
 * Espelho de ContribuinteIController e UsuarioIController.
 */
public interface ImovelIController {

    Imovel salvar(Imovel imovel);

    Imovel atualizar(Long id, Imovel imovel);

    Imovel buscarPorId(Long id);

    List<Imovel> listarTodos();

    List<Imovel> listarPorContribuinte(Long contribuinteId);

    void deletar(Long id);
}
