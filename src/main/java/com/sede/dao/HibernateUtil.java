package com.sede.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;

/**
 * Clase utilitaria para gestionar la SessionFactory de Hibernate
 * Implementa el patrón Singleton para asegurar una única instancia
 */
public class HibernateUtil {
    
    private static SessionFactory sessionFactory;
    
    static {
        try {
            // Crear la SessionFactory desde hibernate.cfg.xml
            sessionFactory = new Configuration().configure().buildSessionFactory();
            System.out.println("SessionFactory creada exitosamente");
        } catch (HibernateException ex) {
            System.err.println("Error al crear SessionFactory: " + ex.getMessage());
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    /**
     * Obtiene la instancia única de SessionFactory
     * @return SessionFactory configurada
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    /**
     * Cierra la SessionFactory y libera los recursos
     */
    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            try {
                sessionFactory.close();
                System.out.println("SessionFactory cerrada correctamente");
            } catch (HibernateException e) {
                System.err.println("Error al cerrar SessionFactory: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
