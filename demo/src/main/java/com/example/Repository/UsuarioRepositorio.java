package com.example.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.Anotacoes.MyRepository;
import com.example.Conexao.ConexaoJDBC;
import com.example.Entidade.Usuario;

@MyRepository
public class UsuarioRepositorio {

    public void Create(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome, email, cpf) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoJDBC.conexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getCpf());

            stmt.executeUpdate();
            System.out.println("Usuario cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar Usuario: " + e.getMessage());
        }
    }

    public boolean Delete(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";

        try (Connection conn = ConexaoJDBC.conexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Usuario removido com sucesso!");
            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao remover Usuario: " + e.getMessage());
        }

        return false;
    }

    public ArrayList<Usuario> ReadAll() {
        String sql = "SELECT * FROM usuario";
        ArrayList<Usuario> lista = new ArrayList<>();

        try (Connection conn = ConexaoJDBC.conexao();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getString("nome"),
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("cpf"));
                lista.add(usuario);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar Usuario: " + e.getMessage());
        }

        return lista;
    }

    public boolean Update(Usuario usuario) {
        String sql = "UPDATE usuario SET nome = ?, email = ?, cpf = ? WHERE id = ?";

        try (Connection conn = ConexaoJDBC.conexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getCpf());
            stmt.setInt(4, usuario.getId());

            stmt.executeUpdate();
            System.out.println("Usuario atualizado com sucesso!");
            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar Usuario: " + e.getMessage());
        }

        return false;
    }

    public Usuario readID(int id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";

        try (Connection conn = ConexaoJDBC.conexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getString("nome"),
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("cpf"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar Usuario: " + e.getMessage());
        }

        return null;
    }
}
