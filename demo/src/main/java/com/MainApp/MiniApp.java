package com.MainApp;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.example.Controller.UsuarioController;
import com.example.IOC.MyAplication;
import com.example.Repository.UsuarioRepositorio;
import com.example.Service.UsuarioServico;
import com.example.Servlet.DispatcherServlet;

public class MiniApp {
    public static void main(String[] args) throws Exception {

        // Iniciando o container
        MyAplication context = new MyAplication(
                UsuarioRepositorio.class,
                UsuarioServico.class,
                UsuarioController.class);

        // Coleta o controller
        UsuarioController controller = context.getBean(UsuarioController.class);

        // Cria o servidor
        Server server = new Server(8080);
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");
        contextHandler.addServlet(new ServletHolder(new DispatcherServlet(controller)), "/*");

        server.setHandler(contextHandler);
        server.start();
        server.join();

    }
}
