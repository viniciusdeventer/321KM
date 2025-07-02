package dao;

import factory.Conexao;
import model.Compra;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompraDAO {
	private Connection conn;

    public CompraDAO() {
        conn = Conexao.getConnection();
    }

    public List<Compra> listar() {
        List<Compra> lista = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Compras");

            while (rs.next()) {
                lista.add(new Compra(
                    rs.getInt("ID_Compra"),
                    rs.getInt("ID_Cliente"),
                    rs.getInt("ID_Vendedor"),
					rs.getDate("Data_Compra"),
					rs.getInt("Status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Compra> buscar(int idCliente) {
        List<Compra> busca = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Compras WHERE ID_Compra = ? AND Status = 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                busca.add(new Compra(
                        rs.getInt("ID_Compra"),
                        rs.getInt("ID_Cliente"),
                        rs.getInt("ID_Vendedor"),
    					rs.getDate("Data_Compra"),
    					rs.getInt("Status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return busca;
    }

    public void salvar(Compra c) {
        try {
            String sql;
            boolean novo = (c.getId() == 0);
            PreparedStatement stmt;

            if (novo) {
                sql = "INSERT INTO Compras (ID_Cliente, ID_Vendedor, Data_Compra, Status) VALUES (?, ?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, c.getIdCliente());
                stmt.setInt(2, c.getIdVendedor());
                stmt.setDate(3, new java.sql.Date(c.getDataCompra().getTime()));
                stmt.setInt(4, c.getStatus());
            } else {
                sql = "UPDATE Compras SET ID_Vendedor=?, Data_Compra=?, Status=? WHERE ID_Compra=?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, c.getIdCliente());
                stmt.setInt(2, c.getIdVendedor());
                stmt.setDate(3, new java.sql.Date(c.getDataCompra().getTime()));
                stmt.setInt(4, c.getStatus());
            }

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Compras WHERE ID_Compra=?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
