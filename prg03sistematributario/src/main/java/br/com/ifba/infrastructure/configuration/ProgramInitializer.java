/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.infrastructure.configuration;

import br.com.ifba.usuario.controller.UsuarioIController;
import br.com.ifba.usuario.entity.Usuario;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author eduar
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ProgramInitializer implements CommandLineRunner {//implementar essa classe faz com que esse código seja executado antes de tudo basicamente
    private final UsuarioIController usuarioController;

   
    @Override
    public void run(String... args) throws Exception {//essa anotação vem junto da classe quando implementamos o commandline, só codar aqui dentro
        log.info("Iniciando verificação do sistema...");
        
        try {
            // Tenta listar. Se o Service der erro por estar vazio, ele pula para o 'catch'
            usuarioController.listarTodos();
            log.info("O banco de dados já possui usuários. Nenhuma ação necessária.");
            
        } catch (IllegalArgumentException e) {
            // Se caiu aqui, é porque o Service gritou que está vazio:
            log.info("Banco vazio detectado. Criando Admin...");
            
            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setLogin("admin");
            admin.setSenha("admin123");
            admin.setNivelAcesso("ADMIN");
            
            usuarioController.salvar(admin);
            log.info("Administrador criado com sucesso!");
        }
    }
}
