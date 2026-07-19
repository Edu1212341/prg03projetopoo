package br.com.ifba.boleto.controller;

import br.com.ifba.boleto.entity.BoletosPrefeitura;
import java.util.List;

/**
 * Contrato do controller para BoletosPrefeitura.
 *
 * @author Sistema de Tributos
 */
public interface BoletosPrefeituraIController {

    BoletosPrefeitura findById(Long id);

    List<BoletosPrefeitura> findByAtivoTrue();

    List<BoletosPrefeitura> findByLancamento(Long lancamentoId);

    List<BoletosPrefeitura> findPending();

    BoletosPrefeitura updateStatus(Long id, String novoStatus);

    void delete(Long id);
}
