package dao;

import factory.Conexao;
import model.ProdutosCompra;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutosCompraDAO {
    private Connection conn;

    public ProdutosCompraDAO() {
        conn = Conexao.getConnection();
    }

    public List<ProdutosCompra> listar() {
        List<ProdutosCompra> lista = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT PC.*, P.Nome FROM Produtos_Compra PC INNER JOIN Produtos P ON PC.ID_Produto = P.ID_Produto");

            while (rs.next()) {
                lista.add(new ProdutosCompra(
            		rs.getInt("ID_Compra"),
                    rs.getInt("ID_Produto"),
                    rs.getString("Nome"),
                    rs.getInt("Quantidade"),
                    rs.getDouble("Valor_Produto"),
                    rs.getDouble("Desconto"),
                    rs.getDate("Data_Inicio"),
                    rs.getDate("Data_Fim"),
                    rs.getString("Observacoes"),
                    rs.getInt("Status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<ProdutosCompra> buscar(int idCompra) {
        List<ProdutosCompra> busca = new ArrayList<>();
        try {
            String sql = "SELECT PC.*, P.Nome FROM Produtos_Compra PC INNER JOIN Produtos P ON PC.ID_Produto = P.ID_Produto WHERE ID_Compra = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idCompra); 

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                busca.add(new ProdutosCompra(
            		rs.getInt("ID_Compra"),
                    rs.getInt("ID_Produto"),
                    rs.getString("Nome"),
                    rs.getInt("Quantidade"),
                    rs.getDouble("Valor_Produto"),
                    rs.getDouble("Desconto"),
                    rs.getDate("Data_Inicio"),
                    rs.getDate("Data_Fim"),
                    rs.getString("Observacoes"),
                    rs.getInt("Status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return busca;
    }

    public void salvar(ProdutosCompra p) {
        try {
            String select = "SELECT COUNT(*) FROM Produtos_Compra WHERE ID_Produto = ? AND ID_Compra = ?";
            PreparedStatement checkStmt = conn.prepareStatement(select);
            checkStmt.setInt(1, p.getIdProduto());
            checkStmt.setInt(2, p.getIdCompra());
            ResultSet rs = checkStmt.executeQuery();
            boolean novo = rs.next() && rs.getInt(1) > 0;

            String sql;
            PreparedStatement stmt;

            if (!novo) {
                sql = "INSERT INTO Produtos_Compra (ID_Produto, ID_Compra, Quantidade, Valor_Produto, Desconto, Data_Inicio, Data_Fim, Observacoes, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, p.getIdProduto());
                stmt.setInt(2, p.getIdCompra());
                stmt.setInt(3, p.getQuantidade());
                stmt.setDouble(4, p.getValorProduto());
                stmt.setDouble(5, p.getDesconto());
                stmt.setDate(6, new java.sql.Date(p.getDataInicio().getTime()));
                stmt.setDate(7, new java.sql.Date(p.getDataFim().getTime()));
                stmt.setString(8, p.getObservacao());
                stmt.setInt(9, p.getStatus());
            } else {
                sql = "UPDATE Produtos_Compra SET Quantidade = ?, Valor_Produto = ?, Desconto = ?, Data_Inicio = ?, Data_Fim = ?, Observacoes = ?, Status = ? WHERE ID_Produto = ? AND ID_Compra = ?";
                stmt = conn.prepareStatement(sql);               
                stmt.setInt(1, p.getQuantidade());
                stmt.setDouble(2, p.getValorProduto());
                stmt.setDouble(3, p.getDesconto());
                stmt.setDate(4, new java.sql.Date(p.getDataInicio().getTime()));
                stmt.setDate(5, new java.sql.Date(p.getDataFim().getTime()));
                stmt.setString(6, p.getObservacao());
                stmt.setInt(7, p.getStatus());
                stmt.setInt(8, p.getIdProduto());
                stmt.setInt(9, p.getIdCompra());
            }

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void excluir(int idProduto, int idCompra) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Produtos_Compra WHERE ID_Produto=? AND ID_Compra=?");
            stmt.setInt(1, idProduto);
            stmt.setInt(2, idCompra);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
