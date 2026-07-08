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
        
        // O método calcularValorVenal() já foi definido na classe Imovel!
        Double valorVenalTotal = imovel.calcularValorVenal();
        
        Double valorImpostoFinal;

        // REGRA DE NEGÓCIO DA PREFEITURA
        // Verifica se o imóvel possui alguma construção
        if (imovel.getAreaConstruida() > 0.0) {
            
            // É um Imóvel Edificado (Casa, Loja, etc.)
            // Divide por 100 para transformar o número (ex: 1.0%) em decimal (0.01)
            Double taxaEdificado = this.aliquotaConstrucao / 100.0;
            // Aplica a taxa menor sobre o valor total do imóvel
            valorImpostoFinal = valorVenalTotal * taxaEdificado;
            
        } else {
            
            // A área construída é 0. Logo, é um Terreno Baldio 
            // Divide por 100 para transformar o número (ex: 3.0%) em decimal (0.03)
            Double taxaTerreno = this.aliquotaTerreno / 100.0;
            
            // Aplica a taxa maior (punição por lote vazio) sobre o valor total do terreno
            valorImpostoFinal = valorVenalTotal * taxaTerreno;
        }
        // Retorna o valor em reais que vai sair impresso no boleto do contribuinte
        return valorImpostoFinal;
    }
}