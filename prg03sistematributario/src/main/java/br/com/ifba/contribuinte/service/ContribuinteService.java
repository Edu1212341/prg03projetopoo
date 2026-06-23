package br.com.ifba.contribuinte.service;

import br.com.ifba.contribuinte.entity.Contribuinte;
import br.com.ifba.contribuinte.entity.Endereco;
import br.com.ifba.contribuinte.repository.ContribuinteRepository;
import br.com.ifba.infrastructure.util.StringUtil;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Camada de negócio para Contribuinte.
 *
 * DECISÃO IMPORTANTE — atualizar() com Endereco como @Entity:
 * Como o Endereco agora é uma entidade gerenciada pelo JPA (tem seu próprio ID
 * e ciclo de vida), NÃO podemos simplesmente substituir o objeto com setEndereco().
 * Isso "abandonaria" o Endereco antigo no banco (registro órfão) e tentaria
 * inserir um novo objeto transiente, causando erro ou duplicidade.
 *
 * A solução correta é copiar os campos novos para o objeto já gerenciado (merge in-place).
 */
@Service
@RequiredArgsConstructor
public class ContribuinteService implements ContribuinteIService {

    private final ContribuinteRepository contribuinteRepository;

    private static final Logger log = LoggerFactory.getLogger(ContribuinteService.class);

    // -------------------------------------------------------------------------
    // SALVAR
    // -------------------------------------------------------------------------
    @Override
    public Contribuinte salvar(Contribuinte contribuinte) {
        log.info("Iniciando o processo de salvar Contribuinte.");
        validarContribuinte(contribuinte);

        Optional<Contribuinte> duplicado = contribuinteRepository.findByCpfCnpj(contribuinte.getCpfCnpj());
        if (duplicado.isPresent()) {
            throw new IllegalArgumentException("Este CPF/CNPJ já está cadastrado no sistema.");
        }

        // CascadeType.ALL garante que o Endereco será persistido junto com o Contribuinte
        return contribuinteRepository.save(contribuinte);
    }

    // -------------------------------------------------------------------------
    // ATUALIZAR
    // -------------------------------------------------------------------------
    @Override
    public Contribuinte atualizar(Long id, Contribuinte dadosNovos) {
        log.info("Iniciando processo de atualização de Contribuinte para o ID: {}", id);

        Contribuinte contribuinteExistente = buscarPorId(id);

        // Verifica unicidade do CPF/CNPJ novo (ignora o próprio registro)
        Optional<Contribuinte> duplicado = contribuinteRepository.findByCpfCnpj(dadosNovos.getCpfCnpj());
        if (duplicado.isPresent() && !duplicado.get().getId().equals(id)) {
            throw new IllegalArgumentException("Este CPF/CNPJ já está sendo utilizado por outro contribuinte.");
        }

        // Atualiza os campos simples do Contribuinte
        contribuinteExistente.setNome(dadosNovos.getNome());
        contribuinteExistente.setCpfCnpj(dadosNovos.getCpfCnpj());
        contribuinteExistente.setTelefone(dadosNovos.getTelefone());
        contribuinteExistente.setEmail(dadosNovos.getEmail());

        Endereco enderecoNovo      = dadosNovos.getEndereco();
        Endereco enderecoGerenciado = contribuinteExistente.getEndereco();

        if (enderecoGerenciado == null) {
            // Caso raro: Contribuinte antigo sem endereço cadastrado
            contribuinteExistente.setEndereco(enderecoNovo);
        } else {
            // Caminho normal: atualiza campo a campo no objeto já gerenciado
            enderecoGerenciado.setLogradouro(enderecoNovo.getLogradouro());
            enderecoGerenciado.setNumero(enderecoNovo.getNumero());
            enderecoGerenciado.setBairro(enderecoNovo.getBairro());
            enderecoGerenciado.setCidade(enderecoNovo.getCidade());
            enderecoGerenciado.setEstado(enderecoNovo.getEstado());
            enderecoGerenciado.setCep(enderecoNovo.getCep());
        }

        // Valida o estado final da entidade mesclada antes de persistir
        validarContribuinte(contribuinteExistente);

        return contribuinteRepository.save(contribuinteExistente);
    }

    // -------------------------------------------------------------------------
    // BUSCAR POR ID
    // -------------------------------------------------------------------------
    @Override
    public Contribuinte buscarPorId(Long id) {
        log.info("Buscando Contribuinte de ID: {}.", id);

        return contribuinteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Contribuinte não encontrado no banco de dados."));
    }

    // -------------------------------------------------------------------------
    // DELETAR (soft-delete)
    // -------------------------------------------------------------------------
    @Override
    public void deletar(Long id) {
        log.info("Iniciando a INATIVAÇÃO do Contribuinte ID: {}", id);

        if (id == null || id <= 0) {
            log.error("Tentativa de deletar com ID nulo ou inválido.");
            throw new IllegalArgumentException("O ID do contribuinte é inválido.");
        }

        Contribuinte contribuinte = buscarPorId(id);
        contribuinte.setAtivo(false);
        contribuinteRepository.save(contribuinte);

        log.info("Contribuinte ID {} inativado com sucesso.", id);
    }

    // -------------------------------------------------------------------------
    // LISTAR TODOS (apenas ativos)
    // -------------------------------------------------------------------------
    @Override
    public List<Contribuinte> listarTodos() {
        log.info("Listando todos os Contribuintes ativos.");
        return contribuinteRepository.findByAtivoTrue();
    }

    // =========================================================================
    // VALIDAÇÕES
    // =========================================================================
    public void validarContribuinte(Contribuinte contribuinte) {
        log.info("Executando a validação de negócio para Contribuinte.");

        if (contribuinte == null) {
            log.error("Falha: objeto Contribuinte nulo.");
            throw new IllegalArgumentException("Os dados do contribuinte não foram preenchidos.");
        }

        // Valida o Nome
        if (StringUtil.isNullOrEmpty(contribuinte.getNome())) {
            throw new IllegalArgumentException("O nome do contribuinte é obrigatório e não pode estar vazio.");
        }
        contribuinte.setNome(contribuinte.getNome().trim());
        if (contribuinte.getNome().length() < 3 || contribuinte.getNome().length() > 150) {
            throw new IllegalArgumentException("O nome deve ter entre 3 e 150 caracteres.");
        }
        if (!contribuinte.getNome().matches("^[\\p{L}0-9 '.,-]+$")) {
            throw new IllegalArgumentException("O nome deve conter apenas letras, números e os caracteres: ' . , -");
        }

        // Puxa o método de validar CPF e CNPJ
        validarCpfCnpj(contribuinte);

        // Valida o telefone
        if (StringUtil.isNullOrEmpty(contribuinte.getTelefone())) {
            throw new IllegalArgumentException("O telefone do contribuinte é obrigatório.");
        }
        String telDigitos = contribuinte.getTelefone().replaceAll("\\D", "");
        if (telDigitos.length() < 10 || telDigitos.length() > 11) {
            throw new IllegalArgumentException(
                    "O telefone deve ter 10 dígitos (fixo) ou 11 dígitos (celular), incluindo o DDD.");
        }

        // Valida o Email
        if (StringUtil.isNullOrEmpty(contribuinte.getEmail())) {
            throw new IllegalArgumentException("O e-mail do contribuinte é obrigatório.");
        }
        contribuinte.setEmail(contribuinte.getEmail().trim().toLowerCase());
        if (!contribuinte.getEmail().matches("^[\\w._%+\\-]+@[\\w.\\-]+\\.[a-z]{2,}$")) {
            throw new IllegalArgumentException("Informe um endereço de e-mail válido (ex: nome@dominio.com).");
        }

        // Valida o Endereço (com outro método também)
        validarEndereco(contribuinte.getEndereco());

        log.info("Validação de Contribuinte concluída com sucesso.");
    }

    private void validarCpfCnpj(Contribuinte contribuinte) {
        if (StringUtil.isNullOrEmpty(contribuinte.getCpfCnpj())) {
            throw new IllegalArgumentException("O CPF ou CNPJ do contribuinte é obrigatório.");
        }
        String apenasDigitos = contribuinte.getCpfCnpj().replaceAll("\\D", "");
        if (apenasDigitos.length() != 11 && apenasDigitos.length() != 14) {
            log.warn("Falha: CPF/CNPJ com {} dígitos.", apenasDigitos.length());
            throw new IllegalArgumentException("CPF/CNPJ inválido: informe 11 dígitos para CPF ou 14 para CNPJ.");
        }
        contribuinte.setCpfCnpj(apenasDigitos);
    }

    private void validarEndereco(Endereco endereco) {
        if (endereco == null) {
            throw new IllegalArgumentException("Os dados de endereço são obrigatórios.");
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
        if (StringUtil.isNullOrEmpty(endereco.getEstado()) || endereco.getEstado().trim().length() != 2) {
            throw new IllegalArgumentException("O estado deve ser informado com a sigla de 2 letras (ex: BA).");
        }
        String cepDigitos = endereco.getCep() == null ? "" : endereco.getCep().replaceAll("\\D", "");
        if (cepDigitos.length() != 8) {
            throw new IllegalArgumentException("O CEP deve ter exatamente 8 dígitos.");
        }

        // Normaliza
        endereco.setLogradouro(endereco.getLogradouro().trim());
        endereco.setBairro(endereco.getBairro().trim());
        endereco.setCidade(endereco.getCidade().trim());
        endereco.setEstado(endereco.getEstado().trim().toUpperCase());
        endereco.setCep(cepDigitos);
    }
}
