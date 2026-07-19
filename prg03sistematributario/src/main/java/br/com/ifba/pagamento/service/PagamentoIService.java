package br.com.ifba.pagamento.service;

import br.com.ifba.pagamento.entity.Pagamento;
import java.util.List;

/**
 * Contrato de negócio para as operações de Pagamento.
 *
 * @author Sistema de Tributos
 */
public interface PagamentoIService {

    Pagamento save(Long boletoId, Long usuarioId);

    Pagamento findById(Long id);

    List<Pagamento> findByAtivoTrue();

    List<Pagamento> findByUsuario(Long usuarioId);

    void delete(Long id);
}
