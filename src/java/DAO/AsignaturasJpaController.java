/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DTO.Asignaturas;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Cursos;
import java.util.ArrayList;
import java.util.Collection;
import DTO.AsignaturasUsuarios;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lmfde
 */
public class AsignaturasJpaController implements Serializable {

    public AsignaturasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Asignaturas asignaturas) {
        if (asignaturas.getCursosCollection() == null) {
            asignaturas.setCursosCollection(new ArrayList<Cursos>());
        }
        if (asignaturas.getAsignaturasUsuariosCollection() == null) {
            asignaturas.setAsignaturasUsuariosCollection(new ArrayList<AsignaturasUsuarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Cursos> attachedCursosCollection = new ArrayList<Cursos>();
            for (Cursos cursosCollectionCursosToAttach : asignaturas.getCursosCollection()) {
                cursosCollectionCursosToAttach = em.getReference(cursosCollectionCursosToAttach.getClass(), cursosCollectionCursosToAttach.getCodCursos());
                attachedCursosCollection.add(cursosCollectionCursosToAttach);
            }
            asignaturas.setCursosCollection(attachedCursosCollection);
            Collection<AsignaturasUsuarios> attachedAsignaturasUsuariosCollection = new ArrayList<AsignaturasUsuarios>();
            for (AsignaturasUsuarios asignaturasUsuariosCollectionAsignaturasUsuariosToAttach : asignaturas.getAsignaturasUsuariosCollection()) {
                asignaturasUsuariosCollectionAsignaturasUsuariosToAttach = em.getReference(asignaturasUsuariosCollectionAsignaturasUsuariosToAttach.getClass(), asignaturasUsuariosCollectionAsignaturasUsuariosToAttach.getId());
                attachedAsignaturasUsuariosCollection.add(asignaturasUsuariosCollectionAsignaturasUsuariosToAttach);
            }
            asignaturas.setAsignaturasUsuariosCollection(attachedAsignaturasUsuariosCollection);
            em.persist(asignaturas);
            for (Cursos cursosCollectionCursos : asignaturas.getCursosCollection()) {
                cursosCollectionCursos.getAsignaturasCollection().add(asignaturas);
                cursosCollectionCursos = em.merge(cursosCollectionCursos);
            }
            for (AsignaturasUsuarios asignaturasUsuariosCollectionAsignaturasUsuarios : asignaturas.getAsignaturasUsuariosCollection()) {
                Asignaturas oldCodAsignaturaOfAsignaturasUsuariosCollectionAsignaturasUsuarios = asignaturasUsuariosCollectionAsignaturasUsuarios.getCodAsignatura();
                asignaturasUsuariosCollectionAsignaturasUsuarios.setCodAsignatura(asignaturas);
                asignaturasUsuariosCollectionAsignaturasUsuarios = em.merge(asignaturasUsuariosCollectionAsignaturasUsuarios);
                if (oldCodAsignaturaOfAsignaturasUsuariosCollectionAsignaturasUsuarios != null) {
                    oldCodAsignaturaOfAsignaturasUsuariosCollectionAsignaturasUsuarios.getAsignaturasUsuariosCollection().remove(asignaturasUsuariosCollectionAsignaturasUsuarios);
                    oldCodAsignaturaOfAsignaturasUsuariosCollectionAsignaturasUsuarios = em.merge(oldCodAsignaturaOfAsignaturasUsuariosCollectionAsignaturasUsuarios);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Asignaturas asignaturas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asignaturas persistentAsignaturas = em.find(Asignaturas.class, asignaturas.getCodAsignatura());
            Collection<Cursos> cursosCollectionOld = persistentAsignaturas.getCursosCollection();
            Collection<Cursos> cursosCollectionNew = asignaturas.getCursosCollection();
            Collection<AsignaturasUsuarios> asignaturasUsuariosCollectionOld = persistentAsignaturas.getAsignaturasUsuariosCollection();
            Collection<AsignaturasUsuarios> asignaturasUsuariosCollectionNew = asignaturas.getAsignaturasUsuariosCollection();
            List<String> illegalOrphanMessages = null;
            for (AsignaturasUsuarios asignaturasUsuariosCollectionOldAsignaturasUsuarios : asignaturasUsuariosCollectionOld) {
                if (!asignaturasUsuariosCollectionNew.contains(asignaturasUsuariosCollectionOldAsignaturasUsuarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AsignaturasUsuarios " + asignaturasUsuariosCollectionOldAsignaturasUsuarios + " since its codAsignatura field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Cursos> attachedCursosCollectionNew = new ArrayList<Cursos>();
            for (Cursos cursosCollectionNewCursosToAttach : cursosCollectionNew) {
                cursosCollectionNewCursosToAttach = em.getReference(cursosCollectionNewCursosToAttach.getClass(), cursosCollectionNewCursosToAttach.getCodCursos());
                attachedCursosCollectionNew.add(cursosCollectionNewCursosToAttach);
            }
            cursosCollectionNew = attachedCursosCollectionNew;
            asignaturas.setCursosCollection(cursosCollectionNew);
            Collection<AsignaturasUsuarios> attachedAsignaturasUsuariosCollectionNew = new ArrayList<AsignaturasUsuarios>();
            for (AsignaturasUsuarios asignaturasUsuariosCollectionNewAsignaturasUsuariosToAttach : asignaturasUsuariosCollectionNew) {
                asignaturasUsuariosCollectionNewAsignaturasUsuariosToAttach = em.getReference(asignaturasUsuariosCollectionNewAsignaturasUsuariosToAttach.getClass(), asignaturasUsuariosCollectionNewAsignaturasUsuariosToAttach.getId());
                attachedAsignaturasUsuariosCollectionNew.add(asignaturasUsuariosCollectionNewAsignaturasUsuariosToAttach);
            }
            asignaturasUsuariosCollectionNew = attachedAsignaturasUsuariosCollectionNew;
            asignaturas.setAsignaturasUsuariosCollection(asignaturasUsuariosCollectionNew);
            asignaturas = em.merge(asignaturas);
            for (Cursos cursosCollectionOldCursos : cursosCollectionOld) {
                if (!cursosCollectionNew.contains(cursosCollectionOldCursos)) {
                    cursosCollectionOldCursos.getAsignaturasCollection().remove(asignaturas);
                    cursosCollectionOldCursos = em.merge(cursosCollectionOldCursos);
                }
            }
            for (Cursos cursosCollectionNewCursos : cursosCollectionNew) {
                if (!cursosCollectionOld.contains(cursosCollectionNewCursos)) {
                    cursosCollectionNewCursos.getAsignaturasCollection().add(asignaturas);
                    cursosCollectionNewCursos = em.merge(cursosCollectionNewCursos);
                }
            }
            for (AsignaturasUsuarios asignaturasUsuariosCollectionNewAsignaturasUsuarios : asignaturasUsuariosCollectionNew) {
                if (!asignaturasUsuariosCollectionOld.contains(asignaturasUsuariosCollectionNewAsignaturasUsuarios)) {
                    Asignaturas oldCodAsignaturaOfAsignaturasUsuariosCollectionNewAsignaturasUsuarios = asignaturasUsuariosCollectionNewAsignaturasUsuarios.getCodAsignatura();
                    asignaturasUsuariosCollectionNewAsignaturasUsuarios.setCodAsignatura(asignaturas);
                    asignaturasUsuariosCollectionNewAsignaturasUsuarios = em.merge(asignaturasUsuariosCollectionNewAsignaturasUsuarios);
                    if (oldCodAsignaturaOfAsignaturasUsuariosCollectionNewAsignaturasUsuarios != null && !oldCodAsignaturaOfAsignaturasUsuariosCollectionNewAsignaturasUsuarios.equals(asignaturas)) {
                        oldCodAsignaturaOfAsignaturasUsuariosCollectionNewAsignaturasUsuarios.getAsignaturasUsuariosCollection().remove(asignaturasUsuariosCollectionNewAsignaturasUsuarios);
                        oldCodAsignaturaOfAsignaturasUsuariosCollectionNewAsignaturasUsuarios = em.merge(oldCodAsignaturaOfAsignaturasUsuariosCollectionNewAsignaturasUsuarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = asignaturas.getCodAsignatura();
                if (findAsignaturas(id) == null) {
                    throw new NonexistentEntityException("The asignaturas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asignaturas asignaturas;
            try {
                asignaturas = em.getReference(Asignaturas.class, id);
                asignaturas.getCodAsignatura();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asignaturas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<AsignaturasUsuarios> asignaturasUsuariosCollectionOrphanCheck = asignaturas.getAsignaturasUsuariosCollection();
            for (AsignaturasUsuarios asignaturasUsuariosCollectionOrphanCheckAsignaturasUsuarios : asignaturasUsuariosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Asignaturas (" + asignaturas + ") cannot be destroyed since the AsignaturasUsuarios " + asignaturasUsuariosCollectionOrphanCheckAsignaturasUsuarios + " in its asignaturasUsuariosCollection field has a non-nullable codAsignatura field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Cursos> cursosCollection = asignaturas.getCursosCollection();
            for (Cursos cursosCollectionCursos : cursosCollection) {
                cursosCollectionCursos.getAsignaturasCollection().remove(asignaturas);
                cursosCollectionCursos = em.merge(cursosCollectionCursos);
            }
            em.remove(asignaturas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Asignaturas> findAsignaturasEntities() {
        return findAsignaturasEntities(true, -1, -1);
    }

    public List<Asignaturas> findAsignaturasEntities(int maxResults, int firstResult) {
        return findAsignaturasEntities(false, maxResults, firstResult);
    }

    private List<Asignaturas> findAsignaturasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Asignaturas.class));
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

    public Asignaturas findAsignaturas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Asignaturas.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsignaturasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Asignaturas> rt = cq.from(Asignaturas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
