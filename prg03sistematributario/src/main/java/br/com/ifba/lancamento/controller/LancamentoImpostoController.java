package br.com.ifba.lancamento.controller;

import br.com.ifba.lancamento.entity.LancamentoImposto;
import br.com.ifba.lancamento.service.LancamentoImpostoIService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Controller de LancamentoImposto — delega todas as operações ao Service.
 *
 * @author Sistema de Tributos
 */
@Component
@RequiredArgsConstructor
public class LancamentoImpostoController implements LancamentoImpostoIController {

    private final LancamentoImpostoIService lancamentoService;

    @Override
    public LancamentoImposto save(LancamentoImposto lancamento, Integer quantidadeParcelas) {
        return lancamentoService.save(lancamento, quantidadeParcelas);
    }

    @Override
    public LancamentoImposto update(Long id, LancamentoImposto lancamento) {
        return lancamentoService.update(id, lancamento);
    }

    @Override
    public LancamentoImposto findById(Long id) {
        return lancamentoService.findById(id);
    }

    @Override
    public List<LancamentoImposto> findByAtivoTrue() {
        return lancamentoService.findByAtivoTrue();
    }

    @Override
    public List<LancamentoImposto> findByImovel(Long imovelId) {
        return lancamentoService.findByImovel(imovelId);
    }

    @Override
    public void delete(Long id) {
        lancamentoService.delete(id);
    }
}
