package br.com.ifba.contribuinte.controller;

import br.com.ifba.contribuinte.entity.Contribuinte;
import br.com.ifba.contribuinte.service.ContribuinteIService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

/**
 * Controller da entidade Contribuinte.
 * Delega 100% das responsabilidades para a camada de Service,
 * seguindo o mesmo padrão de UsuarioController.
 */
@Controller
@RequiredArgsConstructor
public class ContribuinteController implements ContribuinteIController {

    private final ContribuinteIService contribuinteService;

    @Override
    public Contribuinte salvar(Contribuinte contribuinte) {
        return contribuinteService.salvar(contribuinte);
    }

    @Override
    public Contribuinte atualizar(Long id, Contribuinte contribuinte) {
        return contribuinteService.atualizar(id, contribuinte);
    }

    @Override
    public Contribuinte buscarPorId(Long id) {
        return contribuinteService.buscarPorId(id);
    }

    @Override
    public List<Contribuinte> listarTodos() {
        return contribuinteService.listarTodos();
    }

    @Override
    public void deletar(Long id) {
        contribuinteService.deletar(id);
    }
}
