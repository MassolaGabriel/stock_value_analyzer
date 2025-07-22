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

    public ArrayList<Acao> listar() {
        try {
            ArrayList<Acao> acoes = new ArrayList<>();
            String sql = "SELECT * FROM acaovalida;";
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
    //    public Boolean cadastrar(Acao acao){}
    //    public Boolean atualizar(Acao acao){};
    //  public Boolean buscarPeloTicker(Acao acao){};
    //public Boolean removerAcao(Acao acao){};
    //    public Boolean remover (String ticker){};

}
