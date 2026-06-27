package br.com.ifba.imovel.controller;

import br.com.ifba.imovel.entity.Imovel;
import br.com.ifba.imovel.service.ImovelIService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

/**
 * Controller da entidade Imovel.
 * Delega 100% das responsabilidades para a camada de Service,
 * seguindo o mesmo padrão de UsuarioController e ContribuinteController.
 */
@Controller
@RequiredArgsConstructor
public class ImovelController implements ImovelIController {

    private final ImovelIService imovelService;

    @Override
    public Imovel salvar(Imovel imovel) {
        return imovelService.salvar(imovel);
    }

    @Override
    public Imovel atualizar(Long id, Imovel imovel) {
        return imovelService.atualizar(id, imovel);
    }

    @Override
    public Imovel buscarPorId(Long id) {
        return imovelService.buscarPorId(id);
    }

    @Override
    public List<Imovel> listarTodos() {
        return imovelService.listarTodos();
    }

    @Override
    public List<Imovel> listarPorContribuinte(Long contribuinteId) {
        return imovelService.listarPorContribuinte(contribuinteId);
    }

    @Override
    public void deletar(Long id) {
        imovelService.deletar(id);
    }
}
