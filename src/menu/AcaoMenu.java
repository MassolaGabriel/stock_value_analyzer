package menu;

import dao.AcaoDAO;

import java.util.ArrayList;
import java.util.Scanner;

public class AcaoMenu {
    private static AcaoDAO dao = new AcaoDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;

        do {
            System.out.println("---------MENU AÇÃO----------");
            System.out.println("[1] Listar ações");
            System.out.println("[2] Cadastrar nova ação");
            System.out.println("[3] Atualizar uma ação existente");
            System.out.println("[4] Buscar ação pelo Ticker");
            System.out.println("[5] Remover uma ação");
            System.out.println("[0] Sair");
            System.out.println("\nEscolha uma opção: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Opção inválida. Por favor digite um novo número. ");
                scanner.next();
                System.out.println("Escolha uma opção: ");
            }

            opcao = scanner.nextInt();
            scanner.nextLine();

        } while (opcao != 0);

//        Fazer o switch e as funções
        switch (opcao) {
            case 1:
//                Fazer
        }
    }
}
