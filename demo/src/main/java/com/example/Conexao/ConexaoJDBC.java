package com.example.Conexao;

import java.sql.*;

public class ConexaoJDBC {
    public static String url = "jdbc:mysql://localhost:3306/apiserver";
    public static String user = "root";
    public static String password = "";

    public static Connection conexao() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
