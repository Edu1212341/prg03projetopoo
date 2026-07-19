package br.com.ifba.pagamento.controller;

import br.com.ifba.pagamento.entity.Pagamento;
import java.util.List;

/**
 * Contrato do controller para Pagamento.
 *
 * @author Sistema de Tributos
 */
public interface PagamentoIController {

    Pagamento save(Long boletoId, Long usuarioId);

    Pagamento findById(Long id);

    List<Pagamento> findByAtivoTrue();

    List<Pagamento> findByUsuario(Long usuarioId);

    void delete(Long id);
}
