package br.com.ifba.pagamento.controller;

import br.com.ifba.pagamento.entity.Pagamento;
import br.com.ifba.pagamento.service.PagamentoIService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Controller de Pagamento — delega todas as operações ao Service.
 *
 * @author Sistema de Tributos
 */
@Component
@RequiredArgsConstructor
public class PagamentoController implements PagamentoIController {

    private final PagamentoIService pagamentoService;

    @Override
    public Pagamento save(Long boletoId, Long usuarioId) {
        return pagamentoService.save(boletoId, usuarioId);
    }

    @Override
    public Pagamento findById(Long id) {
        return pagamentoService.findById(id);
    }

    @Override
    public List<Pagamento> findByAtivoTrue() {
        return pagamentoService.findByAtivoTrue();
    }

    @Override
    public List<Pagamento> findByUsuario(Long usuarioId) {
        return pagamentoService.findByUsuario(usuarioId);
    }

    @Override
    public void delete(Long id) {
        pagamentoService.delete(id);
    }
}
