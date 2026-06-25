package br.com.ifba.contribuinte.entity;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidade de endereço com tabela própria no banco de dados.
 *
 * Por ser uma @Entity independente:
 *  - Possui sua própria tabela "endereco" com coluna "id"
 *  - Pode ser reutilizada por Contribuinte, Imovel e qualquer outra entidade futura
 *  - Permite buscas diretas por cidade, bairro, CEP etc. sem JOIN obrigatório
 *
 * O relacionamento com Contribuinte é @OneToOne declarado no lado do Contribuinte.
 */
@Entity
@Table(name = "endereco")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco extends PersistenceEntity {

    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String complemento;

}
