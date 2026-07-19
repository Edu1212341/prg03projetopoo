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
    public Imovel save(Imovel imovel) {
        return imovelService.save(imovel);
    }

    @Override
    public Imovel update(Long id, Imovel imovel) {
        return imovelService.update(id, imovel);
    }

    @Override
    public Imovel findById(Long id) {
        return imovelService.findById(id);
    }

    @Override
    public List<Imovel> findByAtivoTrue() {
        return imovelService.findByAtivoTrue();
    }

    @Override
    public List<Imovel> findByContribuinteIdAndAtivoTrue(Long contribuinteId) {
        return imovelService.findByContribuinteIdAndAtivoTrue(contribuinteId);
    }

    @Override
    public void delete(Long id) {
        imovelService.deletar(id);
    }
}
