package br.com.ifba.imovel.service;

import br.com.ifba.contribuinte.entity.Contribuinte;
import br.com.ifba.contribuinte.entity.Endereco;
import br.com.ifba.contribuinte.repository.ContribuinteRepository;
import br.com.ifba.infrastructure.util.StringUtil;
import br.com.ifba.imovel.entity.Imovel;
import br.com.ifba.imovel.repository.ImovelRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author eduar
 */
@Service
@RequiredArgsConstructor
public class ImovelService implements ImovelIService {

    private final ImovelRepository       imovelRepository;
    private final ContribuinteRepository contribuinteRepository; // para buscar o dono do imóvel

    private static final Logger log = LoggerFactory.getLogger(ImovelService.class);

 
    @Override
    public Imovel save(Imovel imovel) {
        log.info("Iniciando o processo de salvar Imovel.");
        validarImovel(imovel);

        // Verifica unicidade da inscrição imobiliária
        Optional<Imovel> duplicado =
                imovelRepository.findByInscricaoImobiliaria(imovel.getInscricaoImobiliaria());
        if (duplicado.isPresent()) {
            throw new IllegalArgumentException("Esta inscrição imobiliária já está cadastrada no sistema.");
        }

        // Garante que o Contribuinte informado existe no banco antes de associar
        Contribuinte contribuinte = buscarContribuintePorId(imovel.getContribuinte().getId());
        imovel.setContribuinte(contribuinte);

        // CascadeType.ALL garante que o Endereco é persistido junto com o Imovel
        return imovelRepository.save(imovel);
    }


    @Override
    public Imovel update(Long id, Imovel dadosNovos) {
        log.info("Iniciando processo de atualização de Imovel para o ID: {}", id);

        Imovel imovelExistente = findById(id);

        // Verifica unicidade da nova inscrição imobiliária (ignora o próprio registro)
        Optional<Imovel> duplicado =
                imovelRepository.findByInscricaoImobiliaria(dadosNovos.getInscricaoImobiliaria());
        if (duplicado.isPresent() && !duplicado.get().getId().equals(id)) {
            throw new IllegalArgumentException(
                    "Esta inscrição imobiliária já está sendo utilizada por outro imóvel.");
        }

        // Atualiza os campos simples
        imovelExistente.setInscricaoImobiliaria(dadosNovos.getInscricaoImobiliaria());
        imovelExistente.setAreaTerreno(dadosNovos.getAreaTerreno());
        imovelExistente.setAreaConstruida(dadosNovos.getAreaConstruida());
        imovelExistente.setValorVenal(dadosNovos.getValorVenal());

        // Atualiza o Contribuinte se foi alterado
        if (dadosNovos.getContribuinte() != null && dadosNovos.getContribuinte().getId() != null) {
            Contribuinte novoContribuinte =
                    buscarContribuintePorId(dadosNovos.getContribuinte().getId());
            imovelExistente.setContribuinte(novoContribuinte);
        }

        // Merge in-place do Endereco — mesmo padrão de ContribuinteService
        Endereco enderecoNovo       = dadosNovos.getEndereco();
        Endereco enderecoGerenciado = imovelExistente.getEndereco();

        if (enderecoGerenciado == null) {
            imovelExistente.setEndereco(enderecoNovo);
        } else {
            enderecoGerenciado.setLogradouro(enderecoNovo.getLogradouro());
            enderecoGerenciado.setNumero(enderecoNovo.getNumero());
            enderecoGerenciado.setComplemento(enderecoNovo.getComplemento());
            enderecoGerenciado.setBairro(enderecoNovo.getBairro());
            enderecoGerenciado.setCidade(enderecoNovo.getCidade());
            enderecoGerenciado.setEstado(enderecoNovo.getEstado());
            enderecoGerenciado.setCep(enderecoNovo.getCep());
        }

        validarImovel(imovelExistente);

        return imovelRepository.save(imovelExistente);
    }

 
    @Override
    public Imovel findById(Long id) {
        log.info("Buscando Imovel de ID: {}.", id);

        return imovelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Imóvel não encontrado no banco de dados."));
    }


    @Override
    public List<Imovel> findByAtivoTrue() {
        log.info("Listando todos os Imóveis ativos.");
        return imovelRepository.findByAtivoTrue();
    }


    @Override
    public List<Imovel> findByContribuinteIdAndAtivoTrue(Long contribuinteId) {
        log.info("Listando imóveis ativos do Contribuinte ID: {}", contribuinteId);
        return imovelRepository.findByContribuinteIdAndAtivoTrue(contribuinteId);
    }

    @Override
    public void deletar(Long id) {
        log.info("Iniciando a INATIVAÇÃO do Imóvel ID: {}", id);

        if (id == null || id <= 0) {
            log.error("Tentativa de deletar com ID nulo ou inválido.");
            throw new IllegalArgumentException("O ID do imóvel é inválido.");
        }

        Imovel imovel = findById(id);
        imovel.setAtivo(false);
        imovelRepository.save(imovel);

        log.info("Imóvel ID {} inativado com sucesso.", id);
    }

    public void validarImovel(Imovel imovel) {
        log.info("Executando a validação de negócio para Imovel.");

        if (imovel == null) {
            log.error("Falha: objeto Imovel nulo.");
            throw new IllegalArgumentException("Os dados do imóvel não foram preenchidos.");
        }

        // Inscrição imobiliária
        if (StringUtil.isNullOrEmpty(imovel.getInscricaoImobiliaria())) {
            throw new IllegalArgumentException("A inscrição imobiliária é obrigatória.");
        }
        imovel.setInscricaoImobiliaria(imovel.getInscricaoImobiliaria().trim().toUpperCase());

        // Área do terreno
        if (imovel.getAreaTerreno() == null || imovel.getAreaTerreno() <= 0) {
            throw new IllegalArgumentException("A área do terreno deve ser maior que zero.");
        }

        // Área construída
        if (imovel.getAreaConstruida() == null || imovel.getAreaConstruida() < 0) {
            throw new IllegalArgumentException("A área construída não pode ser negativa.");
        }

        // Valor venal
        if (imovel.getValorVenal() == null || imovel.getValorVenal() <= 0) {
            throw new IllegalArgumentException("O valor venal deve ser maior que zero.");
        }

        // Contribuinte obrigatório
        if (imovel.getContribuinte() == null) {
            throw new IllegalArgumentException("O imóvel deve estar associado a um contribuinte.");
        }

        // Endereço
        validarEndereco(imovel.getEndereco());

        log.info("Validação de Imovel concluída com sucesso.");
    }

    private Contribuinte buscarContribuintePorId(Long contribuinteId) {
        return contribuinteRepository.findById(contribuinteId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Contribuinte de ID " + contribuinteId + " não encontrado no banco de dados."));
    }

    
    // Valida Endereco (mesmo padrão de ContribuinteService)
    private void validarEndereco(Endereco endereco) {
        if (endereco == null) {
            throw new IllegalArgumentException("Os dados de endereço do imóvel são obrigatórios.");
        }
        if (StringUtil.isNullOrEmpty(endereco.getLogradouro())) {
            throw new IllegalArgumentException("O logradouro do endereço é obrigatório.");
        }
        if (StringUtil.isNullOrEmpty(endereco.getNumero())) {
            throw new IllegalArgumentException("O número do endereço é obrigatório.");
        }
        if (StringUtil.isNullOrEmpty(endereco.getBairro())) {
            throw new IllegalArgumentException("O bairro do endereço é obrigatório.");
        }
        if (StringUtil.isNullOrEmpty(endereco.getCidade())) {
            throw new IllegalArgumentException("A cidade do endereço é obrigatória.");
        }
        if (StringUtil.isNullOrEmpty(endereco.getEstado())) {
            throw new IllegalArgumentException("O estado é obrigatório.");
        }
        String cepDigitos = endereco.getCep() == null ? "" : endereco.getCep().replaceAll("\\D", "");
        if (cepDigitos.length() != 8) {
            throw new IllegalArgumentException("O CEP deve ter exatamente 8 dígitos.");
        }

        // Normaliza
        endereco.setLogradouro(endereco.getLogradouro().trim());
        endereco.setNumero(endereco.getNumero().trim());
        if (!StringUtil.isNullOrEmpty(endereco.getComplemento())) {
            endereco.setComplemento(endereco.getComplemento().trim());
        }
        endereco.setBairro(endereco.getBairro().trim());
        endereco.setCidade(endereco.getCidade().trim());
        endereco.setEstado(endereco.getEstado().trim().toUpperCase());
        endereco.setCep(cepDigitos);
    }
}
