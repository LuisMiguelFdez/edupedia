/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Asignaturas;
import DTO.AsignaturasUsuarios;
import DTO.Usuarios;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lmfde
 */
public class AsignaturasUsuariosJpaController implements Serializable {

    public AsignaturasUsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AsignaturasUsuarios asignaturasUsuarios) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asignaturas codAsignatura = asignaturasUsuarios.getCodAsignatura();
            if (codAsignatura != null) {
                codAsignatura = em.getReference(codAsignatura.getClass(), codAsignatura.getCodAsignatura());
                asignaturasUsuarios.setCodAsignatura(codAsignatura);
            }
            Usuarios codUsuario = asignaturasUsuarios.getCodUsuario();
            if (codUsuario != null) {
                codUsuario = em.getReference(codUsuario.getClass(), codUsuario.getCodUsuario());
                asignaturasUsuarios.setCodUsuario(codUsuario);
            }
            em.persist(asignaturasUsuarios);
            if (codAsignatura != null) {
                codAsignatura.getAsignaturasUsuariosCollection().add(asignaturasUsuarios);
                codAsignatura = em.merge(codAsignatura);
            }
            if (codUsuario != null) {
                codUsuario.getAsignaturasUsuariosCollection().add(asignaturasUsuarios);
                codUsuario = em.merge(codUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AsignaturasUsuarios asignaturasUsuarios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AsignaturasUsuarios persistentAsignaturasUsuarios = em.find(AsignaturasUsuarios.class, asignaturasUsuarios.getId());
            Asignaturas codAsignaturaOld = persistentAsignaturasUsuarios.getCodAsignatura();
            Asignaturas codAsignaturaNew = asignaturasUsuarios.getCodAsignatura();
            Usuarios codUsuarioOld = persistentAsignaturasUsuarios.getCodUsuario();
            Usuarios codUsuarioNew = asignaturasUsuarios.getCodUsuario();
            if (codAsignaturaNew != null) {
                codAsignaturaNew = em.getReference(codAsignaturaNew.getClass(), codAsignaturaNew.getCodAsignatura());
                asignaturasUsuarios.setCodAsignatura(codAsignaturaNew);
            }
            if (codUsuarioNew != null) {
                codUsuarioNew = em.getReference(codUsuarioNew.getClass(), codUsuarioNew.getCodUsuario());
                asignaturasUsuarios.setCodUsuario(codUsuarioNew);
            }
            asignaturasUsuarios = em.merge(asignaturasUsuarios);
            if (codAsignaturaOld != null && !codAsignaturaOld.equals(codAsignaturaNew)) {
                codAsignaturaOld.getAsignaturasUsuariosCollection().remove(asignaturasUsuarios);
                codAsignaturaOld = em.merge(codAsignaturaOld);
            }
            if (codAsignaturaNew != null && !codAsignaturaNew.equals(codAsignaturaOld)) {
                codAsignaturaNew.getAsignaturasUsuariosCollection().add(asignaturasUsuarios);
                codAsignaturaNew = em.merge(codAsignaturaNew);
            }
            if (codUsuarioOld != null && !codUsuarioOld.equals(codUsuarioNew)) {
                codUsuarioOld.getAsignaturasUsuariosCollection().remove(asignaturasUsuarios);
                codUsuarioOld = em.merge(codUsuarioOld);
            }
            if (codUsuarioNew != null && !codUsuarioNew.equals(codUsuarioOld)) {
                codUsuarioNew.getAsignaturasUsuariosCollection().add(asignaturasUsuarios);
                codUsuarioNew = em.merge(codUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = asignaturasUsuarios.getId();
                if (findAsignaturasUsuarios(id) == null) {
                    throw new NonexistentEntityException("The asignaturasUsuarios with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AsignaturasUsuarios asignaturasUsuarios;
            try {
                asignaturasUsuarios = em.getReference(AsignaturasUsuarios.class, id);
                asignaturasUsuarios.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asignaturasUsuarios with id " + id + " no longer exists.", enfe);
            }
            Asignaturas codAsignatura = asignaturasUsuarios.getCodAsignatura();
            if (codAsignatura != null) {
                codAsignatura.getAsignaturasUsuariosCollection().remove(asignaturasUsuarios);
                codAsignatura = em.merge(codAsignatura);
            }
            Usuarios codUsuario = asignaturasUsuarios.getCodUsuario();
            if (codUsuario != null) {
                codUsuario.getAsignaturasUsuariosCollection().remove(asignaturasUsuarios);
                codUsuario = em.merge(codUsuario);
            }
            em.remove(asignaturasUsuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AsignaturasUsuarios> findAsignaturasUsuariosEntities() {
        return findAsignaturasUsuariosEntities(true, -1, -1);
    }

    public List<AsignaturasUsuarios> findAsignaturasUsuariosEntities(int maxResults, int firstResult) {
        return findAsignaturasUsuariosEntities(false, maxResults, firstResult);
    }

    private List<AsignaturasUsuarios> findAsignaturasUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AsignaturasUsuarios.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public AsignaturasUsuarios findAsignaturasUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AsignaturasUsuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsignaturasUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AsignaturasUsuarios> rt = cq.from(AsignaturasUsuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
