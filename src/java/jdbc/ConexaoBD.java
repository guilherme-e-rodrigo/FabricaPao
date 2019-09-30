package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    
    private final String caminho = "jdbc:postgresql://localhost:5432/FabricaPao";
    private final String driver = "org.postgresql.Driver";
    private final String usuario = "postgres";
    private final String senha = "rodrigo1";
    
    public Connection getConnection() throws ClassNotFoundException{
        try {
            System.out.println("Conectou no Banco!");
            Class.forName(driver);
            return DriverManager.getConnection(caminho, usuario, senha);            
        } catch (SQLException e) {
            System.out.println("Erro no Banco de Dados!");
            throw new RuntimeException(e);
        }
    }
}
