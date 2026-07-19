package br.com.ifba.pagamento.repository;

import br.com.ifba.pagamento.entity.Pagamento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório JPA para Pagamento.
 * Spring Data gera as implementações automaticamente a partir dos nomes dos métodos.
 *
 * @author Sistema de Tributos
 */
@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    /** Retorna apenas os pagamentos não estornados (soft-delete). */
    List<Pagamento> findByAtivoTrue();

    /** Retorna os pagamentos ativos registrados por um usuário específico. */
    List<Pagamento> findByUsuarioIdAndAtivoTrue(Long usuarioId);

    /** Retorna pagamentos filtrados por status ("APROVADO" ou "ESTORNADO"). */
    List<Pagamento> findByStatusAndAtivoTrue(String status);

    /**
     * Verifica se já existe um pagamento vinculado a um boleto.
     * Usado em PagamentoService.salvar() para impedir pagamento duplicado.
     */
    boolean existsByBoletoPrefeituraId(Long boletoId);
}
