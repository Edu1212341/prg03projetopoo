package br.com.ifba.contribuinte.repository;

import br.com.ifba.contribuinte.entity.Contribuinte;
import br.com.ifba.contribuinte.entity.Contribuinte;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório JPA para a entidade Contribuinte.
 */
@Repository
public interface ContribuinteRepository extends JpaRepository<Contribuinte, Long> {

    Optional<Contribuinte> findByCpfCnpj(String cpfCnpj);

    List<Contribuinte> findByAtivoTrue();
}
