package dao;

import factory.Conexao;
import model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
	private Connection conn;

    public ClienteDAO() {
        conn = Conexao.getConnection();
    }

    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Clientes");

            while (rs.next()) {
                lista.add(new Cliente(
                    rs.getInt("ID_Cliente"),
                    rs.getString("Nome"),
                    rs.getString("Telefone"),	
                    rs.getString("Email"),
                    rs.getInt("Status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Cliente> buscar(String filtro) {
        List<Cliente> busca = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Clientes WHERE LOWER(Nome) LIKE ? AND Status = 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + filtro.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                busca.add(new Cliente(
                    rs.getInt("ID_Cliente"),
                    rs.getString("Nome"),
                    rs.getString("Telefone"),	
                    rs.getString("Email"),
                    rs.getInt("Status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return busca;
    }

    public void salvar(Cliente c) {
        try {
            String sql;
            boolean novo = (c.getId() == 0);
            PreparedStatement stmt;

            if (novo) {
                sql = "INSERT INTO Clientes (Nome, Telefone, Email, Status) VALUES (?, ?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, c.getNome());
                stmt.setString(2, c.getTelefone());
                stmt.setString(3, c.getEmail());
                stmt.setInt(4, c.getStatus());
            } else {
                sql = "UPDATE Clientes SET Nome=?, Telefone=?, Email=?, Status=? WHERE ID_Cliente=?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, c.getNome());
                stmt.setString(2, c.getTelefone());
                stmt.setString(3, c.getEmail());
                stmt.setInt(4, c.getStatus());
            }

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Clientes WHERE ID_Cliente=?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
