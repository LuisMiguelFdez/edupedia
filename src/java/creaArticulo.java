
import DAO.ArticulosJpaController;
import DTO.Articulos;
import DTO.Usuarios;
import com.oreilly.servlet.MultipartRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alejandro
 */
public class creaArticulo extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EduPedia_V.0.1PU");
        ArticulosJpaController ctrArt = new ArticulosJpaController(emf);

        MultipartRequest mr = new MultipartRequest(request, getServletContext().getRealPath("/img"));
        
        String titulo = mr.getParameter("titulo");
        String tituloImagen = mr.getParameter("tituloImagen");
        String descripcion = mr.getParameter("descripcion");
        int curso = Integer.parseInt(mr.getParameter("filtroCurso"));
        int asig = Integer.parseInt(mr.getParameter("filtroAsig"));
        String img = mr.getFile("imagen").getName();
        
        //ultimo id del ultimo articulo
        List maxId = ctrArt.maxId();
        int ultimoId = (int) maxId.get(0) + 1;

        //ruta a articulos
        String root = getServletContext().getRealPath("/");
        File path = new File(root + "/../../web/articulos");

        //recuuperar el usuario de la sesion
        HttpSession session = request.getSession();
        Usuarios usu = (Usuarios) session.getAttribute("usuario");
        
        
        
        String url = path + "/" + ultimoId + ".html";

        //subir el articulo a la bbdd
        
        Articulos articulo = new Articulos(0, asig, curso, titulo, img, tituloImagen, descripcion, url, usu);

        
        try {
            ctrArt.create(articulo);
        } catch (Exception ex) {
            ex.printStackTrace(out);
            out.println("error");
            
        }
        
        

        //Crear el archivo html
        //String content = "<h1>Hello Wold</h1>";
        String content = mr.getParameter("contenidoEditor");

        File file = new File(path + "/"+ultimoId+".html");

        try (FileOutputStream fop = new FileOutputStream(file)) {

            // si el archivo no existe lo crea
            // si existe lo machaca
            if (!file.exists()) {
                file.createNewFile();
            }

            // obtenemos el contenino en bytes
            byte[] contentInBytes = content.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
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
