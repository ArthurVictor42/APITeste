package com.example.IOC;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.example.Anotacoes.MyControllers;
import com.example.Anotacoes.MyService;

public class MyAplication {
    private final Map<Class<?>, Object> beans = new HashMap<>();

    public MyAplication(Class<?>... componentes){
        try {
            for(Class<?> clazz : componentes ){
                if(clazz.isAnnotationPresent(MyService.class) || clazz.isAnnotationPresent(MyControllers.class)){
                    createbeans(clazz);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao inciar conteudo: " + e.getMessage());
        }
    }

    public Object createbeans(Class<?> clazz) throws Exception {
        if(beans.containsKey(clazz)){
            return beans.get(clazz);
        }

        // Usa para injetar a depedencia
        Constructor<?> construtor = clazz.getConstructors()[0];
        Class<?>[] parametros = construtor.getParameterTypes();

        Object[] dependencias = new Object[parametros.length];

        for(int i = 0; i < parametros.length; i++){
            dependencias[i] = createbeans(parametros[i]);
        }

        Object bean = construtor.newInstance(dependencias);

        beans.put(clazz, bean);
        return bean;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz){
        return (T) beans.get(clazz);
    }
}
