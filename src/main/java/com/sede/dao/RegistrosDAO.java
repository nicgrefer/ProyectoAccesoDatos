package com.sede.dao;

import com.sede.model.Registros;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;

/**
 * DAO para la entidad Registros
 * Proporciona métodos para guardar y buscar registros de trámites
 */
public class RegistrosDAO {
    
    /**
     * Guarda un nuevo registro en la base de datos
     * @param registro Objeto Registros a guardar
     * @return Número de registro generado
     */
    public String guardarRegistro(Registros registro) {
        Transaction transaction = null;
        String numRegistro = null;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            // Guardar el registro
            session.persist(registro);
            numRegistro = registro.getNumRegistro();
            
            transaction.commit();
            System.out.println("Registro guardado exitosamente: " + numRegistro);
            
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al guardar registro: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al guardar el registro", e);
        }
        
        return numRegistro;
    }
    
    /**
     * Busca un registro por su número de registro
     * @param numRegistro Número de registro a buscar
     * @return Registro encontrado o null si no existe
     */
    public Registros buscarPorNumero(String numRegistro) {
        Registros registro = null;
        Transaction transaction = null;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            // Buscar el registro por su clave primaria
            registro = session.get(Registros.class, numRegistro);
            
            // Si el registro existe, forzar la carga de la entidad relacionada
            if (registro != null) {
                // Esto asegura que la entidad sea cargada antes de cerrar la sesión
                registro.getEntidad().getNombreEntidad();
            }
            
            transaction.commit();
            
            if (registro != null) {
                System.out.println("Registro encontrado: " + numRegistro);
            } else {
                System.out.println("No se encontró registro con número: " + numRegistro);
            }
            
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al buscar registro: " + e.getMessage());
            e.printStackTrace();
        }
        
        return registro;
    }
    
    /**
     * Obtiene el contador de registros para generar el siguiente número
     * @return Número de registros en la base de datos
     */
    public long obtenerContador() {
        long contador = 0;
        Transaction transaction = null;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            // HQL query para contar registros
            Query<Long> query = session.createQuery(
                "SELECT COUNT(r) FROM Registros r", Long.class);
            contador = query.uniqueResult();
            
            transaction.commit();
            
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al obtener contador: " + e.getMessage());
            e.printStackTrace();
        }
        
        return contador;
    }
}
