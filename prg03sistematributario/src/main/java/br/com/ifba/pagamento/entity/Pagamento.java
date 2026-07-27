package br.com.ifba.pagamento.entity;
import br.com.ifba.boleto.entity.BoletosPrefeitura;
import br.com.ifba.infrastructure.entity.PersistenceEntity;
import br.com.ifba.usuario.entity.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author eduar
 */
@Entity
@Table(name = "pagamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento extends PersistenceEntity {

    @Column(nullable = false)
    private LocalDate dataPagamento;

    @Column(nullable = false)
    private Double valorPago;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Boolean ativo = true;

    // Rel. 7 — Um pagamento quita exatamente um boleto
    @OneToOne
    @JoinColumn(name = "boleto_id", nullable = false)
    private BoletosPrefeitura boletoPrefeitura;

    // Rel. 8 — lado filho: usuário operador que registrou o pagamento
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // =========================================================================
    // MÉTODO DE NEGÓCIO (conforme diagrama de classes)
    // =========================================================================

    /**
     * Processa a baixa manual de um boleto:
     * preenche os dados do pagamento e atualiza o status do boleto para PAGO.
     *
     * @param boleto  boleto que será quitado
     * @param usuario operador responsável pelo registro
     * @return true se a baixa foi processada com sucesso
     */
    public Boolean processarBaixaManual(BoletosPrefeitura boleto, Usuario usuario) {
        this.boletoPrefeitura = boleto;
        this.usuario          = usuario;
        this.dataPagamento    = LocalDate.now();
        this.valorPago        = boleto.getValorBoleto();
        this.status           = "APROVADO";
        this.ativo            = true;

        boleto.atualizarStatusParaPago();
        return true;
    }
}
