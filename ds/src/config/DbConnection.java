package config; //indica que o arquivo esta na pasta config

import java.sql.Connection; //conecta com o banco de dados
import java.sql.DriverManager; //gerencia a conectividade dos drivers disponiveis na aplicacao
import java.sql.SQLException; //lança uma excecao para quando der erro no banco de dados

public class DbConnection { //encapsula a logica de conexao com o banco
    private static final String URL =
    "jdbc:mysql://localhost:3306/projetods?useSSL=false&serverTimezone=UTC"; //caminho para o banco de dados projetods
    private static final String USER = "root"; //usuario e senha(vazia) para acessar o banco
    private static final String PASSWORD = "";

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException { //cria um metodo para que outras partes do projeto se conectem com o bd
        if (connection == null || connection.isClosed()) { //so conecta se estiver desconectado
            try {
                // Carrega o driver do MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (Exception e) { //se der erro ao 'try' cai no 'catch'
                System.out.println("Driver do MySQL não encontrado.");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return connection; //retorna conectado
    }

    public static void disconnect(Connection connection) { //metodo para desconectar
        try {
            connection.close();
        } catch (SQLException e) { //se der erro ao 'try' cai no 'catch'
            throw new RuntimeException("Error disconnection the database", e);
        }
    }
}
