/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.imposto.service;

import br.com.ifba.imposto.entity.Imposto;
import br.com.ifba.imposto.entity.ImpostoIPTU;
import br.com.ifba.imposto.repository.ImpostoRepository;
import br.com.ifba.infrastructure.util.StringUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author eduar
 */
@Service
@RequiredArgsConstructor
public class ImpostoService implements ImpostoIService {

    private final ImpostoRepository impostoRepository;
    private static final Logger log = LoggerFactory.getLogger(ImpostoService.class);

    @Override
    public Imposto save(Imposto imposto) {
        log.info("Salvando novo imposto.");
        validarImposto(imposto);
        return impostoRepository.save(imposto);
    }

    @Override
    public Imposto update(Long id, Imposto dadosNovos) {
        log.info("Atualizando imposto ID: {}", id);
        Imposto impostoExistente = findById(id);

        impostoExistente.setDescricao(dadosNovos.getDescricao());
        impostoExistente.setAnoExercicio(dadosNovos.getAnoExercicio());

        // Se for especificamente um IPTU, atualiza os campos extras
        if (impostoExistente instanceof ImpostoIPTU && dadosNovos instanceof ImpostoIPTU) {
            ImpostoIPTU iptuExistente = (ImpostoIPTU) impostoExistente;
            ImpostoIPTU iptuNovo = (ImpostoIPTU) dadosNovos;
            
            iptuExistente.setAliquotaTerreno(iptuNovo.getAliquotaTerreno());
            iptuExistente.setAliquotaConstrucao(iptuNovo.getAliquotaConstrucao());
        }

        validarImposto(impostoExistente);
        return impostoRepository.save(impostoExistente);
    }

    @Override
    public Imposto findById(Long id) {
        return impostoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Imposto não encontrado."));
    }

    @Override
    public List<Imposto> findByAtivoTrue() {
        return impostoRepository.findByAtivoTrue();
    }

    @Override
    public void delete(Long id) {
        log.info("Inativando imposto ID: {}", id);
        Imposto imposto = findById(id);
        imposto.setAtivo(false);
        impostoRepository.save(imposto);
    }

    // Validação centralizada
    private void validarImposto(Imposto imposto) {
        if (imposto == null) {
            throw new IllegalArgumentException("Dados do imposto não fornecidos.");
        }
        if (StringUtil.isNullOrEmpty(imposto.getDescricao())) {
            throw new IllegalArgumentException("A descrição do imposto é obrigatória.");
        }
        if (imposto.getAnoExercicio() == null || imposto.getAnoExercicio() < 2000) {
            throw new IllegalArgumentException("Informe um ano de exercício válido.");
        }
        
        // Validação específica caso seja um IPTU
        if (imposto instanceof ImpostoIPTU ) {
            ImpostoIPTU iptu = (ImpostoIPTU) imposto;
            if (iptu.getAliquotaTerreno() == null || iptu.getAliquotaTerreno() < 0) {
                throw new IllegalArgumentException("A alíquota do terreno é inválida.");
            }
            if (iptu.getAliquotaConstrucao() == null || iptu.getAliquotaConstrucao() < 0) {
                throw new IllegalArgumentException("A alíquota da construção é inválida.");
            }
        }
    }
}
