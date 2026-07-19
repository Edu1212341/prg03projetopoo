package br.com.ifba.imovel.controller;

import br.com.ifba.imovel.entity.Imovel;
import java.util.List;

/**
 *
 * @author eduar
 */
public interface ImovelIController {

    Imovel save(Imovel imovel);

    Imovel update(Long id, Imovel imovel);

    Imovel findById(Long id);

    List<Imovel> findByAtivoTrue();

    List<Imovel> findByContribuinteIdAndAtivoTrue(Long contribuinteId);

    void delete(Long id);
}
