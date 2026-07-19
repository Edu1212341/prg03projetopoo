package br.com.ifba.lancamento.controller;

import br.com.ifba.lancamento.entity.LancamentoImposto;
import java.util.List;

/**
 * Contrato do controller para LancamentoImposto.
 *
 * @author Sistema de Tributos
 */
public interface LancamentoImpostoIController {

    LancamentoImposto save(LancamentoImposto lancamento, Integer quantidadeParcelas);

    LancamentoImposto update(Long id, LancamentoImposto lancamento);

    LancamentoImposto findById(Long id);

    List<LancamentoImposto> findByAtivoTrue();

    List<LancamentoImposto> findByImovel(Long imovelId);

    void delete(Long id);
}
