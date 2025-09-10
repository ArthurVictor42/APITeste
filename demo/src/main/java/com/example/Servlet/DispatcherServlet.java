package com.example.Servlet;

import com.example.Controller.UsuarioController;
import com.example.Entidade.Usuario;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class DispatcherServlet extends HttpServlet {
    private final UsuarioController usuarioController;

    public DispatcherServlet(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        String path = req.getPathInfo();

        if ("/users".equals(path)) {
            resp.getWriter().println(usuarioController.listarUsuarios());
        } else if ("/user".equals(path)) {
            String idParam = req.getParameter("id");
            if (idParam != null) {
                int id = Integer.parseInt(idParam);
                resp.getWriter().println(usuarioController.buscarUsuario(id));
            } else {
                resp.getWriter().println("Informe o parâmetro id");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println("404 - Not Found");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        String body = readBody(req);

        // Exemplo de corpo: nome=João;email=joao@mail.com
        Usuario usuario = parseUsuario(body);

        if (usuario.getNome() != null && usuario.getEmail() != null) {
            resp.getWriter().println(usuarioController.createUsuario(usuario));
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Parâmetros inválidos para criar usuário");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        String idParam = req.getParameter("id");

        if (idParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Informe o parâmetro id");
            return;
        }

        int id = Integer.parseInt(idParam);
        String body = readBody(req);
        Usuario usuario = parseUsuario(body);
        usuario.setId(id);

        if (usuario.getNome() != null && usuario.getEmail() != null) {
            boolean atualizado = usuarioController.atualizarUsuario(usuario);
            if (atualizado) {
                resp.getWriter().println("Usuário atualizado com sucesso!");
            } else {
                resp.getWriter().println("Falha ao atualizar o usuário.");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Parâmetros inválidos para atualizar usuário");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        String idParam = req.getParameter("id");

        if (idParam != null) {
            int id = Integer.parseInt(idParam);
            boolean deletado = usuarioController.deletarUsuario(id);
            if (deletado) {
                resp.getWriter().println("Usuário deletado com sucesso!");
            } else {
                resp.getWriter().println("Usuário não encontrado para exclusão.");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Informe o parâmetro id para deletar usuário");
        }
    }

    private String readBody(HttpServletRequest req) throws IOException {
        try (BufferedReader reader = req.getReader()) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }

    private Usuario parseUsuario(String body) {
        // Exemplo de entrada: "nome=João;email=joao@mail.com"
        Usuario usuario = new Usuario();
        String[] campos = body.split(";");
        for (String campo : campos) {
            String[] par = campo.split("=");
            if (par.length == 2) {
                if (par[0].trim().equalsIgnoreCase("nome")) {
                    usuario.setNome(par[1].trim());
                } else if (par[0].trim().equalsIgnoreCase("email")) {
                    usuario.setEmail(par[1].trim());
                }
            }
        }
        return usuario;
    }
}
