package br.com.ifba.boleto.service;

import br.com.ifba.boleto.entity.BoletosPrefeitura;
import java.util.List;

/**
 
 * @author eduardo
 */
public interface BoletosPrefeituraIService {

    BoletosPrefeitura findById(Long id);

    List<BoletosPrefeitura> findByAtivoTrue();

    List<BoletosPrefeitura> findByLancamento(Long lancamentoId);

    List<BoletosPrefeitura> findPending();

    BoletosPrefeitura updateStatus(Long id, String novoStatus);

    void delete(Long id);
}
