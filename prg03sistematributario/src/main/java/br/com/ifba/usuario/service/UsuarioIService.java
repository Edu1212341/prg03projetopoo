/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.usuario.service;

import br.com.ifba.usuario.entity.Usuario;
import java.util.List;

/**
 *
 * @author eduardo
 */
public interface UsuarioIService {
    Usuario salvar(Usuario usuario);  
    Usuario atualizar(Usuario usuario);
    Usuario buscarPorId(Long id);
    List<Usuario> listarTodos();
    void deletar(Long id);
    public Usuario autenticar(String login, String senha);
}
