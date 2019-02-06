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
import DTO.Cursos;
import java.util.ArrayList;
import java.util.List;
import DTO.Asignaturas;
import DTO.Articulos;
import DTO.Usuarios;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author lmfde
 */
public class UsuariosJpaController implements Serializable {

    public UsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarios usuarios) {
        if (usuarios.getCursosList() == null) {
            usuarios.setCursosList(new ArrayList<Cursos>());
        }
        if (usuarios.getAsignaturasList() == null) {
            usuarios.setAsignaturasList(new ArrayList<Asignaturas>());
        }
        if (usuarios.getArticulosList() == null) {
            usuarios.setArticulosList(new ArrayList<Articulos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cursos> attachedCursosList = new ArrayList<Cursos>();
            for (Cursos cursosListCursosToAttach : usuarios.getCursosList()) {
                cursosListCursosToAttach = em.getReference(cursosListCursosToAttach.getClass(), cursosListCursosToAttach.getCodCursos());
                attachedCursosList.add(cursosListCursosToAttach);
            }
            usuarios.setCursosList(attachedCursosList);
            List<Asignaturas> attachedAsignaturasList = new ArrayList<Asignaturas>();
            for (Asignaturas asignaturasListAsignaturasToAttach : usuarios.getAsignaturasList()) {
                asignaturasListAsignaturasToAttach = em.getReference(asignaturasListAsignaturasToAttach.getClass(), asignaturasListAsignaturasToAttach.getCodAsignatura());
                attachedAsignaturasList.add(asignaturasListAsignaturasToAttach);
            }
            usuarios.setAsignaturasList(attachedAsignaturasList);
            List<Articulos> attachedArticulosList = new ArrayList<Articulos>();
            for (Articulos articulosListArticulosToAttach : usuarios.getArticulosList()) {
                articulosListArticulosToAttach = em.getReference(articulosListArticulosToAttach.getClass(), articulosListArticulosToAttach.getCodArticulo());
                attachedArticulosList.add(articulosListArticulosToAttach);
            }
            usuarios.setArticulosList(attachedArticulosList);
            em.persist(usuarios);
            for (Cursos cursosListCursos : usuarios.getCursosList()) {
                cursosListCursos.getUsuariosList().add(usuarios);
                cursosListCursos = em.merge(cursosListCursos);
            }
            for (Asignaturas asignaturasListAsignaturas : usuarios.getAsignaturasList()) {
                asignaturasListAsignaturas.getUsuariosList().add(usuarios);
                asignaturasListAsignaturas = em.merge(asignaturasListAsignaturas);
            }
            for (Articulos articulosListArticulos : usuarios.getArticulosList()) {
                Usuarios oldPropietarioOfArticulosListArticulos = articulosListArticulos.getPropietario();
                articulosListArticulos.setPropietario(usuarios);
                articulosListArticulos = em.merge(articulosListArticulos);
                if (oldPropietarioOfArticulosListArticulos != null) {
                    oldPropietarioOfArticulosListArticulos.getArticulosList().remove(articulosListArticulos);
                    oldPropietarioOfArticulosListArticulos = em.merge(oldPropietarioOfArticulosListArticulos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getCodUsuario());
            List<Cursos> cursosListOld = persistentUsuarios.getCursosList();
            List<Cursos> cursosListNew = usuarios.getCursosList();
            List<Asignaturas> asignaturasListOld = persistentUsuarios.getAsignaturasList();
            List<Asignaturas> asignaturasListNew = usuarios.getAsignaturasList();
            List<Articulos> articulosListOld = persistentUsuarios.getArticulosList();
            List<Articulos> articulosListNew = usuarios.getArticulosList();
            List<String> illegalOrphanMessages = null;
            for (Articulos articulosListOldArticulos : articulosListOld) {
                if (!articulosListNew.contains(articulosListOldArticulos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Articulos " + articulosListOldArticulos + " since its propietario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Cursos> attachedCursosListNew = new ArrayList<Cursos>();
            for (Cursos cursosListNewCursosToAttach : cursosListNew) {
                cursosListNewCursosToAttach = em.getReference(cursosListNewCursosToAttach.getClass(), cursosListNewCursosToAttach.getCodCursos());
                attachedCursosListNew.add(cursosListNewCursosToAttach);
            }
            cursosListNew = attachedCursosListNew;
            usuarios.setCursosList(cursosListNew);
            List<Asignaturas> attachedAsignaturasListNew = new ArrayList<Asignaturas>();
            for (Asignaturas asignaturasListNewAsignaturasToAttach : asignaturasListNew) {
                asignaturasListNewAsignaturasToAttach = em.getReference(asignaturasListNewAsignaturasToAttach.getClass(), asignaturasListNewAsignaturasToAttach.getCodAsignatura());
                attachedAsignaturasListNew.add(asignaturasListNewAsignaturasToAttach);
            }
            asignaturasListNew = attachedAsignaturasListNew;
            usuarios.setAsignaturasList(asignaturasListNew);
            List<Articulos> attachedArticulosListNew = new ArrayList<Articulos>();
            for (Articulos articulosListNewArticulosToAttach : articulosListNew) {
                articulosListNewArticulosToAttach = em.getReference(articulosListNewArticulosToAttach.getClass(), articulosListNewArticulosToAttach.getCodArticulo());
                attachedArticulosListNew.add(articulosListNewArticulosToAttach);
            }
            articulosListNew = attachedArticulosListNew;
            usuarios.setArticulosList(articulosListNew);
            usuarios = em.merge(usuarios);
            for (Cursos cursosListOldCursos : cursosListOld) {
                if (!cursosListNew.contains(cursosListOldCursos)) {
                    cursosListOldCursos.getUsuariosList().remove(usuarios);
                    cursosListOldCursos = em.merge(cursosListOldCursos);
                }
            }
            for (Cursos cursosListNewCursos : cursosListNew) {
                if (!cursosListOld.contains(cursosListNewCursos)) {
                    cursosListNewCursos.getUsuariosList().add(usuarios);
                    cursosListNewCursos = em.merge(cursosListNewCursos);
                }
            }
            for (Asignaturas asignaturasListOldAsignaturas : asignaturasListOld) {
                if (!asignaturasListNew.contains(asignaturasListOldAsignaturas)) {
                    asignaturasListOldAsignaturas.getUsuariosList().remove(usuarios);
                    asignaturasListOldAsignaturas = em.merge(asignaturasListOldAsignaturas);
                }
            }
            for (Asignaturas asignaturasListNewAsignaturas : asignaturasListNew) {
                if (!asignaturasListOld.contains(asignaturasListNewAsignaturas)) {
                    asignaturasListNewAsignaturas.getUsuariosList().add(usuarios);
                    asignaturasListNewAsignaturas = em.merge(asignaturasListNewAsignaturas);
                }
            }
            for (Articulos articulosListNewArticulos : articulosListNew) {
                if (!articulosListOld.contains(articulosListNewArticulos)) {
                    Usuarios oldPropietarioOfArticulosListNewArticulos = articulosListNewArticulos.getPropietario();
                    articulosListNewArticulos.setPropietario(usuarios);
                    articulosListNewArticulos = em.merge(articulosListNewArticulos);
                    if (oldPropietarioOfArticulosListNewArticulos != null && !oldPropietarioOfArticulosListNewArticulos.equals(usuarios)) {
                        oldPropietarioOfArticulosListNewArticulos.getArticulosList().remove(articulosListNewArticulos);
                        oldPropietarioOfArticulosListNewArticulos = em.merge(oldPropietarioOfArticulosListNewArticulos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuarios.getCodUsuario();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
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
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getCodUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Articulos> articulosListOrphanCheck = usuarios.getArticulosList();
            for (Articulos articulosListOrphanCheckArticulos : articulosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the Articulos " + articulosListOrphanCheckArticulos + " in its articulosList field has a non-nullable propietario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Cursos> cursosList = usuarios.getCursosList();
            for (Cursos cursosListCursos : cursosList) {
                cursosListCursos.getUsuariosList().remove(usuarios);
                cursosListCursos = em.merge(cursosListCursos);
            }
            List<Asignaturas> asignaturasList = usuarios.getAsignaturasList();
            for (Asignaturas asignaturasListAsignaturas : asignaturasList) {
                asignaturasListAsignaturas.getUsuariosList().remove(usuarios);
                asignaturasListAsignaturas = em.merge(asignaturasListAsignaturas);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public Usuarios findUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List usuarioPorEmail(String valor) {
        
        EntityManager em = getEntityManager();
        TypedQuery<Usuarios> consulta = em.createNamedQuery("Usuarios.findByEmail", Usuarios.class);
        consulta.setParameter("email", valor);
        return consulta.getResultList();

    }
    
}
