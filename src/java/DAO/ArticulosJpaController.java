/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DTO.Articulos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Usuarios;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author lmfde
 */
public class ArticulosJpaController implements Serializable {

    public ArticulosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Articulos articulos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios propietario = articulos.getPropietario();
            if (propietario != null) {
                propietario = em.getReference(propietario.getClass(), propietario.getCodUsuario());
                articulos.setPropietario(propietario);
            }
            em.persist(articulos);
            if (propietario != null) {
                propietario.getArticulosList().add(articulos);
                propietario = em.merge(propietario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Articulos articulos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Articulos persistentArticulos = em.find(Articulos.class, articulos.getCodArticulo());
            Usuarios propietarioOld = persistentArticulos.getPropietario();
            Usuarios propietarioNew = articulos.getPropietario();
            if (propietarioNew != null) {
                propietarioNew = em.getReference(propietarioNew.getClass(), propietarioNew.getCodUsuario());
                articulos.setPropietario(propietarioNew);
            }
            articulos = em.merge(articulos);
            if (propietarioOld != null && !propietarioOld.equals(propietarioNew)) {
                propietarioOld.getArticulosList().remove(articulos);
                propietarioOld = em.merge(propietarioOld);
            }
            if (propietarioNew != null && !propietarioNew.equals(propietarioOld)) {
                propietarioNew.getArticulosList().add(articulos);
                propietarioNew = em.merge(propietarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = articulos.getCodArticulo();
                if (findArticulos(id) == null) {
                    throw new NonexistentEntityException("The articulos with id " + id + " no longer exists.");
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
            Articulos articulos;
            try {
                articulos = em.getReference(Articulos.class, id);
                articulos.getCodArticulo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The articulos with id " + id + " no longer exists.", enfe);
            }
            Usuarios propietario = articulos.getPropietario();
            if (propietario != null) {
                propietario.getArticulosList().remove(articulos);
                propietario = em.merge(propietario);
            }
            em.remove(articulos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Articulos> findArticulosEntities() {
        return findArticulosEntities(true, -1, -1);
    }

    public List<Articulos> findArticulosEntities(int maxResults, int firstResult) {
        return findArticulosEntities(false, maxResults, firstResult);
    }

    private List<Articulos> findArticulosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Articulos.class));
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

    public Articulos findArticulos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Articulos.class, id);
        } finally {
            em.close();
        }
    }

    public int getArticulosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Articulos> rt = cq.from(Articulos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
public List<Articulos> articulosPorAsignatura(Integer codAsignatura) {
        ArrayList<Articulos> listaArticulos = null;
        List<Articulos> todosArticulos = findArticulosEntities();
        if (todosArticulos != null) {
            listaArticulos = new ArrayList<Articulos>();
            for (Articulos articulo : todosArticulos) {
                if (articulo.getAsignatura() == codAsignatura) {
                    listaArticulos.add(articulo);
                }
            }
        }

        return listaArticulos;
    }

    public List<Articulos> articulosPorCurso(Integer codCurso) {
        ArrayList<Articulos> listaArticulos = null;
        List<Articulos> todosArticulos = findArticulosEntities();
        if (todosArticulos != null) {
            listaArticulos = new ArrayList<Articulos>();
            for (Articulos articulo : todosArticulos) {
                if (articulo.getCurso() == codCurso) {
                    listaArticulos.add(articulo);
                }
            }
        }

        return listaArticulos;
    }

    public List<Articulos> articulosSinTexto(Integer codAsig, Integer codCurso) {
        return articulosFiltrado(codAsig, codCurso);
    }

    public List<Articulos> articulosConTexto(Integer codAsig, Integer codCurso, String busqueda) {
        ArrayList<Articulos> listaArticulos = null;
        List<Articulos> todosArticulos = articulosFiltrado(codAsig, codCurso);
        if (todosArticulos != null) {
            listaArticulos = new ArrayList<Articulos>();
            for (Articulos articulo : todosArticulos) {
                String coincidencia = articulo.getDescripcion() + articulo.getTitulo() + articulo.getImagen() + articulo.getTituloImagen();
                if (coincidencia.toLowerCase().indexOf(busqueda.toLowerCase()) > -1) {
                    listaArticulos.add(articulo);
                }
            }
        }
        return listaArticulos;
    }

    public List<Articulos> articulosFiltrado(Integer codAsig, Integer codCurso) {
        EntityManager em = getEntityManager();
        TypedQuery<Articulos> articulos;
        if (codCurso == 0) {
            if (codAsig == 0) {
                articulos = em.createNamedQuery("Articulos.findAll", Articulos.class);
            } else {
                articulos = em.createNamedQuery("Articulos.findByAsignatura", Articulos.class);
                articulos.setParameter("asignatura", codAsig);
            }
        } else {
            if (codAsig == 0) {
                articulos = em.createNamedQuery("Articulos.findByCurso", Articulos.class);
                articulos.setParameter("curso", codCurso);
            } else {
                articulos = em.createNamedQuery("Articulos.findByAsigCurso", Articulos.class);
                articulos.setParameter("curso", codCurso);
                articulos.setParameter("asignatura", codAsig);
            }
        }
        return articulos.getResultList();
    }

    public List maxId() {

        EntityManager em = getEntityManager();
        Query consulta = em.createNamedQuery("Articulos.findMaxId");
        return consulta.getResultList();

    }

    public List articulosDeUnPropietario(Usuarios valor) {

        EntityManager em = getEntityManager();
        Query consulta = em.createNamedQuery("Articulos.findByPropietario");
        consulta.setParameter("propietario", valor);
        return consulta.getResultList();

    }
    
    public List<Articulos> ultimosArticulos() {
        EntityManager em = getEntityManager();
        TypedQuery<Articulos> articulos = em.createNamedQuery("Articulos.find3Ultimos",Articulos.class);
        List<Articulos> tresUltimos = new ArrayList<Articulos>();
        List<Articulos> todosArticulos = articulos.getResultList();
        int cont = 0;
        while(todosArticulos!=null && tresUltimos.size()<3){
            tresUltimos.add(todosArticulos.get(cont));
            cont++;
        }
        return tresUltimos;
    }

    
    public Articulos articuloAleatorio() {
        List<Articulos> todosArticulos = findArticulosEntities();
        Articulos articuloAleatorio=null;
        if(todosArticulos.size()>0){
            int numAleatorio = (int)(Math.random() * (todosArticulos.size()-1));
            articuloAleatorio=todosArticulos.get(numAleatorio);
        }
        
        return articuloAleatorio;
    }

    
}
