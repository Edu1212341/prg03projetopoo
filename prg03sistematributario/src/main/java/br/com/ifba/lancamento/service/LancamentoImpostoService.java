package br.com.ifba.lancamento.service;

import br.com.ifba.imposto.entity.Imposto;
import br.com.ifba.imposto.repository.ImpostoRepository;
import br.com.ifba.imovel.entity.Imovel;
import br.com.ifba.imovel.repository.ImovelRepository;
import br.com.ifba.lancamento.entity.LancamentoImposto;
import br.com.ifba.lancamento.repository.LancamentoImpostoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Implementação das regras de negócio para LancamentoImposto.
 *
 * @author Sistema de Tributos
 */
@Service
@RequiredArgsConstructor
public class LancamentoImpostoService implements LancamentoImpostoIService {

    private final LancamentoImpostoRepository lancamentoRepository;
    private final ImovelRepository            imovelRepository;
    private final ImpostoRepository           impostoRepository;

    private static final Logger log = LoggerFactory.getLogger(LancamentoImpostoService.class);

    // -------------------------------------------------------------------------
    // SAVE
    // -------------------------------------------------------------------------
    @Override
    public LancamentoImposto save(LancamentoImposto lancamento, Integer quantidadeParcelas) {
        log.info("Iniciando processo de salvar LancamentoImposto.");

        validateParcelas(quantidadeParcelas);

        Imovel  imovel  = findImovelById(lancamento.getImovel().getId());
        Imposto imposto = findImpostoById(lancamento.getImposto().getId());

        if (!imovel.getAtivo()) {
            throw new IllegalArgumentException(
                    "Não é possível lançar imposto sobre um imóvel inativo.");
        }

        int anoAtual = java.time.LocalDate.now().getYear();
        if (lancamentoRepository.existsByImovelIdAndImpostoIdAndAno(
                imovel.getId(), imposto.getId(), anoAtual)) {
            throw new IllegalArgumentException(
                    "Já existe um lançamento ativo para este imóvel com este imposto no ano "
                    + anoAtual + ".");
        }

        lancamento.processarLancamento(imovel, imposto);
        lancamento.gerarBoletosPrefeitura(quantidadeParcelas);

        LancamentoImposto salvo = lancamentoRepository.save(lancamento);
        log.info("LancamentoImposto ID {} salvo. Valor: R$ {}. Parcelas: {}.",
                salvo.getId(), salvo.getValorTotalCalculado(), quantidadeParcelas);
        return salvo;
    }

    // -------------------------------------------------------------------------
    // UPDATE
    // -------------------------------------------------------------------------
    @Override
    public LancamentoImposto update(Long id, LancamentoImposto dadosNovos) {
        log.info("Atualizando LancamentoImposto ID: {}", id);

        LancamentoImposto existente = findById(id);

        if (dadosNovos.getImposto() != null && dadosNovos.getImposto().getId() != null) {
            Imposto novoImposto = findImpostoById(dadosNovos.getImposto().getId());
            existente.setImposto(novoImposto);
            existente.setValorTotalCalculado(
                    novoImposto.calcularImposto(existente.getImovel()));
            log.info("Imposto atualizado. Novo valor total: R$ {}",
                    existente.getValorTotalCalculado());
        }

        return lancamentoRepository.save(existente);
    }


    @Override
    public LancamentoImposto findById(Long id) {
        log.info("Buscando LancamentoImposto ID: {}", id);
        return lancamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Lançamento de imposto não encontrado no banco de dados."));
    }


    @Override
    public List<LancamentoImposto> findByAtivoTrue() {
        log.info("Listando todos os LancamentoImpostos ativos.");
        return lancamentoRepository.findByAtivoTrue();
    }


    @Override
    public List<LancamentoImposto> findByImovel(Long imovelId) {
        log.info("Listando lançamentos do Imóvel ID: {}", imovelId);
        return lancamentoRepository.findByImovelIdAndAtivoTrue(imovelId);
    }


    @Override
    public void delete(Long id) {
        log.info("Inativando LancamentoImposto ID: {}", id);

        if (id == null || id <= 0) {
            log.error("Tentativa de deletar com ID nulo ou inválido.");
            throw new IllegalArgumentException("O ID do lançamento é inválido.");
        }

        LancamentoImposto lancamento = findById(id);
        lancamento.setAtivo(false);
        lancamentoRepository.save(lancamento);

        log.info("LancamentoImposto ID {} inativado com sucesso.", id);
    }


    private Imovel findImovelById(Long id) {
        return imovelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Imóvel de ID " + id + " não encontrado no banco de dados."));
    }

    private Imposto findImpostoById(Long id) {
        return impostoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Imposto de ID " + id + " não encontrado no banco de dados."));
    }

    private void validateParcelas(Integer parcelas) {
        if (parcelas == null || parcelas < 1 || parcelas > 12) {
            throw new IllegalArgumentException(
                    "O número de parcelas deve ser entre 1 e 12.");
        }
    }
}
