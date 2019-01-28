/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Asignaturas;
import DTO.Cursos;
import java.util.ArrayList;
import java.util.Collection;
import DTO.CursosUsuarios;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lmfde
 */
public class CursosJpaController implements Serializable {

    public CursosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cursos cursos) {
        if (cursos.getAsignaturasCollection() == null) {
            cursos.setAsignaturasCollection(new ArrayList<Asignaturas>());
        }
        if (cursos.getCursosUsuariosCollection() == null) {
            cursos.setCursosUsuariosCollection(new ArrayList<CursosUsuarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Asignaturas> attachedAsignaturasCollection = new ArrayList<Asignaturas>();
            for (Asignaturas asignaturasCollectionAsignaturasToAttach : cursos.getAsignaturasCollection()) {
                asignaturasCollectionAsignaturasToAttach = em.getReference(asignaturasCollectionAsignaturasToAttach.getClass(), asignaturasCollectionAsignaturasToAttach.getCodAsignatura());
                attachedAsignaturasCollection.add(asignaturasCollectionAsignaturasToAttach);
            }
            cursos.setAsignaturasCollection(attachedAsignaturasCollection);
            Collection<CursosUsuarios> attachedCursosUsuariosCollection = new ArrayList<CursosUsuarios>();
            for (CursosUsuarios cursosUsuariosCollectionCursosUsuariosToAttach : cursos.getCursosUsuariosCollection()) {
                cursosUsuariosCollectionCursosUsuariosToAttach = em.getReference(cursosUsuariosCollectionCursosUsuariosToAttach.getClass(), cursosUsuariosCollectionCursosUsuariosToAttach.getId());
                attachedCursosUsuariosCollection.add(cursosUsuariosCollectionCursosUsuariosToAttach);
            }
            cursos.setCursosUsuariosCollection(attachedCursosUsuariosCollection);
            em.persist(cursos);
            for (Asignaturas asignaturasCollectionAsignaturas : cursos.getAsignaturasCollection()) {
                asignaturasCollectionAsignaturas.getCursosCollection().add(cursos);
                asignaturasCollectionAsignaturas = em.merge(asignaturasCollectionAsignaturas);
            }
            for (CursosUsuarios cursosUsuariosCollectionCursosUsuarios : cursos.getCursosUsuariosCollection()) {
                Cursos oldCodCursoOfCursosUsuariosCollectionCursosUsuarios = cursosUsuariosCollectionCursosUsuarios.getCodCurso();
                cursosUsuariosCollectionCursosUsuarios.setCodCurso(cursos);
                cursosUsuariosCollectionCursosUsuarios = em.merge(cursosUsuariosCollectionCursosUsuarios);
                if (oldCodCursoOfCursosUsuariosCollectionCursosUsuarios != null) {
                    oldCodCursoOfCursosUsuariosCollectionCursosUsuarios.getCursosUsuariosCollection().remove(cursosUsuariosCollectionCursosUsuarios);
                    oldCodCursoOfCursosUsuariosCollectionCursosUsuarios = em.merge(oldCodCursoOfCursosUsuariosCollectionCursosUsuarios);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cursos cursos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cursos persistentCursos = em.find(Cursos.class, cursos.getCodCursos());
            Collection<Asignaturas> asignaturasCollectionOld = persistentCursos.getAsignaturasCollection();
            Collection<Asignaturas> asignaturasCollectionNew = cursos.getAsignaturasCollection();
            Collection<CursosUsuarios> cursosUsuariosCollectionOld = persistentCursos.getCursosUsuariosCollection();
            Collection<CursosUsuarios> cursosUsuariosCollectionNew = cursos.getCursosUsuariosCollection();
            List<String> illegalOrphanMessages = null;
            for (CursosUsuarios cursosUsuariosCollectionOldCursosUsuarios : cursosUsuariosCollectionOld) {
                if (!cursosUsuariosCollectionNew.contains(cursosUsuariosCollectionOldCursosUsuarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CursosUsuarios " + cursosUsuariosCollectionOldCursosUsuarios + " since its codCurso field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Asignaturas> attachedAsignaturasCollectionNew = new ArrayList<Asignaturas>();
            for (Asignaturas asignaturasCollectionNewAsignaturasToAttach : asignaturasCollectionNew) {
                asignaturasCollectionNewAsignaturasToAttach = em.getReference(asignaturasCollectionNewAsignaturasToAttach.getClass(), asignaturasCollectionNewAsignaturasToAttach.getCodAsignatura());
                attachedAsignaturasCollectionNew.add(asignaturasCollectionNewAsignaturasToAttach);
            }
            asignaturasCollectionNew = attachedAsignaturasCollectionNew;
            cursos.setAsignaturasCollection(asignaturasCollectionNew);
            Collection<CursosUsuarios> attachedCursosUsuariosCollectionNew = new ArrayList<CursosUsuarios>();
            for (CursosUsuarios cursosUsuariosCollectionNewCursosUsuariosToAttach : cursosUsuariosCollectionNew) {
                cursosUsuariosCollectionNewCursosUsuariosToAttach = em.getReference(cursosUsuariosCollectionNewCursosUsuariosToAttach.getClass(), cursosUsuariosCollectionNewCursosUsuariosToAttach.getId());
                attachedCursosUsuariosCollectionNew.add(cursosUsuariosCollectionNewCursosUsuariosToAttach);
            }
            cursosUsuariosCollectionNew = attachedCursosUsuariosCollectionNew;
            cursos.setCursosUsuariosCollection(cursosUsuariosCollectionNew);
            cursos = em.merge(cursos);
            for (Asignaturas asignaturasCollectionOldAsignaturas : asignaturasCollectionOld) {
                if (!asignaturasCollectionNew.contains(asignaturasCollectionOldAsignaturas)) {
                    asignaturasCollectionOldAsignaturas.getCursosCollection().remove(cursos);
                    asignaturasCollectionOldAsignaturas = em.merge(asignaturasCollectionOldAsignaturas);
                }
            }
            for (Asignaturas asignaturasCollectionNewAsignaturas : asignaturasCollectionNew) {
                if (!asignaturasCollectionOld.contains(asignaturasCollectionNewAsignaturas)) {
                    asignaturasCollectionNewAsignaturas.getCursosCollection().add(cursos);
                    asignaturasCollectionNewAsignaturas = em.merge(asignaturasCollectionNewAsignaturas);
                }
            }
            for (CursosUsuarios cursosUsuariosCollectionNewCursosUsuarios : cursosUsuariosCollectionNew) {
                if (!cursosUsuariosCollectionOld.contains(cursosUsuariosCollectionNewCursosUsuarios)) {
                    Cursos oldCodCursoOfCursosUsuariosCollectionNewCursosUsuarios = cursosUsuariosCollectionNewCursosUsuarios.getCodCurso();
                    cursosUsuariosCollectionNewCursosUsuarios.setCodCurso(cursos);
                    cursosUsuariosCollectionNewCursosUsuarios = em.merge(cursosUsuariosCollectionNewCursosUsuarios);
                    if (oldCodCursoOfCursosUsuariosCollectionNewCursosUsuarios != null && !oldCodCursoOfCursosUsuariosCollectionNewCursosUsuarios.equals(cursos)) {
                        oldCodCursoOfCursosUsuariosCollectionNewCursosUsuarios.getCursosUsuariosCollection().remove(cursosUsuariosCollectionNewCursosUsuarios);
                        oldCodCursoOfCursosUsuariosCollectionNewCursosUsuarios = em.merge(oldCodCursoOfCursosUsuariosCollectionNewCursosUsuarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cursos.getCodCursos();
                if (findCursos(id) == null) {
                    throw new NonexistentEntityException("The cursos with id " + id + " no longer exists.");
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
            Cursos cursos;
            try {
                cursos = em.getReference(Cursos.class, id);
                cursos.getCodCursos();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cursos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CursosUsuarios> cursosUsuariosCollectionOrphanCheck = cursos.getCursosUsuariosCollection();
            for (CursosUsuarios cursosUsuariosCollectionOrphanCheckCursosUsuarios : cursosUsuariosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cursos (" + cursos + ") cannot be destroyed since the CursosUsuarios " + cursosUsuariosCollectionOrphanCheckCursosUsuarios + " in its cursosUsuariosCollection field has a non-nullable codCurso field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Asignaturas> asignaturasCollection = cursos.getAsignaturasCollection();
            for (Asignaturas asignaturasCollectionAsignaturas : asignaturasCollection) {
                asignaturasCollectionAsignaturas.getCursosCollection().remove(cursos);
                asignaturasCollectionAsignaturas = em.merge(asignaturasCollectionAsignaturas);
            }
            em.remove(cursos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cursos> findCursosEntities() {
        return findCursosEntities(true, -1, -1);
    }

    public List<Cursos> findCursosEntities(int maxResults, int firstResult) {
        return findCursosEntities(false, maxResults, firstResult);
    }

    private List<Cursos> findCursosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cursos.class));
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

    public Cursos findCursos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cursos.class, id);
        } finally {
            em.close();
        }
    }

    public int getCursosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cursos> rt = cq.from(Cursos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
