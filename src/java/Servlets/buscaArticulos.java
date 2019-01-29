/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import DAO.ArticulosJpaController;
import DTO.Articulos;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author lmfde
 */
public class buscaArticulos extends HttpServlet {

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
        String curso = request.getParameter("curso");            //1
        String asignatura = request.getParameter("asignatura");  //2
        String busqueda = request.getParameter("busqueda");//4
        int eleccion=0;
        /*Convertimos los posibles resultados en combinaciones* y le asignamos un numero potencia de 2*/
        if(curso!=null){
            eleccion+=1;
        }
        if(asignatura!=null){
            eleccion+=2;
        }
        if(busqueda!=null){
            eleccion+=4;
        }

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EduPedia_V.0.1PU");
        ArticulosJpaController ctrlArticulos = new ArticulosJpaController(emf);
        ArrayList<Articulos> articulos = null;
        switch(eleccion){
            case 1: //Enlace --> solo por curso
                articulos=ctrlArticulos.articulosPorCurso(Integer.parseInt(curso));
                request.setAttribute("mensaje", "Mostrando artículos por curso");
                break;
            case 2: //Enlace --> solo por asignatura
                articulos=ctrlArticulos.articulosPorAsignatura(Integer.parseInt(asignatura));
                request.setAttribute("mensaje", "Mostrando artículos por asignaturas");
                break;
            case 3: // Buscador --> curso + asignatura sin texto
                break;
            case 7: // Buscador --> curso + asignatura con texto
                break;
            default:
                break;
        }
        /*Si es nulo, aprovecharemos eso para no pintarlos despues*/
        request.setAttribute("articulosEncontrados", articulos);
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
