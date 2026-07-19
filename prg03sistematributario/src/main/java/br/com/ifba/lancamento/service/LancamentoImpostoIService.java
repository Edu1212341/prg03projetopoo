package br.com.ifba.lancamento.service;

import br.com.ifba.lancamento.entity.LancamentoImposto;
import java.util.List;

/**
 * Contrato de negócio para as operações de LancamentoImposto.
 *
 * @author Sistema de Tributos
 */
public interface LancamentoImpostoIService {

    LancamentoImposto save(LancamentoImposto lancamento, Integer quantidadeParcelas);

    LancamentoImposto update(Long id, LancamentoImposto lancamento);

    LancamentoImposto findById(Long id);

    List<LancamentoImposto> findByAtivoTrue();

    List<LancamentoImposto> findByImovel(Long imovelId);

    void delete(Long id);
}
