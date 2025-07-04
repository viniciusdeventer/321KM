package dao;

import factory.Conexao;
import model.ProdutosFornecedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutosFornecedorDAO {
    private Connection conn;

    public ProdutosFornecedorDAO() {
        conn = Conexao.getConnection();
    }

    public List<ProdutosFornecedor> listar() {
        List<ProdutosFornecedor> lista = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT PF.*, P.Nome FROM Produtos_Fornecedor PF INNER JOIN Produtos P ON PF.ID_Produto = P.ID_Produto ");

            while (rs.next()) {
                lista.add(new ProdutosFornecedor(
                    rs.getInt("ID_Produto"),
                    rs.getString("Nome"),
                    rs.getInt("ID_Fornecedor"),
                    rs.getDouble("Valor_Venda"),
                    rs.getDouble("Valor_Custo")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<ProdutosFornecedor> buscar(int idFornecedor) {
        List<ProdutosFornecedor> busca = new ArrayList<>();
        try {
            String sql = "SELECT PF.*, P.Nome FROM Produtos_Fornecedor PF INNER JOIN Produtos P ON PF.ID_Produto = P.ID_Produto WHERE ID_Fornecedor = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idFornecedor); 

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                busca.add(new ProdutosFornecedor(
                    rs.getInt("ID_Produto"),
                    rs.getString("Nome"),
                    rs.getInt("ID_Fornecedor"),
                    rs.getDouble("Valor_Venda"),
                    rs.getDouble("Valor_Custo")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return busca;
    }

    public void salvar(ProdutosFornecedor p) {
        try {
            String select = "SELECT COUNT(*) FROM Produtos_Fornecedor WHERE ID_Produto = ? AND ID_Fornecedor = ?";
            PreparedStatement checkStmt = conn.prepareStatement(select);
            checkStmt.setInt(1, p.getIdProduto());
            checkStmt.setInt(2, p.getIdFornecedor());
            ResultSet rs = checkStmt.executeQuery();
            boolean novo = rs.next() && rs.getInt(1) > 0;

            String sql;
            PreparedStatement stmt;

            if (!novo) {
                sql = "INSERT INTO Produtos_Fornecedor (ID_Produto, ID_Fornecedor, Valor_Venda, Valor_Custo) VALUES (?, ?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, p.getIdProduto());
                stmt.setInt(2, p.getIdFornecedor());
                stmt.setDouble(3, p.getValorVenda());
                stmt.setDouble(4, p.getValorCusto());
            } else {
                sql = "UPDATE Produtos_Fornecedor SET Valor_Venda = ?, Valor_Custo = ? WHERE ID_Produto = ? AND ID_Fornecedor = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setDouble(1, p.getValorVenda());
                stmt.setDouble(2, p.getValorCusto());
                stmt.setInt(3, p.getIdProduto());
                stmt.setInt(4, p.getIdFornecedor());
            }

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void excluir(int idProduto, int idFornecedor) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Produtos_Fornecedor WHERE ID_Produto=? AND ID_Fornecedor=?");
            stmt.setInt(1, idProduto);
            stmt.setInt(2, idFornecedor);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
