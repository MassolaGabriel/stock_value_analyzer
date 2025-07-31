package dao;

import config.ConnectionSQL;
import model.Acao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AcaoDAO {
    private Connection connection = ConnectionSQL.getConnection();

    public ArrayList<Acao> listarAcoes() {
        if (connection == null) {
            System.out.println("Não foi possível listar as ações. A conexão com o banco não foi estabelecida.");
            return new ArrayList<>();
        }

        ArrayList<Acao> acoes = new ArrayList<>();
        String sql = "SELECT * FROM todasAcoes;";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String setor = rs.getString("setor");
                String ticker = rs.getString("ticker");
                Double precoAtual = rs.getObject("precoAtual", Double.class);
                Double mediaDivCincoAnos = rs.getObject("mediaDivCincoAnos", Double.class);

                Acao acao = new Acao(setor, ticker, precoAtual, mediaDivCincoAnos);
                acoes.add(acao);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar as ações no banco. " + e.getMessage());
        }
        return acoes;
    }

    public Boolean cadastrarNovaAcao(Acao acao) {
        if (connection == null) return false;
        String sql = "INSERT INTO todasAcoes (setor, ticker, precoAtual, mediaDivCincoAnos) VALUES (?, ?, ?, ?);";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, acao.getSetor());
            ps.setString(2, acao.getTicker());
            ps.setObject(3, acao.getPrecoAtual());
            ps.setObject(4, acao.getMediaDivCincoAnos());
            int qtDeLinha = ps.executeUpdate();
            return qtDeLinha > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar nova ação. " + e.getMessage());
        }
        return false;
    }

    public Acao buscarPeloTicker(String ticker) {
        if (connection == null) return null;
        String sql = "SELECT * FROM todasAcoes WHERE ticker = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, ticker.toUpperCase());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String setor = rs.getString("setor");
                    String tickerAcao = rs.getString("ticker");
                    Double precoAtual = rs.getObject("precoAtual", Double.class);
                    Double mediaDivCincoAnos = rs.getObject("mediaDivCincoAnos", Double.class);

                    Acao acao = new Acao(setor, tickerAcao, precoAtual, mediaDivCincoAnos);
                    return acao;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar ação pelo Ticker! " + e.getMessage());
        }
        return null;
    }

    public Boolean atualizarAcao(Acao acao) {
        if (connection == null) return false;
        String sql = "UPDATE todasAcoes SET setor = ?, precoAtual = ?, mediaDivCincoAnos = ? WHERE ticker = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, acao.getSetor());
            ps.setObject(2, acao.getPrecoAtual());
            ps.setObject(3, acao.getMediaDivCincoAnos());
            ps.setString(4, acao.getTicker());
            int qtDeLinha = ps.executeUpdate();
            return qtDeLinha > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar a ação! " + e.getMessage());
        }
        return false;
    }

    public Boolean removerAcao(String ticker) {
        if (connection == null) return false;
        String sql = "DELETE FROM todasAcoes WHERE ticker = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, ticker.toUpperCase());
            int qtDeLinha = ps.executeUpdate();
            return qtDeLinha > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao remover a ação! " + e.getMessage());
        }
        return false;
    }
}