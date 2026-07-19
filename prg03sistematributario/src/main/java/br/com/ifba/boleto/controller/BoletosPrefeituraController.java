package br.com.ifba.boleto.controller;

import br.com.ifba.boleto.entity.BoletosPrefeitura;
import br.com.ifba.boleto.service.BoletosPrefeituraIService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Controller de BoletosPrefeitura — delega todas as operações ao Service.
 *
 * @author Sistema de Tributos
 */
@Component
@RequiredArgsConstructor
public class BoletosPrefeituraController implements BoletosPrefeituraIController {

    private final BoletosPrefeituraIService boletoService;

    @Override
    public BoletosPrefeitura findById(Long id) {
        return boletoService.findById(id);
    }

    @Override
    public List<BoletosPrefeitura> findByAtivoTrue() {
        return boletoService.findByAtivoTrue();
    }

    @Override
    public List<BoletosPrefeitura> findByLancamento(Long lancamentoId) {
        return boletoService.findByLancamento(lancamentoId);
    }

    @Override
    public List<BoletosPrefeitura> findPending() {
        return boletoService.findPending();
    }

    @Override
    public BoletosPrefeitura updateStatus(Long id, String novoStatus) {
        return boletoService.updateStatus(id, novoStatus);
    }

    @Override
    public void delete(Long id) {
        boletoService.delete(id);
    }
}
