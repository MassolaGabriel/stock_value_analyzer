package menu;

import dao.AcaoDAO;
import model.Acao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AcaoMenu {
    private static AcaoDAO dao = new AcaoDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;

        do {
            System.out.println("\n---------MENU AÇÃO----------");
            System.out.println("[1] Listar ações");
            System.out.println("[2] Cadastrar nova ação");
            System.out.println("[3] Atualizar uma ação existente");
            System.out.println("[4] Buscar ação pelo Ticker");
            System.out.println("[5] Remover uma ação");
            System.out.println("[6] Verificar preço ideal (Método Barsi)");
            System.out.println("[0] Sair");
            System.out.print("\nEscolha uma opção: ");

            try {
                opcao = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida. Por favor, digite um número.");
                opcao = -1;
            } finally {
                scanner.nextLine();
            }

            switch (opcao) {
                case 1:
                    listarAcoes();
                    break;
                case 2:
                    cadastrarAcao();
                    break;
                case 3:
                    atualizarAcao();
                    break;
                case 4:
                    buscarPeloTicker();
                    break;
                case 5:
                    removerAcao();
                    break;
                case 6:
                    verificarPrecoIdealBarsi();
                    break;
                case 0:
                    System.out.println("Saindo do menu Ação...");
                    break;
                case -1:
                    break;
                default:
                    System.out.println("Opção não encontrada. Tente novamente.");
                    break;
            }
        } while (opcao != 0);
        scanner.close();
    }

    public static void listarAcoes() {
        ArrayList<Acao> acoes = dao.listarAcoes();
        if (acoes != null && !acoes.isEmpty()) {
            System.out.println("\n--------LISTA DE AÇÕES---------");
            for (Acao acao : acoes) {
                System.out.println(acao);
            }
        } else {
            System.out.println("\nNenhuma ação foi encontrada.");
        }
    }

    public static void cadastrarAcao() {
        try {
            System.out.println("\n--- Cadastro de Nova Ação ---");
            System.out.print("Setor para nova açao: ");
            String setor = scanner.nextLine().toUpperCase();
            System.out.print("Ticker da nova ação: ");
            String ticker = scanner.nextLine().toUpperCase();
            System.out.print("Preço atual: ");
            Double precoAtual = scanner.nextDouble();
            System.out.print("Media de dividendos dos ultimos 5 anos: ");
            Double mediaCincoAnos = scanner.nextDouble();

            Acao acao = new Acao(setor, ticker, precoAtual, mediaCincoAnos);

            if (dao.cadastrarNovaAcao(acao)) {
                System.out.println("\n>> Ação cadastrada com sucesso!");
            } else {
                System.out.println("\n>> Erro ao cadastrar nova ação.");
            }
        } catch (InputMismatchException e) {
            System.out.println("\nErro de entrada. Preço e Média de Dividendos devem ser números.");
        } finally {
            scanner.nextLine();
        }
    }

    public static void buscarPeloTicker() {
        System.out.print("\nQual o ticker da ação desejada? ");
        String ticker = scanner.nextLine().toUpperCase();
        Acao acao = dao.buscarPeloTicker(ticker);
        if (acao != null) {
            System.out.println("\n>> Ação encontrada: " + acao);
        } else {
            System.out.println("\n>> Ação com o ticker: " + ticker + " não foi encontrada.");
        }
    }

    public static void atualizarAcao() {
        System.out.print("\nQual o ticker da ação que iremos atualizar? ");
        String ticker = scanner.nextLine().toUpperCase();
        Acao acao = dao.buscarPeloTicker(ticker);
        if (acao == null) {
            System.out.println("\n>> Acao com o ticker " + ticker + " não foi encontrada.");
            return;
        }
        System.out.println("\n>> Ação encontrada: " + acao);
        System.out.println("Deixe o campo em branco para não alterar o valor atual.");
        System.out.print("Novo setor da ação: ");
        String setorInput = scanner.nextLine().toUpperCase();
        if (!setorInput.trim().isEmpty()) {
            acao.setSetor(setorInput);
        }
        try {
            System.out.print("Nova cotação atual: ");
            String precoInput = scanner.nextLine();
            if (!precoInput.trim().isEmpty()) {
                acao.setPrecoAtual(Double.parseDouble(precoInput.replace(",", ".")));
            }
            System.out.print("Nova media de dividendos dos ultimos cinco anos: ");
            String mediaInput = scanner.nextLine();
            if (!mediaInput.trim().isEmpty()) {
                acao.setMediaDivCincoAnos(Double.parseDouble(mediaInput.replace(",", ".")));
            }
        } catch (NumberFormatException e) {
            System.out.println("\nErro: Valor inválido para número. A atualização foi cancelada.");
            return;
        }
        if (dao.atualizarAcao(acao)) {
            System.out.println("\n>> Ação atualizada com sucesso!");
        } else {
            System.out.println("\n>> Erro ao atualizar a ação.");
        }
    }

    public static void removerAcao() {
        System.out.print("\nQual ticker da ação que iremos remover? ");
        String tickerParaRemover = scanner.nextLine().toUpperCase();
        if (tickerParaRemover.trim().isEmpty()) {
            System.out.println("\n>> Ticker não pode ser vazio.");
            return;
        }
        System.out.print("Tem certeza que deseja remover a ação " + tickerParaRemover + "? (S/N): ");
        String confirmacao = scanner.nextLine();
        if (confirmacao.equalsIgnoreCase("S")) {
            if (dao.removerAcao(tickerParaRemover)) {
                System.out.println("\n>> Ação removida com êxito!");
            } else {
                System.out.println("\n>> Erro ao remover ação. Verifique se o ticker '" + tickerParaRemover + "' está correto.");
            }
        } else {
            System.out.println("\n>> Remoção cancelada.");
        }
    }

    public static Acao verificarPrecoIdealBarsi() {
        System.out.print("\nDigite o ticker da ação que deseja analisar: ");
        String ticker = scanner.nextLine().toUpperCase();
        Acao acao = dao.buscarPeloTicker(ticker);
        if (acao == null) {
            System.out.println("\nERRO: Ação com o ticker '" + ticker + "' não foi encontrada.");
            return null;
        }
        if (acao.getMediaDivCincoAnos() == null || acao.getMediaDivCincoAnos() <= 0 || acao.getPrecoAtual() == null || acao.getPrecoAtual() <= 0) {
            System.out.println("\nERRO: Não foi possível calcular. Dados de Preço ou Média de Dividendos são inválidos para '" + ticker + "'.");
            return acao;
        }
        double mediaDividendos = acao.getMediaDivCincoAnos();
        double precoAtual = acao.getPrecoAtual();
        double yieldProjetado = (mediaDividendos / precoAtual) * 100;
        final double YIELD_ALVO = 0.06;
        double precoTeto = mediaDividendos / YIELD_ALVO;

        System.out.println("\n---------- RESULTADO DA ANÁLISE ----------");
        System.out.printf("Ação Analisada: %s\n", acao.getTicker());
        System.out.printf("Preço Atual: R$%,.2f\n", precoAtual);
        System.out.printf("Média de Dividendos (5a): R$%,.2f\n", mediaDividendos);
        System.out.println("--------------------------------------------");
        System.out.printf("=> Yield Projetado: %.2f%%\n", yieldProjetado);
        System.out.println("--------------------------------------------");

        if (yieldProjetado >= (YIELD_ALVO * 100)) {
            System.out.println("✅ VEREDITO: BOM PREÇO PARA APORTE!");
            System.out.printf("O yield projetado de %.2f%% está acima do critério de %.1f%%.\n", yieldProjetado, (YIELD_ALVO * 100));
        } else {
            System.out.println("❌ VEREDITO: PREÇO NÃO ESTÁ IDEAL.");
            System.out.printf("O yield projetado de %.2f%% está abaixo do critério de %.1f%%.\n", yieldProjetado, (YIELD_ALVO * 100));
            System.out.println("\n💡 DICA DE PREÇO:");
            System.out.printf("   Para atingir o yield de %.1f%%, o preço da ação deveria ser no máximo R$%,.2f (Preço Teto).\n", (YIELD_ALVO * 100), precoTeto);
        }
        System.out.println("--------------------------------------------");
        return acao;
    }
}