package com.example.Service;

import java.util.ArrayList;

import com.example.Anotacoes.MyService;
import com.example.Entidade.Usuario;
import com.example.Repository.UsuarioRepositorio;

@MyService
public class UsuarioServico {
    private final UsuarioRepositorio repositorio;

    public UsuarioServico(UsuarioRepositorio repositorio){
        this.repositorio = repositorio;
    }

    public void Create(Usuario usuario){
        repositorio.Create(usuario);
    }

    public boolean Delete(int id){
        return repositorio.Delete(id);
    }

    public ArrayList<Usuario >ReadAll(){
        return repositorio.ReadAll();
    }

    public boolean Update(Usuario usuario){
        return repositorio.Update(usuario);
    }

    public Usuario ReadId(int id){
        return repositorio.readID(id);
    }

}
