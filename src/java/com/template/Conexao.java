package com.template;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    // Configurações do seu banco de dados PostgreSQL
    private static final String conexao = "jdbc:postgresql://localhost:5432/db_pizzaria";
    private static final String usuario = "postgres";
    private static final String senha = "postgres";

    public Connection conectaBD() {
        try {
            return DriverManager.getConnection(conexao, usuario, senha);
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }
}