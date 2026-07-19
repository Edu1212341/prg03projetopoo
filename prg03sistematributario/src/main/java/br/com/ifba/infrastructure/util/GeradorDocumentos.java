package br.com.ifba.infrastructure.util;

/**
 * Contrato para entidades que geram documentos imprimíveis no sistema.
 * Implementada por BoletosPrefeitura conforme diagrama de classes (Rel. 3).
 *
 * @author Sistema de Tributos
 */
public interface GeradorDocumentos {
    void imprimir();
}
