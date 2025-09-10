package com.example.Conexao;

import java.sql.*;

public class ConexaoJDBC {
    public static String url = "jdbc:mysql://localhost:3306/apiserver";
    public static String usuario = "root";
    public static String senha = "";

    public static Connection conexao() throws SQLException {
        return DriverManager.getConnection(url, usuario, senha);
    }
}
