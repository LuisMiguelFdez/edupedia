/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lmfde
 */
@Entity
@Table(name = "articulos", catalog = "edupedia", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Articulos.findAll", query = "SELECT a FROM Articulos a")
    , @NamedQuery(name = "Articulos.findByCodArticulo", query = "SELECT a FROM Articulos a WHERE a.codArticulo = :codArticulo")
    , @NamedQuery(name = "Articulos.findByAsignatura", query = "SELECT a FROM Articulos a WHERE a.asignatura = :asignatura")
    , @NamedQuery(name = "Articulos.findByCurso", query = "SELECT a FROM Articulos a WHERE a.curso = :curso")
    , @NamedQuery(name = "Articulos.findByTitulo", query = "SELECT a FROM Articulos a WHERE a.titulo = :titulo")
    , @NamedQuery(name = "Articulos.findByImagen", query = "SELECT a FROM Articulos a WHERE a.imagen = :imagen")
    , @NamedQuery(name = "Articulos.findMaxId", query = "SELECT MAX( a.codArticulo ) FROM Articulos a")
    , @NamedQuery(name = "Articulos.findByPropietario", query = "SELECT a FROM Articulos a WHERE a.propietario = :propietario")
    , @NamedQuery(name = "Articulos.findByAsigCurso" , query = "SELECT a FROM Articulos a WHERE a.curso = :curso AND a.asignatura = :asignatura")
    , @NamedQuery(name = "Articulos.find3Ultimos" , query = "SELECT a FROM Articulos a ORDER BY a.codArticulo DESC")
    , @NamedQuery(name = "Articulos.findByTituloImagen", query = "SELECT a FROM Articulos a WHERE a.tituloImagen = :tituloImagen")})
public class Articulos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "COD_ARTICULO")
    private Integer codArticulo;
    @Basic(optional = false)
    @Column(name = "ASIGNATURA")
    private int asignatura;
    @Basic(optional = false)
    @Column(name = "CURSO")
    private int curso;
    @Basic(optional = false)
    @Column(name = "TITULO")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "IMAGEN")
    private String imagen;
    @Basic(optional = false)
    @Column(name = "TITULO_IMAGEN")
    private String tituloImagen;
    @Basic(optional = false)
    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Lob
    @Column(name = "URL")
    private String url;
    @JoinColumn(name = "PROPIETARIO", referencedColumnName = "COD_USUARIO")
    @ManyToOne(optional = false)
    private Usuarios propietario;

    public Articulos() {
    }

    public Articulos(Integer codArticulo) {
        this.codArticulo = codArticulo;
    }

    public Articulos(Integer codArticulo, int asignatura, int curso, String titulo, String imagen, String tituloImagen, String descripcion) {
        this.codArticulo = codArticulo;
        this.asignatura = asignatura;
        this.curso = curso;
        this.titulo = titulo;
        this.imagen = imagen;
        this.tituloImagen = tituloImagen;
        this.descripcion = descripcion;
    }

    public Articulos(Integer codArticulo, int asignatura, int curso, String titulo, String imagen, String tituloImagen, String descripcion, String url, Usuarios propietario) {
        this.codArticulo = codArticulo;
        this.asignatura = asignatura;
        this.curso = curso;
        this.titulo = titulo;
        this.imagen = imagen;
        this.tituloImagen = tituloImagen;
        this.descripcion = descripcion;
        this.url = url;
        this.propietario = propietario;
    }

    public Integer getCodArticulo() {
        return codArticulo;
    }

    public void setCodArticulo(Integer codArticulo) {
        this.codArticulo = codArticulo;
    }

    public int getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(int asignatura) {
        this.asignatura = asignatura;
    }

    public int getCurso() {
        return curso;
    }

    public void setCurso(int curso) {
        this.curso = curso;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTituloImagen() {
        return tituloImagen;
    }

    public void setTituloImagen(String tituloImagen) {
        this.tituloImagen = tituloImagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Usuarios getPropietario() {
        return propietario;
    }

    public void setPropietario(Usuarios propietario) {
        this.propietario = propietario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codArticulo != null ? codArticulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Articulos)) {
            return false;
        }
        Articulos other = (Articulos) object;
        if ((this.codArticulo == null && other.codArticulo != null) || (this.codArticulo != null && !this.codArticulo.equals(other.codArticulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Articulos[ codArticulo=" + codArticulo + " ]";
    }
    
}
