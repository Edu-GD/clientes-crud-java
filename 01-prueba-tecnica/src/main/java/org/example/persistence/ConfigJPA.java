package org.example.persistence;

import jakarta.persistence.*;

public class ConfigJPA {

    //Creo la fábrica de EntityManagers
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("clientesPU");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void close() {
        emf.close();
    }
}