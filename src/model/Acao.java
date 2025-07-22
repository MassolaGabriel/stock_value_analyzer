package model;

import java.util.Scanner;

public class Acao {
    Scanner scanner = new Scanner(System.in);

    private String setor;
    private String ticker;
    private Double mediaDivCincoAnos;
    private Double precoAtual;

    public Acao(String setor, String ticker, Double precoAtual, Double mediaDivCincoAnos) {
        this.setor = setor;
        this.ticker = ticker;
        this.precoAtual = precoAtual;
        this.mediaDivCincoAnos = mediaDivCincoAnos;
    }

    public Acao() {
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Double getMediaDivCincoAnos() {
        return mediaDivCincoAnos;
    }

    public void setMediaDivCincoAnos(Double mediaDivCincoAnos) {
        this.mediaDivCincoAnos = mediaDivCincoAnos;
    }

    public Double getPrecoAtual() {
        return precoAtual;
    }

    public void setPrecoAtual(Double precoAtual) {
        this.precoAtual = precoAtual;
    }

    public static Double calcularMediaDividendos(Scanner scanner) {
        Double[] anos = new Double[5];
        Double soma = 0.0;
        Double media = 0.0;

        for (int i = 0; i < 5; i++) {
            System.out.printf("Qual o valor do ano %d? R$ \n", i + 1);
            anos[i] = scanner.nextDouble();
            scanner.nextLine();
        }

        for (int i = 0; i < anos.length; i++) {
            soma += anos[i];
        }

        return soma / anos.length;
    };

    public static Acao obterArgs(Scanner scanner){
        System.out.println("Qual setor da ação? ");
        String setor = scanner.nextLine();

        System.out.println("Qual o ticker? ");
        String ticker = scanner.nextLine();

        System.out.println("Qual o preço atual? ");
        Double precoAtual = scanner.nextDouble();
        scanner.nextLine();

        Double mediaDividendo = calcularMediaDividendos(scanner);
        Double dividendoYieldMedio = (mediaDividendo / precoAtual) * 100;

        return new Acao(setor, ticker, precoAtual, dividendoYieldMedio);
    }

    @Override
    public String toString() {
        return "{"
                + "\"scanner\":" + scanner + ", "
                + "\"setor\":\"" + setor + "\", "
                + "\"ticker\":\"" + ticker + "\", "
                + "\"mediaDivCincoAnos\":" + mediaDivCincoAnos + ", "
                + "\"precoAtual\":" + precoAtual
                + "}";
    }
}
