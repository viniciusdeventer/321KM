package factory;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/321KM?useSSL=false&serverTimezone=UTC",
                "root",
                ""     
            );
        } catch (Exception e) {
            System.out.println("Erro na conexão com o banco:");
            e.printStackTrace();
            return null;
        }
    }
}
