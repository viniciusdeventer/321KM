package dao;

import factory.Conexao;
import model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private Connection conn;

    public ProdutoDAO() {
        conn = Conexao.getConnection();
    }

    public List<Produto> listar() {
        List<Produto> lista = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Produtos");

            while (rs.next()) {
                lista.add(new Produto(
                    rs.getInt("ID_Produto"),
                    rs.getString("Nome"),
                    rs.getString("Descricao"),
                    rs.getDouble("Valor_Produto"),
                    rs.getInt("Tipo_Produto"),
                    rs.getInt("Status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Produto> buscar(String filtro) {
        List<Produto> busca = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Produtos WHERE LOWER(Nome) LIKE ? OR LOWER(Descricao) LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            String filtroLike = "%" + filtro.toLowerCase() + "%";
            stmt.setString(1, filtroLike);
            stmt.setString(2, filtroLike);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                busca.add(new Produto(
                    rs.getInt("ID_Produto"),
                    rs.getString("Nome"),
                    rs.getString("Descricao"),
                    rs.getDouble("Valor_Produto"),
                    rs.getInt("Tipo_Produto"),
                    rs.getInt("Status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return busca;
    }

    public void salvar(Produto u) {
        try {
            String sql;
            boolean novo = (u.getId() == 0);
            PreparedStatement stmt;

            if (novo) {
                sql = "INSERT INTO Produtos (Nome, Descricao, Valor_Produto, Tipo_Produto, Status) VALUES (?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, u.getNome());
                stmt.setString(2, u.getDesc());
                stmt.setDouble(3, u.getValorProduto());
                stmt.setInt(4, u.getTipoProduto());
                stmt.setInt(5, u.getStatus());
            } else {
                sql = "UPDATE Produtos SET Nome=?, Descricao=?, Valor_Produto=?, Tipo_Produto=?, Status=? WHERE ID_Produto=?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, u.getNome());
                stmt.setString(2, u.getDesc());
                stmt.setDouble(3, u.getValorProduto());
                stmt.setInt(4, u.getTipoProduto());
                stmt.setInt(5, u.getStatus());
                stmt.setInt(6, u.getId());
            }

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Produtos WHERE ID_Produto=?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
