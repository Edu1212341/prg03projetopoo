/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.imposto.entity;

import br.com.ifba.imovel.entity.Imovel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "imposto_iptu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImpostoIPTU extends Imposto {

    @Column(nullable = false)
    private Double aliquotaTerreno;

    @Column(nullable = false)
    private Double aliquotaConstrucao;

    @Override
    public Double calcularImposto(Imovel imovel) {
        // Exemplo de cálculo básico baseado nas alíquotas cadastradas para este imposto
        // e nas áreas vindas do imóvel associado.
        double valorTerreno = imovel.getAreaTerreno() * this.aliquotaTerreno;
        double valorConstrucao = imovel.getAreaConstruida() * this.aliquotaConstrucao;
        
        return valorTerreno + valorConstrucao;
    }
}