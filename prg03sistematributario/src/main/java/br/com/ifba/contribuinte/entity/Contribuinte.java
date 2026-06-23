/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.contribuinte.entity;

import br.com.ifba.contribuinte.entity.Endereco;
import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author eduar
 */
@Entity
@Table(name = "contribuinte")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contribuinte extends PersistenceEntity {

    @Column (nullable = false)
    private String nome;
    @Column (nullable = false)
    private String cpfCnpj;
    @Column (nullable = false)
    private String telefone;
    @Column (nullable = false)
    private String email;
    @Column (name = "Ativo", nullable = false)
    private Boolean ativo = true;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;
}
