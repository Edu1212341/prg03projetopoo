package br.com.ifba.lancamento.repository;

import br.com.ifba.lancamento.entity.LancamentoImposto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório JPA para LancamentoImposto.
 * Spring Data gera as implementações automaticamente a partir dos nomes dos métodos.
 *
 * @author Sistema de Tributos
 */
@Repository
public interface LancamentoImpostoRepository extends JpaRepository<LancamentoImposto, Long> {

    /** Retorna apenas os lançamentos não inativados (soft-delete). */
    List<LancamentoImposto> findByAtivoTrue();

    /** Retorna os lançamentos ativos de um imóvel específico. */
    List<LancamentoImposto> findByImovelIdAndAtivoTrue(Long imovelId);

    /** Retorna os lançamentos ativos de um tipo de imposto específico. */
    List<LancamentoImposto> findByImpostoIdAndAtivoTrue(Long impostoId);

    /**
     * Verifica duplicidade: mesmo imóvel + mesmo imposto + mesmo ano.
     * Usado em LancamentoImpostoService.salvar() para evitar lançamentos
     * duplicados no mesmo exercício fiscal.
     */
    boolean existsByImovelIdAndImpostoIdAndAno(Long imovelId, Long impostoId, Integer ano);
}
