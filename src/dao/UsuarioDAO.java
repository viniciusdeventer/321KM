package dao;

import model.Usuario;
import factory.Conexao;
import java.sql.*;
import java.util.*;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class UsuarioDAO {
    private Connection conn;

    public UsuarioDAO() {
        conn = Conexao.getConnection();
    }

    public Usuario login(String login, String senhaDigitada) {
        try {
            //String sql = "SELECT * FROM Usuarios WHERE Usuario=? AND Status = 1";
        	String sql = " SELECT U.*, " +
        				 " CASE WHEN U.Tipo_Usuario = 0 THEN C.Nome " +
        				 " ELSE V.Nome END AS Nome_Usuario " +
   			             " FROM Usuarios U " + 
   			             " LEFT JOIN Clientes C ON U.ID_Referencia = C.ID_Cliente " + 
						 " LEFT JOIN Vendedores V ON U.ID_Referencia = V.ID_Vendedor " +
						 " WHERE Usuario=? AND U.Status = 1 ";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String senhaHashBanco = rs.getString("Senha");

                BCrypt.Result result = BCrypt.verifyer().verify(senhaDigitada.toCharArray(), senhaHashBanco);
                if (result.verified) {
                    return new Usuario(
                        rs.getInt("ID_Usuario"),
                        rs.getString("Usuario"),
                        senhaHashBanco,
                        rs.getInt("Tipo_Usuario"),
                        rs.getInt("ID_Referencia"),
                        rs.getString("Nome_Usuario"),
                        rs.getInt("Status")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(" SELECT U.*, " +
							   				 " CASE WHEN U.Tipo_Usuario = 0 THEN C.Nome " +
							   				 " ELSE V.Nome END AS Nome_Usuario " +
						            		 " FROM Usuarios U " + 
            								 " LEFT JOIN Clientes C ON U.ID_Referencia = C.ID_Cliente " + 
            								 " LEFT JOIN Vendedores V ON U.ID_Referencia = V.ID_Vendedor ");
            while (rs.next()) {
                lista.add(new Usuario(
                    rs.getInt("ID_Usuario"),
                    rs.getString("Usuario"),
                    rs.getString("Senha"),
                    rs.getInt("Tipo_Usuario"),
                    rs.getInt("ID_Referencia"),
                    rs.getString("Nome_Usuario"),                    
                    rs.getInt("Status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public List<Usuario> buscar(String filtro) {
        List<Usuario> busca = new ArrayList<>();
        try {
            //String sql = "SELECT * FROM Usuarios WHERE LOWER(Usuario) LIKE ? AND Status = 1";
        	String sql = " SELECT U.*, " +
		   				 " CASE WHEN U.Tipo_Usuario = 0 THEN C.Nome " +
		   				 " ELSE V.Nome END AS Nome_Usuario " +
        			     " FROM Usuarios U " + 
						 " LEFT JOIN Clientes C ON U.ID_Referencia = C.ID_Cliente " + 
						 " LEFT JOIN Vendedores V ON U.ID_Referencia = V.ID_Vendedor " +
						 " WHERE LOWER(U.Usuario) LIKE ? AND U.Status = 1 ";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + filtro.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	busca.add(new Usuario(
                    rs.getInt("ID_Usuario"),
                    rs.getString("Usuario"),
                    rs.getString("Senha"),
                    rs.getInt("Tipo_Usuario"),
                    rs.getInt("ID_Referencia"),
                    rs.getString("Nome_Usuario"),
                    rs.getInt("Status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return busca;
    }

    public void salvar(Usuario u) {
        try {
            String sql;
            boolean novo = (u.getId() == 0);
            PreparedStatement stmt;

            if (novo) {
                sql = "INSERT INTO Usuarios (Usuario, Senha, Tipo_Usuario, ID_Referencia, Status) VALUES (?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, u.getLogin());
                
                String senhaHash = BCrypt.withDefaults().hashToString(12, u.getSenha().toCharArray());
                stmt.setString(2, senhaHash);
                stmt.setInt(3, u.getTipo());
                stmt.setInt(4, u.getIdReferencia());
                stmt.setInt(5, u.getStatus());

            } else {
            	sql = "UPDATE Usuarios SET Usuario=?, Tipo_Usuario=?, ID_Referencia=?, Status=? WHERE ID_Usuario=?";
            	stmt = conn.prepareStatement(sql);
            	stmt.setString(1, u.getLogin());
            	stmt.setInt(2, u.getTipo());
            	stmt.setInt(3, u.getIdReferencia());
            	stmt.setInt(4, u.getStatus());
            	stmt.setInt(5, u.getId());
            }

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Usuarios WHERE ID_Usuario=?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
