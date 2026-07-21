package br.com.ifba.imovel.entity;

import br.com.ifba.contribuinte.entity.Contribuinte;
import br.com.ifba.contribuinte.entity.Endereco;
import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author eduardo
 */
@Entity
@Table(name = "imovel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Imovel extends PersistenceEntity {

    @Column(nullable = false, unique = true)
    private String inscricaoImobiliaria;

    @Column(nullable = false)
    private Double areaTerreno;

    @Column(nullable = false)
    private Double areaConstruida;

    @Column(name = "valor_venal", nullable = false)
    private Double valorVenal;

    @Column(nullable = false)
    private Boolean ativo = true;

    // Cada imóvel possui seu próprio endereço
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    // Vários imóveis pertencem a um único Contribuinte
    // A coluna FK "contribuinte_id" fica na tabela imovel
    @ManyToOne
    @JoinColumn(name = "contribuinte_id", nullable = false)
    private Contribuinte contribuinte;

    public Double calcularValorVenal() {
        //calculo de valor venal
        double valorM2Terreno    = 500.0;  // R$ por m² de terreno
        double valorM2Construcao = 800.0;  // R$ por m² construído
        return (this.areaTerreno * valorM2Terreno) + (this.areaConstruida * valorM2Construcao);
    }
}
