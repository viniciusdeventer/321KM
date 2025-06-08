package dao;

import factory.Conexao;
import model.Fornecedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FornecedorDAO {
    private Connection conn;

    public FornecedorDAO() {
        conn = Conexao.getConnection();
    }

    public List<Fornecedor> listar() {
        List<Fornecedor> lista = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Fornecedores");

            while (rs.next()) {
            	lista.add(new Fornecedor(
        		    rs.getInt("ID_Fornecedor"),
        		    rs.getString("Nome"),
        		    rs.getInt("Tipo_Pessoa"),
        		    rs.getString("Inscricao"),
        		    rs.getString("Fantasia"),
        		    rs.getString("Telefone"),
        		    rs.getString("Email"),
        		    rs.getString("Endereco"),
        		    rs.getString("Numero"),
        		    rs.getString("Complemento"),
        		    rs.getString("Bairro"),
        		    rs.getString("Cidade"),
        		    rs.getString("Estado"),
        		    rs.getString("CEP"),
        		    rs.getInt("Status")
        		));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Fornecedor> buscar(String filtro) {
        List<Fornecedor> busca = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Fornecedores WHERE LOWER(Nome) LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            String filtroLike = "%" + filtro.toLowerCase() + "%";
            stmt.setString(1, filtroLike);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                busca.add(new Fornecedor(
        		    rs.getInt("ID_Fornecedor"),
        		    rs.getString("Nome"),
        		    rs.getInt("Tipo_Pessoa"),
        		    rs.getString("Inscricao"),
        		    rs.getString("Fantasia"),
        		    rs.getString("Telefone"),
        		    rs.getString("Email"),
        		    rs.getString("Endereco"),
        		    rs.getString("Numero"),
        		    rs.getString("Complemento"),
        		    rs.getString("Bairro"),
        		    rs.getString("Cidade"),
        		    rs.getString("Estado"),
        		    rs.getString("CEP"),
        		    rs.getInt("Status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return busca;
    }

    public void salvar(Fornecedor u) {
        try {
            String sql;
            boolean novo = (u.getId() == 0);
            PreparedStatement stmt;

            if (novo) {
                sql = "INSERT INTO Fornecedores (Nome, Tipo_Pessoa, Inscricao, Fantasia, Telefone, Email, Endereco, Numero, Complemento, Bairro, Cidade, Estado, CEP, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, u.getNome());
                stmt.setInt(2, u.getTipoPessoa());
                stmt.setString(3, u.getInscricao());
                stmt.setString(4, u.getFantasia());
                stmt.setString(5, u.getTelefone());
                stmt.setString(6, u.getEmail());
                stmt.setString(7, u.getEndereco());
                stmt.setString(8, u.getNumero());
                stmt.setString(9, u.getComplemento());
                stmt.setString(10, u.getBairro());
                stmt.setString(11, u.getCidade());
                stmt.setString(12, u.getEstado());
                stmt.setString(13, u.getCEP());
                stmt.setInt(14, u.getStatus());
            } else {
                sql = "UPDATE Fornecedores SET Nome=?, Tipo_Pessoa=?, Inscricao=?, Fantasia=?, Telefone=?, Email=?, Endereco=?, Numero=?, Complemento=?, Bairro=?, Cidade=?, Estado=?, CEP=?, Status=? WHERE ID_Fornecedor=?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, u.getNome());
                stmt.setInt(2, u.getTipoPessoa());
                stmt.setString(3, u.getInscricao());
                stmt.setString(4, u.getFantasia());
                stmt.setString(5, u.getTelefone());
                stmt.setString(6, u.getEmail());
                stmt.setString(7, u.getEndereco());
                stmt.setString(8, u.getNumero());
                stmt.setString(9, u.getComplemento());
                stmt.setString(10, u.getBairro());
                stmt.setString(11, u.getCidade());
                stmt.setString(12, u.getEstado());
                stmt.setString(13, u.getCEP());
                stmt.setInt(14, u.getStatus());
                stmt.setInt(15, u.getId());
            }

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void excluir(int id) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Fornecedores WHERE ID_Fornecedor=?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
