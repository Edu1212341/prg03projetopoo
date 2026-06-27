package br.com.ifba.imovel.service;

import br.com.ifba.imovel.entity.Imovel;
import java.util.List;

/**
 * Contrato de negócio para as operações de Imovel.
 * Espelho de ContribuinteIService e UsuarioIService.
 */
public interface ImovelIService {

    Imovel salvar(Imovel imovel);

    Imovel atualizar(Long id, Imovel imovel);

    Imovel buscarPorId(Long id);

    List<Imovel> listarTodos();

    List<Imovel> listarPorContribuinte(Long contribuinteId);

    void deletar(Long id);
}
