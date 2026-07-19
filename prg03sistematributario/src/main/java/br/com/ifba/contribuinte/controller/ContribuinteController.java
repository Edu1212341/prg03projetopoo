package br.com.ifba.contribuinte.controller;

import br.com.ifba.contribuinte.entity.Contribuinte;
import br.com.ifba.contribuinte.service.ContribuinteIService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

/**
 *
 * @author eduar
 */
@Controller
@RequiredArgsConstructor
public class ContribuinteController implements ContribuinteIController {

    private final ContribuinteIService contribuinteService;

    @Override
    public Contribuinte save(Contribuinte contribuinte) {
        return contribuinteService.save(contribuinte);
    }

    @Override
    public Contribuinte update(Long id, Contribuinte contribuinte) {
        return contribuinteService.update(id, contribuinte);
    }

    @Override
    public Contribuinte findById(Long id) {
        return contribuinteService.findById(id);
    }

    @Override
    public List<Contribuinte> findByAtivoTrue() {
        return contribuinteService.findByAtivoTrue();
    }

    @Override
    public void delete(Long id) {
        contribuinteService.delete(id);
    }
}
