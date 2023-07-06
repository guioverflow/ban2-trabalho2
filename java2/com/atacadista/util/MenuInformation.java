package com.atacadista.util;


import lombok.RequiredArgsConstructor;
import org.fusesource.jansi.AnsiConsole;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Scanner;

// Lógica extraída do trabalho feito em Python
@Component
public class MenuInformation {
    private Scanner scanner = new Scanner(System.in);

    public boolean intValidation(String input) {
        try {
            int number = Integer.parseInt(input);
            return true;
        } catch (NumberFormatException numExcept) {}
        return false;
    }
    public Integer menuInicial() {

        System.out.println("""
                Informe a opção desejada:
                
                1 - Cadastro
                2 - Consultar por ID
                3 - Consultar todos
                4 - Atualização
                5 - Remoção
                6 - Relatórios
                0 - Sair
        """);

        return scanner.nextInt();
    }

    public Integer menuTabelas() {
        System.out.println("""
                Informe a opção desejada:
                
                1 - Estabelecimentos
                2 - Funcionários
                3 - Fornecedores
                4 - Produtos
                5 - Pedidos
                6 - Vendas""");

        System.out.println("0 - Voltar\nInforme a opção: ");

        return scanner.nextInt();
    }



    public void clearTerminal() {
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.flush();
    }

    public void pauseTerminal() {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        } scanner.nextLine();
    }
}
