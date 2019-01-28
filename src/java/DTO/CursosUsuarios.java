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
@Table(name = "cursos_usuarios", catalog = "edupedia", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CursosUsuarios.findAll", query = "SELECT c FROM CursosUsuarios c")
    , @NamedQuery(name = "CursosUsuarios.findById", query = "SELECT c FROM CursosUsuarios c WHERE c.id = :id")})
public class CursosUsuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @JoinColumn(name = "COD_CURSO", referencedColumnName = "COD_CURSOS")
    @ManyToOne(optional = false)
    private Cursos codCurso;
    @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO")
    @ManyToOne(optional = false)
    private Usuarios codUsuario;

    public CursosUsuarios() {
    }

    public CursosUsuarios(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cursos getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(Cursos codCurso) {
        this.codCurso = codCurso;
    }

    public Usuarios getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(Usuarios codUsuario) {
        this.codUsuario = codUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CursosUsuarios)) {
            return false;
        }
        CursosUsuarios other = (CursosUsuarios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.CursosUsuarios[ id=" + id + " ]";
    }
    
}
