/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.service;

import br.com.ifba.infrastructure.util.StringUtil;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author eduardo
 */
@Service
@RequiredArgsConstructor
public class UsuarioService implements UsuarioIService {
    
    private final UsuarioRepository usuarioRepository;
    
    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);
    @Override
    public Usuario salvar(Usuario usuario) {
        log.info("Iniciando o processo de salvar ");
        validarUsuario(usuario);
        Optional<Usuario> usuarioComMesmoLogin = usuarioRepository.findByLogin(usuario.getLogin());
        if (usuarioComMesmoLogin.isPresent()) {
            throw new IllegalArgumentException("Este login já existe no sistema.");
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario atualizar(Long id, Usuario dadosNovos) {
        log.info("Iniciando processo de atualização de cadastro para o ID: {}", id);
        Usuario usuarioExistente = buscarPorId(id);

        //para proteger o ADMIN
        if (usuarioExistente.getLogin().equalsIgnoreCase("admin")) {
            // Impede a alteração do login "admin"
            if (!dadosNovos.getLogin().equalsIgnoreCase("admin")) {
                throw new IllegalArgumentException("Operação negada: O login do Administrador padrão não pode ser alterado.");
            }
            // Impede o rebaixamento de cargo
            if (!dadosNovos.getNivelAcesso().equalsIgnoreCase("ADMIN")) {
                throw new IllegalArgumentException("Operação negada: O Administrador padrão não pode ser rebaixado de cargo.");
            }
        }
        
        Optional<Usuario> usuarioComMesmoLogin = usuarioRepository.findByLogin(dadosNovos.getLogin());
        if (usuarioComMesmoLogin.isPresent()) {
            boolean loginPertenceAOutroUsuario = !usuarioComMesmoLogin.get().getId().equals(id);
            if (loginPertenceAOutroUsuario) {
                throw new IllegalArgumentException("Este login já está sendo utilizado por outro usuário.");
            }
        }

        // 3. Transfere os dados da tela para a entidade gerenciada
        usuarioExistente.setNome(dadosNovos.getNome());
        usuarioExistente.setLogin(dadosNovos.getLogin());
        usuarioExistente.setNivelAcesso(dadosNovos.getNivelAcesso());
    
        // Atualiza a senha apenas se foi digitada uma nova
        if (dadosNovos.getSenha() != null && !dadosNovos.getSenha().trim().isEmpty()) {
            usuarioExistente.setSenha(dadosNovos.getSenha());
        }

        // 4. Valida a entidade já mesclada antes de salvar
        validarUsuario(usuarioExistente);

        return usuarioRepository.save(usuarioExistente);
    }

    @Override
    public Usuario buscarPorId(Long id) {
        log.info("Buscando usuário de ID: {}.", id);

        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(id);

        if (usuarioEncontrado.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado no banco de dados.");
        }

        return usuarioEncontrado.get();
    }

    @Override
    public void deletar(Long id) {
       log.info("Iniciando a exclusão do usuário ID: {}", id);

        // Verifica se tá no banco
        if (id == null || id <= 0) {
            log.error("Tentativa de deletar com ID nulo ou inválido.");
            throw new IllegalArgumentException("O ID do usuário é inválido.");
        }
        // Se não existir, esta linha já lança o erro e para tudo!
        Usuario usuario = buscarPorId(id);

        // É impossivel deletar uma conta de admin, quem fará isso será quem têm acesso direto ao banco.
        if (usuario.getLogin().equalsIgnoreCase("admin") || usuario.getNivelAcesso().equalsIgnoreCase("ADMIN")) {
            log.warn("Alerta de Segurança: Tentativa de deletar a conta Administradora bloqueada.");
            throw new IllegalArgumentException("Operação negada: Não é permitido excluir um perfil de Administrador.");
        }
        usuarioRepository.delete(usuario);
        log.info("Usuário ID {} deletado com sucesso do banco de dados.", id);
    }

    @Override
    public List<Usuario> listarTodos() {
        log.info("Listando todos os usuários.");

        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios;
    }
    
    // Método para ser usado pela tela de login
    @Override
    public Usuario autenticar(String login, String senha) {
        
        // Procura o usuário
        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Erro: Usuário não encontrado."));
        
        // Olha se a senha digitada é igual à senha do banco
        if (!usuario.getSenha().equals(senha)) {
            throw new IllegalArgumentException("Erro: Senha incorreta.");
        }
        
        // Se passou pelas verificações, devolve o usuário com tudo que ele tinha no banco, cargo, nome.
        return usuario;
    }
    
    public void validarUsuario(Usuario usuario) {
        
        log.info("Executando a validação de segurança para usuário");

        if (usuario == null) {//se usuario nulo
            log.error("Falha: Tentativa de validar um objeto Usuário nulo.");
            throw new IllegalArgumentException("Os dados do usuário não foram preenchidos.");
        }

        //  Validação do nome
       
        if (StringUtil.isNullOrEmpty(usuario.getNome())) {//se o nome for null ou estiver vazio
            log.warn("Falha: Nome do funcionário está em branco.");
            throw new IllegalArgumentException("O nome do funcionário é obrigatório e não pode estar vazio.");
        }
        usuario.setNome(usuario.getNome().trim());

        if (usuario.getNome().length() < 3 || usuario.getNome().length() > 100) {//se o nome tiver mais de 100 caracteres ou for menor que 3
            log.warn("Falha: Tamanho do nome inválido");
            throw new IllegalArgumentException("O nome deve ter entre 3 e 100 caracteres.");
        }

        if (!usuario.getNome().matches("^[\\p{L} '-]+$")) {//Aceita letras (acentuadas também) e determinados caracteres especiais
            log.warn("Falha: Nome contém caracteres inválidos.");
            throw new IllegalArgumentException("O nome deve conter apenas letras e os seguintes caracteres: .'-]");
        }

        // Validação do login
        if (StringUtil.isNullOrEmpty(usuario.getLogin())) {
            log.warn("Falha: Login de acesso está em branco");
            throw new IllegalArgumentException("O login de acesso é obrigatório.");
        }
        usuario.setLogin(usuario.getLogin().trim().toLowerCase());

        if (!usuario.getLogin().matches("^[a-z0-9._-]{4,30}$")) {
            log.warn("Validação falhou: Login com formato inválido");
            throw new IllegalArgumentException(
                "O login deve ter entre 4 e 30 caracteres e conter apenas letras minúsculas, números, e as seguintes pontuações . - _"
            );
        }


        // Validação do nível de acesso

        if (StringUtil.isNullOrEmpty(usuario.getNivelAcesso())) {
            log.warn("Validação falhou: Nível de acesso não definido.");
            throw new IllegalArgumentException("O nível de acesso (cargo/perfil) é obrigatório.");
        }

        String nivel = usuario.getNivelAcesso().trim().toUpperCase();
        usuario.setNivelAcesso(nivel);

        if (!nivel.equals("ADMIN") && !nivel.equals("FISCAL") && !nivel.equals("ATENDENTE")) {
            log.error("Alerta de Segurança: Tentativa de injetar perfil inválido: '{}'.", nivel);
            throw new IllegalArgumentException("Nível de acesso inválido! Escolha entre: ADMIN, FISCAL ou ATENDENTE.");
        }


        // Validação de senha

        if (StringUtil.isNullOrEmpty(usuario.getSenha())) {
            log.warn("Validação falhou: Senha ausente.");
            throw new IllegalArgumentException("A senha de acesso é obrigatória.");
        }

        if (usuario.getSenha().length() < 6) {
            log.warn("Validação falhou: Senha muito curta ({} caracteres).", usuario.getSenha().length());
            throw new IllegalArgumentException("A senha deve ter no mínimo 6 caracteres.");
        }

        if (!usuario.getSenha().matches("^(?=.*[A-Za-z])(?=.*\\d).{6,}$")) {
            log.warn("Validação falhou: Senha não atende aos critérios mínimos de complexidade.");
            throw new IllegalArgumentException("A senha deve conter pelo menos uma letra e um número.");
        }

        
        log.info("Validação concluída.");
    }
}