package org.example.persistence;

import jakarta.persistence.*;

public class ConfigJPA {

    //Creo la fábrica de EntityManagers
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("clientesPU");

    //Creo el EntityManager que será el que se encargará de crear, leer, actualizar clientes
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //Cierro la EntityManagerFactory
    public static void close() {
        emf.close();
    }
}