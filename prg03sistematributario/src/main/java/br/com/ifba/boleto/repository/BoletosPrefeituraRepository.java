package br.com.ifba.boleto.repository;

import br.com.ifba.boleto.entity.BoletosPrefeitura;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório JPA para BoletosPrefeitura.
 * Spring Data gera as implementações automaticamente a partir dos nomes dos métodos.
 *
 * @author Sistema de Tributos
 */
@Repository
public interface BoletosPrefeituraRepository extends JpaRepository<BoletosPrefeitura, Long> {

    /** Retorna todos os boletos não inativados (soft-delete). */
    List<BoletosPrefeitura> findByAtivoTrue();

    /** Retorna todos os boletos gerados por um lançamento específico. */
    List<BoletosPrefeitura> findByLancamentoImpostoId(Long lancamentoId);

    /**
     * Retorna boletos filtrando por status e ativos.
     * Exemplo de uso: findByStatusAndAtivoTrue("PENDENTE")
     */
    List<BoletosPrefeitura> findByStatusAndAtivoTrue(String status);

    /**
     * Busca boleto pelo código de barras.
     * Usado para garantir unicidade antes de salvar.
     */
    Optional<BoletosPrefeitura> findByNumeroCodigoBarras(String codigoBarras);
}
