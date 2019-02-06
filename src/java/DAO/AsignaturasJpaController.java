/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DTO.Asignaturas;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class AsignaturasJpaController implements Serializable {

    public AsignaturasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Asignaturas asignaturas) {
        if (asignaturas.getCursosList() == null) {
            asignaturas.setCursosList(new ArrayList<Cursos>());
        }
        if (asignaturas.getUsuariosList() == null) {
            asignaturas.setUsuariosList(new ArrayList<Usuarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cursos> attachedCursosList = new ArrayList<Cursos>();
            for (Cursos cursosListCursosToAttach : asignaturas.getCursosList()) {
                cursosListCursosToAttach = em.getReference(cursosListCursosToAttach.getClass(), cursosListCursosToAttach.getCodCursos());
                attachedCursosList.add(cursosListCursosToAttach);
            }
            asignaturas.setCursosList(attachedCursosList);
            List<Usuarios> attachedUsuariosList = new ArrayList<Usuarios>();
            for (Usuarios usuariosListUsuariosToAttach : asignaturas.getUsuariosList()) {
                usuariosListUsuariosToAttach = em.getReference(usuariosListUsuariosToAttach.getClass(), usuariosListUsuariosToAttach.getCodUsuario());
                attachedUsuariosList.add(usuariosListUsuariosToAttach);
            }
            asignaturas.setUsuariosList(attachedUsuariosList);
            em.persist(asignaturas);
            for (Cursos cursosListCursos : asignaturas.getCursosList()) {
                cursosListCursos.getAsignaturasList().add(asignaturas);
                cursosListCursos = em.merge(cursosListCursos);
            }
            for (Usuarios usuariosListUsuarios : asignaturas.getUsuariosList()) {
                usuariosListUsuarios.getAsignaturasList().add(asignaturas);
                usuariosListUsuarios = em.merge(usuariosListUsuarios);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Asignaturas asignaturas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asignaturas persistentAsignaturas = em.find(Asignaturas.class, asignaturas.getCodAsignatura());
            List<Cursos> cursosListOld = persistentAsignaturas.getCursosList();
            List<Cursos> cursosListNew = asignaturas.getCursosList();
            List<Usuarios> usuariosListOld = persistentAsignaturas.getUsuariosList();
            List<Usuarios> usuariosListNew = asignaturas.getUsuariosList();
            List<Cursos> attachedCursosListNew = new ArrayList<Cursos>();
            for (Cursos cursosListNewCursosToAttach : cursosListNew) {
                cursosListNewCursosToAttach = em.getReference(cursosListNewCursosToAttach.getClass(), cursosListNewCursosToAttach.getCodCursos());
                attachedCursosListNew.add(cursosListNewCursosToAttach);
            }
            cursosListNew = attachedCursosListNew;
            asignaturas.setCursosList(cursosListNew);
            List<Usuarios> attachedUsuariosListNew = new ArrayList<Usuarios>();
            for (Usuarios usuariosListNewUsuariosToAttach : usuariosListNew) {
                usuariosListNewUsuariosToAttach = em.getReference(usuariosListNewUsuariosToAttach.getClass(), usuariosListNewUsuariosToAttach.getCodUsuario());
                attachedUsuariosListNew.add(usuariosListNewUsuariosToAttach);
            }
            usuariosListNew = attachedUsuariosListNew;
            asignaturas.setUsuariosList(usuariosListNew);
            asignaturas = em.merge(asignaturas);
            for (Cursos cursosListOldCursos : cursosListOld) {
                if (!cursosListNew.contains(cursosListOldCursos)) {
                    cursosListOldCursos.getAsignaturasList().remove(asignaturas);
                    cursosListOldCursos = em.merge(cursosListOldCursos);
                }
            }
            for (Cursos cursosListNewCursos : cursosListNew) {
                if (!cursosListOld.contains(cursosListNewCursos)) {
                    cursosListNewCursos.getAsignaturasList().add(asignaturas);
                    cursosListNewCursos = em.merge(cursosListNewCursos);
                }
            }
            for (Usuarios usuariosListOldUsuarios : usuariosListOld) {
                if (!usuariosListNew.contains(usuariosListOldUsuarios)) {
                    usuariosListOldUsuarios.getAsignaturasList().remove(asignaturas);
                    usuariosListOldUsuarios = em.merge(usuariosListOldUsuarios);
                }
            }
            for (Usuarios usuariosListNewUsuarios : usuariosListNew) {
                if (!usuariosListOld.contains(usuariosListNewUsuarios)) {
                    usuariosListNewUsuarios.getAsignaturasList().add(asignaturas);
                    usuariosListNewUsuarios = em.merge(usuariosListNewUsuarios);
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

    public void destroy(Integer id) throws NonexistentEntityException {
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
            List<Cursos> cursosList = asignaturas.getCursosList();
            for (Cursos cursosListCursos : cursosList) {
                cursosListCursos.getAsignaturasList().remove(asignaturas);
                cursosListCursos = em.merge(cursosListCursos);
            }
            List<Usuarios> usuariosList = asignaturas.getUsuariosList();
            for (Usuarios usuariosListUsuarios : usuariosList) {
                usuariosListUsuarios.getAsignaturasList().remove(asignaturas);
                usuariosListUsuarios = em.merge(usuariosListUsuarios);
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
