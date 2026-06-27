/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.infrastructure.util;

/**
 *
 * @author eduar
 */

public class ContribuinteItem {
    private Long id;
    private String nome;

    //classe feita somente para que o combobox exiba o nome do contribuinte e guarde o ID também. vai ficar massa 
    public ContribuinteItem(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    // O JComboBox usa o método toString() para saber o que mostrar na tela!
    @Override
    public String toString() {
        return nome; 
    }
}