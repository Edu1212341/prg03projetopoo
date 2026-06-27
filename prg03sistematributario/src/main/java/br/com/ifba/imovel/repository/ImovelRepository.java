package br.com.ifba.imovel.repository;

import br.com.ifba.imovel.entity.Imovel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author eduar
 */
@Repository
public interface ImovelRepository extends JpaRepository<Imovel, Long> {

    // Busca imóvel pela inscrição imobiliária 
    Optional<Imovel> findByInscricaoImobiliaria(String inscricaoImobiliaria);

    // Lista apenas imóveis ativos
    List<Imovel> findByAtivoTrue();

    // Lista todos os imóveis ativos de um contribuinte específico
    List<Imovel> findByContribuinteIdAndAtivoTrue(Long contribuinteId);
}
