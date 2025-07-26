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
            return null;
        }

        ArrayList<Acao> acoes = new ArrayList<>();
        String sql = "SELECT * FROM todasAcoes;";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) { // Usando try-with-resources

            while (rs.next()){
                String setor = rs.getString("setor");
                String ticker = rs.getString("ticker");
                Double precoAtual = rs.getDouble("precoAtual");
                Double mediaDivCincoAnos = rs.getDouble("mediaDivCincoAnos");

                Acao acao = new Acao(setor, ticker, precoAtual, mediaDivCincoAnos);
                acoes.add(acao);
            }
        } catch (SQLException e){
            System.out.println("Erro ao buscar as ações no banco. " + e.getMessage());
        }
        return acoes;
    }
        public Boolean cadastrar(Acao acao){
            try {
                String sql = "INSERT INTO todasAcoes (setor, ticker, precoAtual, mediaDivCincoAnos) VALUES (?, ?, ?, ?);";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, acao.getSetor());
                ps.setString(2, acao.getTicker());
                ps.setDouble(3, acao.getPrecoAtual());
                ps.setDouble(4, acao.getMediaDivCincoAnos());
            }catch (SQLException e ) {
                System.out.println("Erro ao cadastrar nova ação." + e.getMessage());
            }
            return null;
        }
    //    public Boolean atualizar(Acao acao){};
    //    public Boolean buscarPeloTicker(Acao acao){};
    //    public Boolean removerAcao(Acao acao){};
    //    public Boolean remover (String ticker){};

}
