package br.com.ifba.boleto.service;

import br.com.ifba.boleto.entity.BoletosPrefeitura;
import br.com.ifba.boleto.repository.BoletosPrefeituraRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author eduardo
 */
@Service
@RequiredArgsConstructor
public class BoletosPrefeituraService implements BoletosPrefeituraIService {

    private final BoletosPrefeituraRepository boletoRepository;

    private static final Logger log = LoggerFactory.getLogger(BoletosPrefeituraService.class);

    //busca por ID
    @Override
    public BoletosPrefeitura findById(Long id) {
        log.info("Buscando BoletosPrefeitura ID: {}", id);
        return boletoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Boleto não encontrado no banco de dados."));
    }

    // Busca por ativo
    @Override
    public List<BoletosPrefeitura> findByAtivoTrue() {
        log.info("Listando todos os Boletos ativos.");
        return boletoRepository.findByAtivoTrue();
    }

    //busca por lancamento
    @Override
    public List<BoletosPrefeitura> findByLancamento(Long lancamentoId) {
        log.info("Listando boletos do LancamentoImposto ID: {}", lancamentoId);
        return boletoRepository.findByLancamentoImpostoId(lancamentoId);
    }

    // busca por pendente
    @Override
    public List<BoletosPrefeitura> findPending() {
        log.info("Listando boletos com status PENDENTE.");
        return boletoRepository.findByStatusAndAtivoTrue("PENDENTE");
    }

    // atualizar status
    @Override
    public BoletosPrefeitura updateStatus(Long id, String novoStatus) {
        log.info("Atualizando status do Boleto ID {} para '{}'.", id, novoStatus);

        validateStatus(novoStatus);

        BoletosPrefeitura boleto = findById(id);

        if ("PAGO".equals(boleto.getStatus()) && "PENDENTE".equals(novoStatus)) {
            throw new IllegalArgumentException(
                    "Boleto já pago. Para revertê-lo utilize o estorno de pagamento.");
        }

        boleto.setStatus(novoStatus);
        return boletoRepository.save(boleto);
    }

    // deletar
    @Override
    public void delete(Long id) {
        log.info("Inativando BoletosPrefeitura ID: {}", id);

        if (id == null || id <= 0) {
            log.error("Tentativa de deletar com ID nulo ou inválido.");
            throw new IllegalArgumentException("O ID do boleto é inválido.");
        }

        BoletosPrefeitura boleto = findById(id);

        if ("PAGO".equals(boleto.getStatus())) {
            throw new IllegalArgumentException(
                    "Não é possível excluir um boleto já pago. "
                    + "Utilize o estorno de pagamento.");
        }

        boleto.setAtivo(false);
        boletoRepository.save(boleto);
        log.info("BoletosPrefeitura ID {} inativado com sucesso.", id);
    }

    //validar status
    private void validateStatus(String status) {
        if (!"PENDENTE".equals(status)
                && !"PAGO".equals(status)
                && !"VENCIDO".equals(status)) {
            throw new IllegalArgumentException(
                    "Status inválido. Valores aceitos: PENDENTE, PAGO, VENCIDO.");
        }
    }
}
