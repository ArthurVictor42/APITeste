package com.example.Controller;

import com.example.Anotacoes.MyControllers;
import com.example.Entidade.Usuario;
import com.example.Service.UsuarioServico;

import java.util.List;

@MyControllers
public class UsuarioController {
    private final UsuarioServico service;

    public UsuarioController(UsuarioServico service) {
        this.service = service;
    }

    public String createUsuario(Usuario usuario) {
        service.Create(usuario);
        return "Usuário cadastrado com sucesso!";
    }

    public List<Usuario> listarUsuarios() {
        return service.ReadAll();
    }

    public Usuario buscarUsuario(int id) {
        return service.ReadId(id);
    }

    public boolean atualizarUsuario(Usuario usuario) {
        return service.Update(usuario);
    }

    public boolean deletarUsuario(int id) {
        return service.Delete(id);
    }

    public UsuarioServico getService() {
        return service;
    }
}