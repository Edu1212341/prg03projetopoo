package br.com.ifba.contribuinte.service;

import br.com.ifba.contribuinte.entity.Contribuinte;
import java.util.List;

/**
 *
 * @author eduar
 */
public interface ContribuinteIService {

    Contribuinte save(Contribuinte contribuinte);
    Contribuinte update(Long id, Contribuinte contribuinte);
    Contribuinte findById(Long id);
    List<Contribuinte> findByAtivoTrue();
    void delete(Long id);
}
