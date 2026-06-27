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
 * Entidade que representa um Imóvel no sistema de tributação municipal.
 *
 * Relacionamentos:
 *  - @ManyToOne  com Contribuinte: vários imóveis pertencem a um contribuinte
 *  - @OneToOne   com Endereco: cada imóvel tem seu próprio endereço
 *
 * Herda id, @Id e @GeneratedValue de PersistenceEntity.
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

    @Column(nullable = false)
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

    // -------------------------------------------------------------------------
    // MÉTODO DE NEGÓCIO — conforme diagrama UML
    // -------------------------------------------------------------------------
    /**
     * Calcula o valor venal do imóvel com base nas áreas e nos valores de
     * referência por m² definidos pela planta genérica de valores do município.
     *
     * Fórmula: (areaTerreno × R$/m² terreno) + (areaConstruida × R$/m² construção)
     *
     * Os valores de R$/m² são exemplos — devem ser ajustados conforme a
     * legislação municipal vigente.
     */
    public Double calcularValorVenal() {
        double valorM2Terreno    = 500.0;  // R$ por m² de terreno
        double valorM2Construcao = 800.0;  // R$ por m² construído
        return (this.areaTerreno * valorM2Terreno) + (this.areaConstruida * valorM2Construcao);
    }
}
