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
import DTO.Cursos;
import DTO.CursosUsuarios;
import DTO.Usuarios;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lmfde
 */
public class CursosUsuariosJpaController implements Serializable {

    public CursosUsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CursosUsuarios cursosUsuarios) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cursos codCurso = cursosUsuarios.getCodCurso();
            if (codCurso != null) {
                codCurso = em.getReference(codCurso.getClass(), codCurso.getCodCursos());
                cursosUsuarios.setCodCurso(codCurso);
            }
            Usuarios codUsuario = cursosUsuarios.getCodUsuario();
            if (codUsuario != null) {
                codUsuario = em.getReference(codUsuario.getClass(), codUsuario.getCodUsuario());
                cursosUsuarios.setCodUsuario(codUsuario);
            }
            em.persist(cursosUsuarios);
            if (codCurso != null) {
                codCurso.getCursosUsuariosCollection().add(cursosUsuarios);
                codCurso = em.merge(codCurso);
            }
            if (codUsuario != null) {
                codUsuario.getCursosUsuariosCollection().add(cursosUsuarios);
                codUsuario = em.merge(codUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CursosUsuarios cursosUsuarios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CursosUsuarios persistentCursosUsuarios = em.find(CursosUsuarios.class, cursosUsuarios.getId());
            Cursos codCursoOld = persistentCursosUsuarios.getCodCurso();
            Cursos codCursoNew = cursosUsuarios.getCodCurso();
            Usuarios codUsuarioOld = persistentCursosUsuarios.getCodUsuario();
            Usuarios codUsuarioNew = cursosUsuarios.getCodUsuario();
            if (codCursoNew != null) {
                codCursoNew = em.getReference(codCursoNew.getClass(), codCursoNew.getCodCursos());
                cursosUsuarios.setCodCurso(codCursoNew);
            }
            if (codUsuarioNew != null) {
                codUsuarioNew = em.getReference(codUsuarioNew.getClass(), codUsuarioNew.getCodUsuario());
                cursosUsuarios.setCodUsuario(codUsuarioNew);
            }
            cursosUsuarios = em.merge(cursosUsuarios);
            if (codCursoOld != null && !codCursoOld.equals(codCursoNew)) {
                codCursoOld.getCursosUsuariosCollection().remove(cursosUsuarios);
                codCursoOld = em.merge(codCursoOld);
            }
            if (codCursoNew != null && !codCursoNew.equals(codCursoOld)) {
                codCursoNew.getCursosUsuariosCollection().add(cursosUsuarios);
                codCursoNew = em.merge(codCursoNew);
            }
            if (codUsuarioOld != null && !codUsuarioOld.equals(codUsuarioNew)) {
                codUsuarioOld.getCursosUsuariosCollection().remove(cursosUsuarios);
                codUsuarioOld = em.merge(codUsuarioOld);
            }
            if (codUsuarioNew != null && !codUsuarioNew.equals(codUsuarioOld)) {
                codUsuarioNew.getCursosUsuariosCollection().add(cursosUsuarios);
                codUsuarioNew = em.merge(codUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cursosUsuarios.getId();
                if (findCursosUsuarios(id) == null) {
                    throw new NonexistentEntityException("The cursosUsuarios with id " + id + " no longer exists.");
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
            CursosUsuarios cursosUsuarios;
            try {
                cursosUsuarios = em.getReference(CursosUsuarios.class, id);
                cursosUsuarios.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cursosUsuarios with id " + id + " no longer exists.", enfe);
            }
            Cursos codCurso = cursosUsuarios.getCodCurso();
            if (codCurso != null) {
                codCurso.getCursosUsuariosCollection().remove(cursosUsuarios);
                codCurso = em.merge(codCurso);
            }
            Usuarios codUsuario = cursosUsuarios.getCodUsuario();
            if (codUsuario != null) {
                codUsuario.getCursosUsuariosCollection().remove(cursosUsuarios);
                codUsuario = em.merge(codUsuario);
            }
            em.remove(cursosUsuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CursosUsuarios> findCursosUsuariosEntities() {
        return findCursosUsuariosEntities(true, -1, -1);
    }

    public List<CursosUsuarios> findCursosUsuariosEntities(int maxResults, int firstResult) {
        return findCursosUsuariosEntities(false, maxResults, firstResult);
    }

    private List<CursosUsuarios> findCursosUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CursosUsuarios.class));
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

    public CursosUsuarios findCursosUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CursosUsuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getCursosUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CursosUsuarios> rt = cq.from(CursosUsuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
