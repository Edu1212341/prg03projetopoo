/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.usuario.controller;

import br.com.ifba.usuario.entity.Usuario;
import java.util.List;

/**
 *
 * @author eduardo
 */
public interface UsuarioIController {
    Usuario salvar(Usuario usuario);
    Usuario atualizar(Long id, Usuario usuario);
    Usuario buscarPorId(Long id);
    List<Usuario> listarTodos();
    void deletar(Long id);
    Usuario autenticar(String login, String senha);
}
