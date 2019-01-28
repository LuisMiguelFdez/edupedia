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
@Table(name = "asignaturas_usuarios", catalog = "edupedia", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AsignaturasUsuarios.findAll", query = "SELECT a FROM AsignaturasUsuarios a")
    , @NamedQuery(name = "AsignaturasUsuarios.findById", query = "SELECT a FROM AsignaturasUsuarios a WHERE a.id = :id")})
public class AsignaturasUsuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @JoinColumn(name = "COD_ASIGNATURA", referencedColumnName = "COD_ASIGNATURA")
    @ManyToOne(optional = false)
    private Asignaturas codAsignatura;
    @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO")
    @ManyToOne(optional = false)
    private Usuarios codUsuario;

    public AsignaturasUsuarios() {
    }

    public AsignaturasUsuarios(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Asignaturas getCodAsignatura() {
        return codAsignatura;
    }

    public void setCodAsignatura(Asignaturas codAsignatura) {
        this.codAsignatura = codAsignatura;
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
        if (!(object instanceof AsignaturasUsuarios)) {
            return false;
        }
        AsignaturasUsuarios other = (AsignaturasUsuarios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.AsignaturasUsuarios[ id=" + id + " ]";
    }
    
}
