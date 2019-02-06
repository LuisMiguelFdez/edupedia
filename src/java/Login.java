/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import DAO.ArticulosJpaController;
import DAO.UsuariosJpaController;
import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DTO.Usuarios;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alejandro
 */
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EduPedia_V.0.1PU");
        UsuariosJpaController ctrUsu = new UsuariosJpaController(emf);

        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        Usuarios usu = null;
        
        List<Usuarios> usuario = ctrUsu.usuarioPorEmail(email);
        if(usuario.size()>0){
            usu = (Usuarios)usuario.get(0);
        }
        
        if (usu != null) {
            String contra = usu.getPassword();
            if (contra.equals(pass)) {
                // Si la validación es correcta creamos la sesión
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usu);
                response.sendRedirect("index.jsp");
            } else {
                request.setAttribute("mensaje", "Correo o contraseña incorrectos");
                response.sendRedirect("login.jsp");
            }
        } else {
                request.setAttribute("mensaje", "Correo o contraseña incorrectos");
                response.sendRedirect("login.jsp");
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
