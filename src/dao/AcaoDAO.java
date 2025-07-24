package dao;

import config.ConnectionSQL;
import model.Acao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AcaoDAO {
    private ConnectionSQL ConnectMySQL;
    private Connection connection = ConnectMySQL.getConnection();

    public ArrayList<Acao> listarAcoes() {
        try {
            ArrayList<Acao> acoes = new ArrayList<>();
            String sql = "SELECT * FROM todasAcoes;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String setor = rs.getString("setor");
                String ticker = rs.getString("ticker");
                Double precoAtual = rs.getDouble("precoAtual");
                Double mediaDivCincoAnos = rs.getDouble("mediaDivCincoAnos");

                Acao acao = new Acao(setor, ticker, precoAtual, mediaDivCincoAnos);

                acoes.add(acao);
            }
            return acoes;

        }catch (SQLException e){
            System.out.println("Erro ao se conectar com o banco de ações. " + e.getMessage());
        }
        return null;

    };
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
