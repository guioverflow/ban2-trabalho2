package com.atacadista.util;

import java.util.Scanner;

public class Validation {

    // Remove não numéricos e verifica tamanho
    public boolean validaCnpj(String cnpj) {
        cnpj = cnpj.replaceAll("[^0-9]", "");
        return cnpj.length() == 14;
    }

    // Remove não numéricos e verifica tamanho
    public boolean validaCpf(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        return cpf.length() == 11;
    }

    public static boolean validaEmail(String email) {
        if (!email.contains(".")) {
            return false; // Não tem ponto
        }

        if (email.startsWith(".") || email.endsWith(".")) {
            return false; // Ponto no inicio ou final da string
        }

        if (email.indexOf("@") != email.lastIndexOf("@")) {
            return false; // Dois ou mais @
        }

        String[] parts = email.split("@");
        if (parts[0].endsWith(".") || parts[1].startsWith(".")) {
            return false; // Ponto concatenado com o @
        }

        return true;
    }

    public static boolean validaNome(String nome) {
        // Inputs invalidos: '-1' , '+1' , ' -1' , ' +1'
        nome = nome.replace(" ", ""); // Remove espaços
        nome = nome.replace("-", ""); // Remove negativos
        nome = nome.replace("+", ""); // Remove positivos
        if (nome.matches("\\d+")) {
            return false;
        } return true;
    }
}
