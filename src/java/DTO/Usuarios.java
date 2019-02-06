/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.List;
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
@Table(name = "usuarios", catalog = "edupedia", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u")
    , @NamedQuery(name = "Usuarios.findByCodUsuario", query = "SELECT u FROM Usuarios u WHERE u.codUsuario = :codUsuario")
    , @NamedQuery(name = "Usuarios.findByNombre", query = "SELECT u FROM Usuarios u WHERE u.nombre = :nombre")
    , @NamedQuery(name = "Usuarios.findByPassword", query = "SELECT u FROM Usuarios u WHERE u.password = :password")
    , @NamedQuery(name = "Usuarios.findByApellidos", query = "SELECT u FROM Usuarios u WHERE u.apellidos = :apellidos")
    , @NamedQuery(name = "Usuarios.findByEmail", query = "SELECT u FROM Usuarios u WHERE u.email = :email")
    , @NamedQuery(name = "Usuarios.findByFotoperfil", query = "SELECT u FROM Usuarios u WHERE u.fotoperfil = :fotoperfil")
    , @NamedQuery(name = "Usuarios.findByRol", query = "SELECT u FROM Usuarios u WHERE u.rol = :rol")})
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "COD_USUARIO")
    private Integer codUsuario;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Basic(optional = false)
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "FOTOPERFIL")
    private String fotoperfil;
    @Basic(optional = false)
    @Column(name = "ROL")
    private String rol;
    @ManyToMany(mappedBy = "usuariosList")
    private List<Cursos> cursosList;
    @ManyToMany(mappedBy = "usuariosList")
    private List<Asignaturas> asignaturasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "propietario")
    private List<Articulos> articulosList;

    public Usuarios() {
    }

    public Usuarios(Integer codUsuario) {
        this.codUsuario = codUsuario;
    }

    public Usuarios(Integer codUsuario, String nombre, String password, String email, String rol) {
        this.codUsuario = codUsuario;
        this.nombre = nombre;
        this.password = password;
        this.email = email;
        this.rol = rol;
    }

    public Integer getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(Integer codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFotoperfil() {
        return fotoperfil;
    }

    public void setFotoperfil(String fotoperfil) {
        this.fotoperfil = fotoperfil;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @XmlTransient
    public List<Cursos> getCursosList() {
        return cursosList;
    }

    public void setCursosList(List<Cursos> cursosList) {
        this.cursosList = cursosList;
    }

    @XmlTransient
    public List<Asignaturas> getAsignaturasList() {
        return asignaturasList;
    }

    public void setAsignaturasList(List<Asignaturas> asignaturasList) {
        this.asignaturasList = asignaturasList;
    }

    @XmlTransient
    public List<Articulos> getArticulosList() {
        return articulosList;
    }

    public void setArticulosList(List<Articulos> articulosList) {
        this.articulosList = articulosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codUsuario != null ? codUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.codUsuario == null && other.codUsuario != null) || (this.codUsuario != null && !this.codUsuario.equals(other.codUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Usuarios[ codUsuario=" + codUsuario + " ]";
    }
    
}
