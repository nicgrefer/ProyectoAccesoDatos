package com.sede.dao;

import com.sede.model.Entidad;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Entidad
 * Proporciona métodos para acceder a la tabla entidad en la base de datos
 */
public class EntidadDAO {
    
    /**
     * Obtiene todas las entidades de la base de datos
     * @return Lista de entidades
     */
    public List<Entidad> listarEntidades() {
        List<Entidad> entidades = new ArrayList<>();
        Transaction transaction = null;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            // HQL query para obtener todas las entidades ordenadas por nombre
            Query<Entidad> query = session.createQuery(
                "FROM Entidad e ORDER BY e.nombreEntidad", Entidad.class);
            entidades = query.list();
            
            transaction.commit();
            System.out.println("Se obtuvieron " + entidades.size() + " entidades");
            
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al listar entidades: " + e.getMessage());
            e.printStackTrace();
        }
        
        return entidades;
    }
    
    /**
     * Obtiene una entidad por su ID
     * @param id ID de la entidad
     * @return Entidad encontrada o null si no existe
     */
    public Entidad obtenerPorId(int id) {
        Entidad entidad = null;
        Transaction transaction = null;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            entidad = session.get(Entidad.class, id);
            
            transaction.commit();
            
            if (entidad != null) {
                System.out.println("Entidad encontrada: " + entidad.getNombreEntidad());
            } else {
                System.out.println("No se encontró entidad con ID: " + id);
            }
            
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al obtener entidad por ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return entidad;
    }
}
