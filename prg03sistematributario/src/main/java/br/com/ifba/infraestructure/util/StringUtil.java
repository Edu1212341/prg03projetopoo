/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.infraestructure.util;

/**
 *
 * @author eduardo
 */
public class StringUtil {
    // Serve pra ver se tá nula ou vazia, vai ajudar bastante na service
    public static boolean isNullOrEmpty(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}
