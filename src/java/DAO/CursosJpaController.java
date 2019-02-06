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
import DTO.Cursos;
import java.util.ArrayList;
import java.util.List;
import DTO.Usuarios;
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
        if (cursos.getAsignaturasList() == null) {
            cursos.setAsignaturasList(new ArrayList<Asignaturas>());
        }
        if (cursos.getUsuariosList() == null) {
            cursos.setUsuariosList(new ArrayList<Usuarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Asignaturas> attachedAsignaturasList = new ArrayList<Asignaturas>();
            for (Asignaturas asignaturasListAsignaturasToAttach : cursos.getAsignaturasList()) {
                asignaturasListAsignaturasToAttach = em.getReference(asignaturasListAsignaturasToAttach.getClass(), asignaturasListAsignaturasToAttach.getCodAsignatura());
                attachedAsignaturasList.add(asignaturasListAsignaturasToAttach);
            }
            cursos.setAsignaturasList(attachedAsignaturasList);
            List<Usuarios> attachedUsuariosList = new ArrayList<Usuarios>();
            for (Usuarios usuariosListUsuariosToAttach : cursos.getUsuariosList()) {
                usuariosListUsuariosToAttach = em.getReference(usuariosListUsuariosToAttach.getClass(), usuariosListUsuariosToAttach.getCodUsuario());
                attachedUsuariosList.add(usuariosListUsuariosToAttach);
            }
            cursos.setUsuariosList(attachedUsuariosList);
            em.persist(cursos);
            for (Asignaturas asignaturasListAsignaturas : cursos.getAsignaturasList()) {
                asignaturasListAsignaturas.getCursosList().add(cursos);
                asignaturasListAsignaturas = em.merge(asignaturasListAsignaturas);
            }
            for (Usuarios usuariosListUsuarios : cursos.getUsuariosList()) {
                usuariosListUsuarios.getCursosList().add(cursos);
                usuariosListUsuarios = em.merge(usuariosListUsuarios);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cursos cursos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cursos persistentCursos = em.find(Cursos.class, cursos.getCodCursos());
            List<Asignaturas> asignaturasListOld = persistentCursos.getAsignaturasList();
            List<Asignaturas> asignaturasListNew = cursos.getAsignaturasList();
            List<Usuarios> usuariosListOld = persistentCursos.getUsuariosList();
            List<Usuarios> usuariosListNew = cursos.getUsuariosList();
            List<Asignaturas> attachedAsignaturasListNew = new ArrayList<Asignaturas>();
            for (Asignaturas asignaturasListNewAsignaturasToAttach : asignaturasListNew) {
                asignaturasListNewAsignaturasToAttach = em.getReference(asignaturasListNewAsignaturasToAttach.getClass(), asignaturasListNewAsignaturasToAttach.getCodAsignatura());
                attachedAsignaturasListNew.add(asignaturasListNewAsignaturasToAttach);
            }
            asignaturasListNew = attachedAsignaturasListNew;
            cursos.setAsignaturasList(asignaturasListNew);
            List<Usuarios> attachedUsuariosListNew = new ArrayList<Usuarios>();
            for (Usuarios usuariosListNewUsuariosToAttach : usuariosListNew) {
                usuariosListNewUsuariosToAttach = em.getReference(usuariosListNewUsuariosToAttach.getClass(), usuariosListNewUsuariosToAttach.getCodUsuario());
                attachedUsuariosListNew.add(usuariosListNewUsuariosToAttach);
            }
            usuariosListNew = attachedUsuariosListNew;
            cursos.setUsuariosList(usuariosListNew);
            cursos = em.merge(cursos);
            for (Asignaturas asignaturasListOldAsignaturas : asignaturasListOld) {
                if (!asignaturasListNew.contains(asignaturasListOldAsignaturas)) {
                    asignaturasListOldAsignaturas.getCursosList().remove(cursos);
                    asignaturasListOldAsignaturas = em.merge(asignaturasListOldAsignaturas);
                }
            }
            for (Asignaturas asignaturasListNewAsignaturas : asignaturasListNew) {
                if (!asignaturasListOld.contains(asignaturasListNewAsignaturas)) {
                    asignaturasListNewAsignaturas.getCursosList().add(cursos);
                    asignaturasListNewAsignaturas = em.merge(asignaturasListNewAsignaturas);
                }
            }
            for (Usuarios usuariosListOldUsuarios : usuariosListOld) {
                if (!usuariosListNew.contains(usuariosListOldUsuarios)) {
                    usuariosListOldUsuarios.getCursosList().remove(cursos);
                    usuariosListOldUsuarios = em.merge(usuariosListOldUsuarios);
                }
            }
            for (Usuarios usuariosListNewUsuarios : usuariosListNew) {
                if (!usuariosListOld.contains(usuariosListNewUsuarios)) {
                    usuariosListNewUsuarios.getCursosList().add(cursos);
                    usuariosListNewUsuarios = em.merge(usuariosListNewUsuarios);
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

    public void destroy(Integer id) throws NonexistentEntityException {
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
            List<Asignaturas> asignaturasList = cursos.getAsignaturasList();
            for (Asignaturas asignaturasListAsignaturas : asignaturasList) {
                asignaturasListAsignaturas.getCursosList().remove(cursos);
                asignaturasListAsignaturas = em.merge(asignaturasListAsignaturas);
            }
            List<Usuarios> usuariosList = cursos.getUsuariosList();
            for (Usuarios usuariosListUsuarios : usuariosList) {
                usuariosListUsuarios.getCursosList().remove(cursos);
                usuariosListUsuarios = em.merge(usuariosListUsuarios);
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
