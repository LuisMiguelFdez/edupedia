package Ajax;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

 @WebServlet(
        urlPatterns = "/cambiaAsignaturas",
        initParams ={
            @WebInitParam(name = "userName", value = "root"),
            @WebInitParam(name = "password", value = "root"),
            @WebInitParam(name = "url",      value = "jdbc:mysql://localhost/edupedia"),
            @WebInitParam(name = "driver",   value = "com.mysql.jdbc.Driver")
        }
)

public class cambiaAsignaturas extends HttpServlet {
 
    private static final long serialVersionUID = 1L;
    
    private String url;
    private String driver;
    private String userName;
    private String password;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
       
       
        String codCurso = request.getParameter("curso");  
        String consulta="";
        JSONObject obj = new JSONObject();
        
        if (codCurso!=null && Integer.parseInt(codCurso)!=0){
            consulta ="select * from asignaturas where COD_ASIGNATURA "
                    + "IN (Select COD_ASIGNATURA from asignaturas_por_cursos where cod_curso ="+Integer.parseInt(codCurso)+")";
        }
        else{
            consulta ="select * from asignaturas where COD_ASIGNATURA ";
        }
 
        try {
            ResultSet datos=conectaBD(consulta);
            while( datos.next()){     
                obj.put(datos.getString(1),datos.getString(2)); 
            }
        } catch (InstantiationException ex) { 
            System.out.println("Error en ---> " +ex.getMessage());
        } catch (IllegalAccessException ex) {
            System.out.println("Error en ---> " +ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("Error en ---> " +ex.getMessage());
        } catch (JSONException ex) {
            System.out.println("Error en ---> " +ex.getMessage());
        }
       
        response.setContentType("application/json");
        response.getWriter().print(obj.toString());
        }//doPost
    
    //----------
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        doPost(request,response);
    
    }
    //----------
        
    
protected ResultSet conectaBD(String consulta) throws InstantiationException, IllegalAccessException, SQLException
{
    Connection conn = null;
    Statement stmt = null;
    String sqlStr="";
    ResultSet rset;
   
    try {
    
    //Paso 1: Cargar el driver JDBC.
    Class.forName(driver).newInstance();
   
    // Paso 2: Conectarse a la Base de Datos utilizando la clase Connection
    conn = DriverManager.getConnection(url, userName, password);
    // Paso 3: Crear sentencias SQL, utilizando objetos de tipo Statement
    stmt = conn.createStatement();
    
    return  rset = stmt.executeQuery(consulta);
    
    } catch (ClassNotFoundException ex){
        ex.getMessage();
        ex.printStackTrace();
    }
        return null;
    
}//-------------   
//--------------------------------------------------
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
       
        url=config.getInitParameter("url");           
        driver=config.getInitParameter("driver");
        userName=config.getInitParameter("userName");
        password=config.getInitParameter("password");
    }  
}