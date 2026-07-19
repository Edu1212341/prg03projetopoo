package br.com.ifba.boleto.entity;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import br.com.ifba.infrastructure.util.GeradorDocumentos;
import br.com.ifba.lancamento.entity.LancamentoImposto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 
 * @author eduardo
 */
@Entity
@Table(name = "boleto_prefeitura")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoletosPrefeitura extends PersistenceEntity implements GeradorDocumentos {

    /** Código de barras único de 44 caracteres gerado pelo LancamentoImposto. */
    @Column(nullable = false, unique = true, length = 44)
    private String numeroCodigoBarras;

    @Column(nullable = false)
    private Double valorBoleto;

    @Column(nullable = false)
    private LocalDate dataVencimento;

    /**
     * Status do boleto.
     * Valores possíveis: "PENDENTE" | "PAGO" | "VENCIDO"
     */
    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Boolean ativo = true;

    /**
     * Rel. 6 — lado filho: referencia o lançamento que originou este boleto.
     * A FK "lancamento_imposto_id" fica nesta tabela.
     */
    @ManyToOne
    @JoinColumn(name = "lancamento_imposto_id", nullable = false)
    private LancamentoImposto lancamentoImposto;

    // =========================================================================
    // MÉTODOS DE NEGÓCIO (conforme diagrama de classes)
    // =========================================================================

    /**
     * Verifica se o boleto ultrapassou a data de vencimento.
     *
     * @return true se hoje é posterior à data de vencimento
     */
    public Boolean verificarVencimento() {
        return LocalDate.now().isAfter(this.dataVencimento);
    }

    /**
     * Marca o boleto como pago.
     * Chamado por Pagamento.processarBaixaManual() após o pagamento ser registrado.
     */
    public void atualizarStatusParaPago() {
        this.status = "PAGO";
    }

    /**
     * Implementação de GeradorDocumentos (Rel. 3).
     * Imprime os dados essenciais do boleto no console.
     * Em produção, substituir pela geração de PDF da prefeitura.
     */
    @Override
    public void imprimir() {
        System.out.printf(
            "========== BOLETO PREFEITURA ==========%n" +
            "Cód. Barras : %s%n"                        +
            "Valor       : R$ %.2f%n"                   +
            "Vencimento  : %s%n"                        +
            "Status      : %s%n"                        +
            "=======================================%n",
            numeroCodigoBarras, valorBoleto, dataVencimento, status
        );
    }
}
