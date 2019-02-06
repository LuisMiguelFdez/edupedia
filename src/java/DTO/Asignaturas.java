/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lmfde
 */
@Entity
@Table(name = "asignaturas", catalog = "edupedia", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asignaturas.findAll", query = "SELECT a FROM Asignaturas a")
    , @NamedQuery(name = "Asignaturas.findByCodAsignatura", query = "SELECT a FROM Asignaturas a WHERE a.codAsignatura = :codAsignatura")
    , @NamedQuery(name = "Asignaturas.findByNombreAsignatura", query = "SELECT a FROM Asignaturas a WHERE a.nombreAsignatura = :nombreAsignatura")})
public class Asignaturas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "COD_ASIGNATURA")
    private Integer codAsignatura;
    @Basic(optional = false)
    @Column(name = "NOMBRE_ASIGNATURA")
    private String nombreAsignatura;
    @JoinTable(name = "asignaturas_por_cursos", joinColumns = {
        @JoinColumn(name = "COD_ASIGNATURA", referencedColumnName = "COD_ASIGNATURA")}, inverseJoinColumns = {
        @JoinColumn(name = "COD_CURSO", referencedColumnName = "COD_CURSOS")})
    @ManyToMany
    private List<Cursos> cursosList;
    @JoinTable(name = "asignaturas_usuarios", joinColumns = {
        @JoinColumn(name = "COD_ASIGNATURA", referencedColumnName = "COD_ASIGNATURA")}, inverseJoinColumns = {
        @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO")})
    @ManyToMany
    private List<Usuarios> usuariosList;

    public Asignaturas() {
    }

    public Asignaturas(Integer codAsignatura) {
        this.codAsignatura = codAsignatura;
    }

    public Asignaturas(Integer codAsignatura, String nombreAsignatura) {
        this.codAsignatura = codAsignatura;
        this.nombreAsignatura = nombreAsignatura;
    }

    public Integer getCodAsignatura() {
        return codAsignatura;
    }

    public void setCodAsignatura(Integer codAsignatura) {
        this.codAsignatura = codAsignatura;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    @XmlTransient
    public List<Cursos> getCursosList() {
        return cursosList;
    }

    public void setCursosList(List<Cursos> cursosList) {
        this.cursosList = cursosList;
    }

    @XmlTransient
    public List<Usuarios> getUsuariosList() {
        return usuariosList;
    }

    public void setUsuariosList(List<Usuarios> usuariosList) {
        this.usuariosList = usuariosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codAsignatura != null ? codAsignatura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asignaturas)) {
            return false;
        }
        Asignaturas other = (Asignaturas) object;
        if ((this.codAsignatura == null && other.codAsignatura != null) || (this.codAsignatura != null && !this.codAsignatura.equals(other.codAsignatura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Asignaturas[ codAsignatura=" + codAsignatura + " ]";
    }
    
}
