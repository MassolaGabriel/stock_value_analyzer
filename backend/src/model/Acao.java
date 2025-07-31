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

    @Override
    public String toString() {
        return String.format("Setor: %-15s | Ticker: %-10s | Preço Atual: R$%.2f | Média Div. 5a: %.2f%%",
                this.setor,
                this.ticker,
                this.precoAtual,
                this.mediaDivCincoAnos
        );
    }
}
