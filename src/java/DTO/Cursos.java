/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lmfde
 */
@Entity
@Table(name = "cursos", catalog = "edupedia", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cursos.findAll", query = "SELECT c FROM Cursos c")
    , @NamedQuery(name = "Cursos.findByCodCursos", query = "SELECT c FROM Cursos c WHERE c.codCursos = :codCursos")
    , @NamedQuery(name = "Cursos.findByNombreCurso", query = "SELECT c FROM Cursos c WHERE c.nombreCurso = :nombreCurso")})
public class Cursos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "COD_CURSOS")
    private Integer codCursos;
    @Basic(optional = false)
    @Column(name = "NOMBRE_CURSO")
    private String nombreCurso;
    @ManyToMany(mappedBy = "cursosCollection")
    private Collection<Asignaturas> asignaturasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codCurso")
    private Collection<CursosUsuarios> cursosUsuariosCollection;

    public Cursos() {
    }

    public Cursos(Integer codCursos) {
        this.codCursos = codCursos;
    }

    public Cursos(Integer codCursos, String nombreCurso) {
        this.codCursos = codCursos;
        this.nombreCurso = nombreCurso;
    }

    public Integer getCodCursos() {
        return codCursos;
    }

    public void setCodCursos(Integer codCursos) {
        this.codCursos = codCursos;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    @XmlTransient
    public Collection<Asignaturas> getAsignaturasCollection() {
        return asignaturasCollection;
    }

    public void setAsignaturasCollection(Collection<Asignaturas> asignaturasCollection) {
        this.asignaturasCollection = asignaturasCollection;
    }

    @XmlTransient
    public Collection<CursosUsuarios> getCursosUsuariosCollection() {
        return cursosUsuariosCollection;
    }

    public void setCursosUsuariosCollection(Collection<CursosUsuarios> cursosUsuariosCollection) {
        this.cursosUsuariosCollection = cursosUsuariosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCursos != null ? codCursos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cursos)) {
            return false;
        }
        Cursos other = (Cursos) object;
        if ((this.codCursos == null && other.codCursos != null) || (this.codCursos != null && !this.codCursos.equals(other.codCursos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Cursos[ codCursos=" + codCursos + " ]";
    }
    
}
