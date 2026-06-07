/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.controller;

import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.service.UsuarioIService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

/**
 *
 * @author eduardo
 */

@Controller
@RequiredArgsConstructor
public class UsuarioController implements UsuarioIController{

    private final UsuarioIService usuarioService;

    @Override
    public Usuario salvar(Usuario usuario) {
        return usuarioService.salvar(usuario);
    }

    @Override
    public Usuario atualizar(Long id, Usuario usuario) {
        return usuarioService.atualizar(id, usuario);
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return usuarioService.buscarPorId(id);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    @Override
    public void deletar(Long id) {
        usuarioService.deletar(id);
    }
    
    public Usuario autenticar(String login, String senha) {
        return usuarioService.autenticar(login, senha);
    }
}
