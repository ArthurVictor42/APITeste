package com.example.Servlet;

import com.example.Controller.UsuarioController;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

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
}
