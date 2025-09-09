package com.example.Controller;

import com.example.Anotacoes.MyControllers;
import com.example.Entidade.Usuario;
import com.example.Service.UsuarioServico;

@MyControllers
public class UsuarioController {
    private final UsuarioServico service;

    public UsuarioController(UsuarioServico service) {
        this.service = service;
    }

    public String createUsuario(Usuario usuario){
        service.Create(usuario);

        return "Usuario cadastrado com sucesso!";
 
    }
    public String listarUsuarios() {
        StringBuilder sb = new StringBuilder();
        for (Usuario u : service.ReadAll()) {
            sb.append(u.getId()).append(" - ").append(u.getNome()).append("\n");
        }
        return sb.toString();
    }

    public String buscarUsuario(int id) {
        Usuario u = service.ReadId(id);
        return u != null ? u.getNome() : "Usuário não encontrado";
    }
}
