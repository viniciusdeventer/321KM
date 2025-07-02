package dao;

import factory.Conexao;
import model.Vendedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendedorDAO {
	private Connection conn;

    public VendedorDAO() {
        conn = Conexao.getConnection();
    }

    public List<Vendedor> listar() {
        List<Vendedor> lista = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Vendedores");

            while (rs.next()) {
                lista.add(new Vendedor(
                    rs.getInt("ID_Vendedor"),
                    rs.getString("Nome"),
                    rs.getString("Telefone"),	
                    rs.getString("Email"),
                    rs.getDouble("Tx_Comissao"),
                    rs.getInt("Status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Vendedor> buscar(String filtro) {
        List<Vendedor> busca = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Vendedores WHERE LOWER(Nome) LIKE ? AND Status = 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + filtro.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                busca.add(new Vendedor(
                    rs.getInt("ID_Vendedor"),
                    rs.getString("Nome"),
                    rs.getString("Telefone"),	
                    rs.getString("Email"),
                    rs.getDouble("Tx_Comissao"),
                    rs.getInt("Status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return busca;
    }

    public void salvar(Vendedor v) {
        try {
            String sql;
            boolean novo = (v.getId() == 0);
            PreparedStatement stmt;

            if (novo) {
                sql = "INSERT INTO Vendedores (Nome, Telefone, Email, Tx_Comissao, Status) VALUES (?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, v.getNome());
                stmt.setString(2, v.getTelefone());
                stmt.setString(3, v.getEmail());
                stmt.setDouble(4, v.getTxComissao());
                stmt.setInt(5, v.getStatus());
            } else {
                sql = "UPDATE Vendedores SET Nome=?, Telefone=?, Email=?, Tx_Comissao=?, Status=? WHERE ID_Vendedor=?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, v.getNome());
                stmt.setString(2, v.getTelefone());
                stmt.setString(3, v.getEmail());
                stmt.setDouble(4, v.getTxComissao());
                stmt.setInt(5, v.getStatus());
            }

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Vendedores WHERE ID_Vendedor=?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
