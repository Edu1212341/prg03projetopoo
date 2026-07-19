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
    public Usuario save(Usuario usuario) {
        return usuarioService.save(usuario);
    }

    @Override
    public Usuario update(Long id, Usuario usuario) {
        return usuarioService.update(id, usuario);
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioService.findById(id);
    }

    @Override
    public List<Usuario> findByAtivoTrue() {
        return usuarioService.findByAtivoTrue();
    }

    @Override
    public void delete(Long id) {
        usuarioService.delete(id);
    }
    
    public Usuario authenticate(String login, String senha) {
        return usuarioService.authenticate(login, senha);
    }
}
