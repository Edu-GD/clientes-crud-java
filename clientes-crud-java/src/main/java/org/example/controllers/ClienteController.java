package org.example.controllers;

import jakarta.persistence.EntityManager;
import org.example.entities.Cliente;
import org.example.persistence.ConfigJPA;

import java.util.List;

public class ClienteController {

    // Metodo para agregar un nuevo cliente
    public void agregarNuevoCliente(Cliente cliente) {
        EntityManager em = ConfigJPA.getEntityManager(); //Llamamos al EntityManager para poder mandar información al database
        em.getTransaction().begin(); //Envía la información
        em.persist(cliente); //Guarda en el database
        em.getTransaction().commit(); //Confirmación
        System.out.println("✅ Cliente guardado correctamente ✅");
        em.close();
    }

    // Metodo para agregar listar los clientes
    public List<Cliente> listarClientes() {
        EntityManager em = ConfigJPA.getEntityManager();
        List<Cliente> clientes = em.createQuery("FROM Cliente", Cliente.class).getResultList();
        em.close(); // Cerramos manualmente el EntityManager
        return clientes;
    }

    // Metodo para actualizar un cliente
    public void actualizarCliente(Cliente cliente) {
        EntityManager em = ConfigJPA.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(cliente);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("⚠\uFE0F Error al actualizar el cliente " + e.getMessage() + "⚠\uFE0F");
        } finally {
            em.close();
        }
    }

    // Metodo para buscar un cliente por por Id (para usarlo en actualizar cliente)
    public Cliente buscarPorId(long id) {
        EntityManager em = ConfigJPA.getEntityManager();
        Cliente cliente = null;
        try {
            cliente = em.find(Cliente.class, id);
        } catch (Exception e) {
            System.out.println("⚠\uFE0F Error al buscar el cliente: " + e.getMessage() + " ⚠\uFE0F");
        } finally {
            em.close();
        }
        return cliente;
    }

    // Metodo para buscar clientes de ''x'' ciudad
    public List<Cliente> buscarClientePorCiudad(String ciudad) {
        EntityManager em = ConfigJPA.getEntityManager();
        List<Cliente> clientes;
        try {
            clientes = em.createQuery("FROM Cliente c WHERE c.ciudad = :ciudad", Cliente.class).setParameter("ciudad", ciudad).getResultList();
        } finally {
            em.close();
        }
        return clientes;
    }

    //Metodo para eliminar un cliente
    public void eliminarCliente(long id) {
        EntityManager em = ConfigJPA.getEntityManager();
        try {
            Cliente c = em.find(Cliente.class, id);
            if (c != null) {
                em.getTransaction().begin();
                em.remove(c);
                em.getTransaction().commit();
                System.out.println("✅ Cliente eliminado correctamente ✅");
                System.out.println("");
            } else {
                System.out.println("⚠\uFE0F Cliente no encontrado ⚠\uFE0F");
                System.out.println("");
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("⚠\uFE0F Error al eliminar el cliente: " + e.getMessage() + " ⚠\uFE0F");
            System.out.println("");
        } finally {
            em.close();
        }
    }
}