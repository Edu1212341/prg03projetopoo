package br.com.ifba.imovel.service;

import br.com.ifba.imovel.entity.Imovel;
import java.util.List;

/**
 * Contrato de negócio para as operações de Imovel.
 * Espelho de ContribuinteIService e UsuarioIService.
 */
public interface ImovelIService {

    Imovel save(Imovel imovel);

    Imovel update(Long id, Imovel imovel);

    Imovel findById(Long id);

    List<Imovel> findByAtivoTrue();

    List<Imovel> findByContribuinteIdAndAtivoTrue(Long contribuinteId);

    void deletar(Long id);
}
