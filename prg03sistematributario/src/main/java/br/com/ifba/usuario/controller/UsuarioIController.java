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
    Usuario save(Usuario usuario);
    Usuario update(Long id, Usuario usuario);
    Usuario findById(Long id);
    List<Usuario> findByAtivoTrue();
    void delete(Long id);
    Usuario authenticate(String login, String senha);
}
