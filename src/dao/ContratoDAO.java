package dao;

import factory.Conexao;
import model.Contrato;
import model.Fornecedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContratoDAO {
    private Connection conn;

    public ContratoDAO() {
        conn = Conexao.getConnection();
    }

    public List<Contrato> listar() {
        List<Contrato> lista = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Contratos_Fornecedor");

            while (rs.next()) {
                Contrato contrato = new Contrato(
                    rs.getInt("ID_Contrato"),
                    rs.getDate("Data_Inicio"),
                    rs.getDate("Data_Vencimento"),
                    rs.getString("Contato"),
                    rs.getString("Caminho_Arquivo"),
                    rs.getInt("Status")
                );
                
                // Carrega o fornecedor associado
                if (rs.getInt("ID_Fornecedor") > 0) {
                    Fornecedor fornecedor = new Fornecedor();
                    fornecedor.setId(rs.getInt("ID_Fornecedor"));
                    contrato.setFornecedor(fornecedor);
                }
                
                lista.add(contrato);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Contrato buscar(int id) {
        try {
            String sql = "SELECT * FROM Contratos_Fornecedor WHERE ID_Contrato = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Contrato contrato = new Contrato(
                    rs.getInt("ID_Contrato"),
                    rs.getDate("Data_Inicio"),
                    rs.getDate("Data_Vencimento"),
                    rs.getString("Contato"),
                    rs.getString("Caminho_Arquivo"),
                    rs.getInt("Status")
                );
                
                if (rs.getInt("ID_Fornecedor") > 0) {
                    Fornecedor fornecedor = new Fornecedor();
                    fornecedor.setId(rs.getInt("ID_Fornecedor"));
                    contrato.setFornecedor(fornecedor);
                }
                
                return contrato;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Contrato buscarPorFornecedor(int idFornecedor) {
        try {
            String sql = "SELECT * FROM Contratos_Fornecedor WHERE ID_Fornecedor = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idFornecedor);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Contrato contrato = new Contrato(
                    rs.getInt("ID_Contrato"),
                    rs.getDate("Data_Inicio"),
                    rs.getDate("Data_Vencimento"),
                    rs.getString("Contato"),
                    rs.getString("Caminho_Arquivo"),
                    rs.getInt("Status")
                );
                
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setId(idFornecedor);
                contrato.setFornecedor(fornecedor);
                
                return contrato;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void salvar(Contrato c) {
        try {
            String sql;
            boolean novo = (c.getId() == 0);
            PreparedStatement stmt;

            if (novo) {
                sql = "INSERT INTO Contratos_Fornecedor (Data_Inicio, Data_Vencimento, Contato, Caminho_Arquivo, Status, ID_Fornecedor) VALUES (?, ?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            } else {
                sql = "UPDATE Contratos_Fornecedor SET Data_Inicio=?, Data_Vencimento=?, Contato=?, Caminho_Arquivo=?, Status=?, ID_Fornecedor=? WHERE ID_Contrato=?";
                stmt = conn.prepareStatement(sql);
            }

            stmt.setDate(1, new java.sql.Date(c.getDataInicio().getTime()));
            stmt.setDate(2, new java.sql.Date(c.getDataVencimento().getTime()));
            stmt.setString(3, c.getContato());
            stmt.setString(4, c.getCaminho());
            stmt.setInt(5, c.getStatus());
            stmt.setInt(6, c.getFornecedor().getId());

            if (!novo) {
                stmt.setInt(7, c.getId());
            }

            stmt.executeUpdate();

            if (novo) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    c.setId(rs.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Contratos_Fornecedor WHERE ID_Contrato=?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}