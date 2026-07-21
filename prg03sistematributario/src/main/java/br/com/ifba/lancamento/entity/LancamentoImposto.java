package br.com.ifba.lancamento.entity;
import br.com.ifba.boleto.entity.BoletosPrefeitura;
import br.com.ifba.imposto.entity.Imposto;
import br.com.ifba.infrastructure.entity.PersistenceEntity;
import br.com.ifba.imovel.entity.Imovel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Sistema de Tributos
 */
@Entity
@Table(name = "lancamento_imposto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoImposto extends PersistenceEntity {

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false)
    private Double valorTotalCalculado;

    @Column(nullable = false)
    private LocalDate dataLancamento;

    @Column(nullable = false)
    private Boolean ativo = true;

    /** Rel. 5 — Qual imóvel está sendo tributado neste lançamento. */
    @ManyToOne
    @JoinColumn(name = "imovel_id", nullable = false)
    private Imovel imovel;

    /** Rel. 4 — Qual tipo de imposto foi aplicado (ex: IPTU). */
    @ManyToOne
    @JoinColumn(name = "imposto_id", nullable = false)
    private Imposto imposto;


    @OneToMany(mappedBy = "lancamentoImposto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoletosPrefeitura> boletos = new ArrayList<>();

    public void processarLancamento(Imovel imovel, Imposto imposto) {
        this.imovel              = imovel;
        this.imposto             = imposto;
        this.valorTotalCalculado = imposto.calcularImposto(imovel);
        this.dataLancamento      = LocalDate.now();
        this.ano                 = LocalDate.now().getYear();
    }

    // quantidadeParcelas número de parcelas a gerar (1–12)
    public void gerarBoletosPrefeitura(Integer quantidadeParcelas) {
        this.boletos.clear();
        double valorParcela =
                Math.round((this.valorTotalCalculado / quantidadeParcelas) * 100.0) / 100.0;

        for (int i = 1; i <= quantidadeParcelas; i++) {
            BoletosPrefeitura boleto = new BoletosPrefeitura();
            
            // CORREÇÃO: Gerando um código de barras numérico
            StringBuilder codigoBarras = new StringBuilder();
            for (int j = 0; j < 44; j++) {
                codigoBarras.append((int) (Math.random() * 10)); // Sorteia um número de 0 a 9
            }
            boleto.setNumeroCodigoBarras(codigoBarras.toString());
            
            boleto.setValorBoleto(valorParcela);
            boleto.setDataVencimento(LocalDate.now().plusMonths(i));
            boleto.setStatus("PENDENTE");
            boleto.setAtivo(true);
            boleto.setLancamentoImposto(this);
            this.boletos.add(boleto);
        }
    }
}
