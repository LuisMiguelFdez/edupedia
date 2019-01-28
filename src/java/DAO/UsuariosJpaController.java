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
import DTO.Articulos;
import java.util.ArrayList;
import java.util.Collection;
import DTO.CursosUsuarios;
import DTO.AsignaturasUsuarios;
import DTO.Usuarios;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
        if (usuarios.getArticulosCollection() == null) {
            usuarios.setArticulosCollection(new ArrayList<Articulos>());
        }
        if (usuarios.getCursosUsuariosCollection() == null) {
            usuarios.setCursosUsuariosCollection(new ArrayList<CursosUsuarios>());
        }
        if (usuarios.getAsignaturasUsuariosCollection() == null) {
            usuarios.setAsignaturasUsuariosCollection(new ArrayList<AsignaturasUsuarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Articulos> attachedArticulosCollection = new ArrayList<Articulos>();
            for (Articulos articulosCollectionArticulosToAttach : usuarios.getArticulosCollection()) {
                articulosCollectionArticulosToAttach = em.getReference(articulosCollectionArticulosToAttach.getClass(), articulosCollectionArticulosToAttach.getCodArticulo());
                attachedArticulosCollection.add(articulosCollectionArticulosToAttach);
            }
            usuarios.setArticulosCollection(attachedArticulosCollection);
            Collection<CursosUsuarios> attachedCursosUsuariosCollection = new ArrayList<CursosUsuarios>();
            for (CursosUsuarios cursosUsuariosCollectionCursosUsuariosToAttach : usuarios.getCursosUsuariosCollection()) {
                cursosUsuariosCollectionCursosUsuariosToAttach = em.getReference(cursosUsuariosCollectionCursosUsuariosToAttach.getClass(), cursosUsuariosCollectionCursosUsuariosToAttach.getId());
                attachedCursosUsuariosCollection.add(cursosUsuariosCollectionCursosUsuariosToAttach);
            }
            usuarios.setCursosUsuariosCollection(attachedCursosUsuariosCollection);
            Collection<AsignaturasUsuarios> attachedAsignaturasUsuariosCollection = new ArrayList<AsignaturasUsuarios>();
            for (AsignaturasUsuarios asignaturasUsuariosCollectionAsignaturasUsuariosToAttach : usuarios.getAsignaturasUsuariosCollection()) {
                asignaturasUsuariosCollectionAsignaturasUsuariosToAttach = em.getReference(asignaturasUsuariosCollectionAsignaturasUsuariosToAttach.getClass(), asignaturasUsuariosCollectionAsignaturasUsuariosToAttach.getId());
                attachedAsignaturasUsuariosCollection.add(asignaturasUsuariosCollectionAsignaturasUsuariosToAttach);
            }
            usuarios.setAsignaturasUsuariosCollection(attachedAsignaturasUsuariosCollection);
            em.persist(usuarios);
            for (Articulos articulosCollectionArticulos : usuarios.getArticulosCollection()) {
                Usuarios oldPropietarioOfArticulosCollectionArticulos = articulosCollectionArticulos.getPropietario();
                articulosCollectionArticulos.setPropietario(usuarios);
                articulosCollectionArticulos = em.merge(articulosCollectionArticulos);
                if (oldPropietarioOfArticulosCollectionArticulos != null) {
                    oldPropietarioOfArticulosCollectionArticulos.getArticulosCollection().remove(articulosCollectionArticulos);
                    oldPropietarioOfArticulosCollectionArticulos = em.merge(oldPropietarioOfArticulosCollectionArticulos);
                }
            }
            for (CursosUsuarios cursosUsuariosCollectionCursosUsuarios : usuarios.getCursosUsuariosCollection()) {
                Usuarios oldCodUsuarioOfCursosUsuariosCollectionCursosUsuarios = cursosUsuariosCollectionCursosUsuarios.getCodUsuario();
                cursosUsuariosCollectionCursosUsuarios.setCodUsuario(usuarios);
                cursosUsuariosCollectionCursosUsuarios = em.merge(cursosUsuariosCollectionCursosUsuarios);
                if (oldCodUsuarioOfCursosUsuariosCollectionCursosUsuarios != null) {
                    oldCodUsuarioOfCursosUsuariosCollectionCursosUsuarios.getCursosUsuariosCollection().remove(cursosUsuariosCollectionCursosUsuarios);
                    oldCodUsuarioOfCursosUsuariosCollectionCursosUsuarios = em.merge(oldCodUsuarioOfCursosUsuariosCollectionCursosUsuarios);
                }
            }
            for (AsignaturasUsuarios asignaturasUsuariosCollectionAsignaturasUsuarios : usuarios.getAsignaturasUsuariosCollection()) {
                Usuarios oldCodUsuarioOfAsignaturasUsuariosCollectionAsignaturasUsuarios = asignaturasUsuariosCollectionAsignaturasUsuarios.getCodUsuario();
                asignaturasUsuariosCollectionAsignaturasUsuarios.setCodUsuario(usuarios);
                asignaturasUsuariosCollectionAsignaturasUsuarios = em.merge(asignaturasUsuariosCollectionAsignaturasUsuarios);
                if (oldCodUsuarioOfAsignaturasUsuariosCollectionAsignaturasUsuarios != null) {
                    oldCodUsuarioOfAsignaturasUsuariosCollectionAsignaturasUsuarios.getAsignaturasUsuariosCollection().remove(asignaturasUsuariosCollectionAsignaturasUsuarios);
                    oldCodUsuarioOfAsignaturasUsuariosCollectionAsignaturasUsuarios = em.merge(oldCodUsuarioOfAsignaturasUsuariosCollectionAsignaturasUsuarios);
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
            Collection<Articulos> articulosCollectionOld = persistentUsuarios.getArticulosCollection();
            Collection<Articulos> articulosCollectionNew = usuarios.getArticulosCollection();
            Collection<CursosUsuarios> cursosUsuariosCollectionOld = persistentUsuarios.getCursosUsuariosCollection();
            Collection<CursosUsuarios> cursosUsuariosCollectionNew = usuarios.getCursosUsuariosCollection();
            Collection<AsignaturasUsuarios> asignaturasUsuariosCollectionOld = persistentUsuarios.getAsignaturasUsuariosCollection();
            Collection<AsignaturasUsuarios> asignaturasUsuariosCollectionNew = usuarios.getAsignaturasUsuariosCollection();
            List<String> illegalOrphanMessages = null;
            for (Articulos articulosCollectionOldArticulos : articulosCollectionOld) {
                if (!articulosCollectionNew.contains(articulosCollectionOldArticulos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Articulos " + articulosCollectionOldArticulos + " since its propietario field is not nullable.");
                }
            }
            for (CursosUsuarios cursosUsuariosCollectionOldCursosUsuarios : cursosUsuariosCollectionOld) {
                if (!cursosUsuariosCollectionNew.contains(cursosUsuariosCollectionOldCursosUsuarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CursosUsuarios " + cursosUsuariosCollectionOldCursosUsuarios + " since its codUsuario field is not nullable.");
                }
            }
            for (AsignaturasUsuarios asignaturasUsuariosCollectionOldAsignaturasUsuarios : asignaturasUsuariosCollectionOld) {
                if (!asignaturasUsuariosCollectionNew.contains(asignaturasUsuariosCollectionOldAsignaturasUsuarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AsignaturasUsuarios " + asignaturasUsuariosCollectionOldAsignaturasUsuarios + " since its codUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Articulos> attachedArticulosCollectionNew = new ArrayList<Articulos>();
            for (Articulos articulosCollectionNewArticulosToAttach : articulosCollectionNew) {
                articulosCollectionNewArticulosToAttach = em.getReference(articulosCollectionNewArticulosToAttach.getClass(), articulosCollectionNewArticulosToAttach.getCodArticulo());
                attachedArticulosCollectionNew.add(articulosCollectionNewArticulosToAttach);
            }
            articulosCollectionNew = attachedArticulosCollectionNew;
            usuarios.setArticulosCollection(articulosCollectionNew);
            Collection<CursosUsuarios> attachedCursosUsuariosCollectionNew = new ArrayList<CursosUsuarios>();
            for (CursosUsuarios cursosUsuariosCollectionNewCursosUsuariosToAttach : cursosUsuariosCollectionNew) {
                cursosUsuariosCollectionNewCursosUsuariosToAttach = em.getReference(cursosUsuariosCollectionNewCursosUsuariosToAttach.getClass(), cursosUsuariosCollectionNewCursosUsuariosToAttach.getId());
                attachedCursosUsuariosCollectionNew.add(cursosUsuariosCollectionNewCursosUsuariosToAttach);
            }
            cursosUsuariosCollectionNew = attachedCursosUsuariosCollectionNew;
            usuarios.setCursosUsuariosCollection(cursosUsuariosCollectionNew);
            Collection<AsignaturasUsuarios> attachedAsignaturasUsuariosCollectionNew = new ArrayList<AsignaturasUsuarios>();
            for (AsignaturasUsuarios asignaturasUsuariosCollectionNewAsignaturasUsuariosToAttach : asignaturasUsuariosCollectionNew) {
                asignaturasUsuariosCollectionNewAsignaturasUsuariosToAttach = em.getReference(asignaturasUsuariosCollectionNewAsignaturasUsuariosToAttach.getClass(), asignaturasUsuariosCollectionNewAsignaturasUsuariosToAttach.getId());
                attachedAsignaturasUsuariosCollectionNew.add(asignaturasUsuariosCollectionNewAsignaturasUsuariosToAttach);
            }
            asignaturasUsuariosCollectionNew = attachedAsignaturasUsuariosCollectionNew;
            usuarios.setAsignaturasUsuariosCollection(asignaturasUsuariosCollectionNew);
            usuarios = em.merge(usuarios);
            for (Articulos articulosCollectionNewArticulos : articulosCollectionNew) {
                if (!articulosCollectionOld.contains(articulosCollectionNewArticulos)) {
                    Usuarios oldPropietarioOfArticulosCollectionNewArticulos = articulosCollectionNewArticulos.getPropietario();
                    articulosCollectionNewArticulos.setPropietario(usuarios);
                    articulosCollectionNewArticulos = em.merge(articulosCollectionNewArticulos);
                    if (oldPropietarioOfArticulosCollectionNewArticulos != null && !oldPropietarioOfArticulosCollectionNewArticulos.equals(usuarios)) {
                        oldPropietarioOfArticulosCollectionNewArticulos.getArticulosCollection().remove(articulosCollectionNewArticulos);
                        oldPropietarioOfArticulosCollectionNewArticulos = em.merge(oldPropietarioOfArticulosCollectionNewArticulos);
                    }
                }
            }
            for (CursosUsuarios cursosUsuariosCollectionNewCursosUsuarios : cursosUsuariosCollectionNew) {
                if (!cursosUsuariosCollectionOld.contains(cursosUsuariosCollectionNewCursosUsuarios)) {
                    Usuarios oldCodUsuarioOfCursosUsuariosCollectionNewCursosUsuarios = cursosUsuariosCollectionNewCursosUsuarios.getCodUsuario();
                    cursosUsuariosCollectionNewCursosUsuarios.setCodUsuario(usuarios);
                    cursosUsuariosCollectionNewCursosUsuarios = em.merge(cursosUsuariosCollectionNewCursosUsuarios);
                    if (oldCodUsuarioOfCursosUsuariosCollectionNewCursosUsuarios != null && !oldCodUsuarioOfCursosUsuariosCollectionNewCursosUsuarios.equals(usuarios)) {
                        oldCodUsuarioOfCursosUsuariosCollectionNewCursosUsuarios.getCursosUsuariosCollection().remove(cursosUsuariosCollectionNewCursosUsuarios);
                        oldCodUsuarioOfCursosUsuariosCollectionNewCursosUsuarios = em.merge(oldCodUsuarioOfCursosUsuariosCollectionNewCursosUsuarios);
                    }
                }
            }
            for (AsignaturasUsuarios asignaturasUsuariosCollectionNewAsignaturasUsuarios : asignaturasUsuariosCollectionNew) {
                if (!asignaturasUsuariosCollectionOld.contains(asignaturasUsuariosCollectionNewAsignaturasUsuarios)) {
                    Usuarios oldCodUsuarioOfAsignaturasUsuariosCollectionNewAsignaturasUsuarios = asignaturasUsuariosCollectionNewAsignaturasUsuarios.getCodUsuario();
                    asignaturasUsuariosCollectionNewAsignaturasUsuarios.setCodUsuario(usuarios);
                    asignaturasUsuariosCollectionNewAsignaturasUsuarios = em.merge(asignaturasUsuariosCollectionNewAsignaturasUsuarios);
                    if (oldCodUsuarioOfAsignaturasUsuariosCollectionNewAsignaturasUsuarios != null && !oldCodUsuarioOfAsignaturasUsuariosCollectionNewAsignaturasUsuarios.equals(usuarios)) {
                        oldCodUsuarioOfAsignaturasUsuariosCollectionNewAsignaturasUsuarios.getAsignaturasUsuariosCollection().remove(asignaturasUsuariosCollectionNewAsignaturasUsuarios);
                        oldCodUsuarioOfAsignaturasUsuariosCollectionNewAsignaturasUsuarios = em.merge(oldCodUsuarioOfAsignaturasUsuariosCollectionNewAsignaturasUsuarios);
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
            Collection<Articulos> articulosCollectionOrphanCheck = usuarios.getArticulosCollection();
            for (Articulos articulosCollectionOrphanCheckArticulos : articulosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the Articulos " + articulosCollectionOrphanCheckArticulos + " in its articulosCollection field has a non-nullable propietario field.");
            }
            Collection<CursosUsuarios> cursosUsuariosCollectionOrphanCheck = usuarios.getCursosUsuariosCollection();
            for (CursosUsuarios cursosUsuariosCollectionOrphanCheckCursosUsuarios : cursosUsuariosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the CursosUsuarios " + cursosUsuariosCollectionOrphanCheckCursosUsuarios + " in its cursosUsuariosCollection field has a non-nullable codUsuario field.");
            }
            Collection<AsignaturasUsuarios> asignaturasUsuariosCollectionOrphanCheck = usuarios.getAsignaturasUsuariosCollection();
            for (AsignaturasUsuarios asignaturasUsuariosCollectionOrphanCheckAsignaturasUsuarios : asignaturasUsuariosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the AsignaturasUsuarios " + asignaturasUsuariosCollectionOrphanCheckAsignaturasUsuarios + " in its asignaturasUsuariosCollection field has a non-nullable codUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
    
}
