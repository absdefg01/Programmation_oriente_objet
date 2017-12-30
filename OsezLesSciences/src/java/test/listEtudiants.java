/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author zhaomengzi
 */
@WebServlet(name = "listEtudiants", urlPatterns = {"/listEtudiants"})
public class listEtudiants extends HttpServlet {

    private static final String URL = "jdbc:mysql://localhost:3306/osezlessciences";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "397949844";
    
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
        } catch ( ClassNotFoundException e ) {
            /* Gérer les éventuelles erreurs ici. */
        }
        try{
            //On se connecte au serveur
            Connection conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            //On prépare une requête SQL
            Statement stmt = conn.createStatement();
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Liste des étudiants</title>"); 
            out.println("<meta charset=\"utf-8\" />");
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
"		<link rel=\"stylesheet\" href=\"assets2/css/main.css\" />");
            out.println("<script type=\"text/javascript\">\n" +
"    function validateForm()\n" +
"    {\n" +
"        var a = document.getElementById('inp').value;\n" +
"    if (a==\"\")\n" +
"      {\n" +
"      alert(\"Veuillez remplir tous les champs !!\");\n" +
"      return false;\n" +
"      }\n" +
"    }\n" +
"    </script>");
            out.println("</head>");
            
            out.println("<body class=\"homepage\">\n");
            
            out.println("<div id=\"page-wrapper\">");
            out.println("<div id=\"header-wrapper\">\n" +
"					<header id=\"header\" class=\"container\">\n" +
"						<!-- Logo -->\n" +
"							<div id=\"logo\">\n" +
"								<h1>Osez les sciences</h1>\n" +
"                                                        </div>\n" +
"						<!-- Nav -->\n" +
"							<nav id=\"nav\">\n" +
"								<ul>\n" +
"									<li><a href=\"gererCreneau.html\">Gérer des créneaux</a></li>\n" +
"									<li><a href=\"connexionAdmin.html\">Déconnectez vous</a></li>\n" +
"								</ul>\n" +
"							</nav>\n" +
"					</header>\n" +
"				</div>");
            out.println("</div>");
            
            out.println("<!-- Main -->" +
"				<div id=\"main-wrapper\">" +
"					<div class=\"container\">" +
                    "<div class=\"8u 12u(medium) important(medium)\">" +
"								<!-- Content -->\n" +
"                                                                <div id=\"content\" style=\"margin-left: 270px\">\n" +
"										<section class=\"last\">\n"); 
            
            out.println("<h1 style='margin-left:40px'>choisir un enseigant pour voir la liste correspondante</h1>");
            out.println("<table border='2' align='center' cellpadding='15' cellspacing='10' width='150%'>");
                out.println("<tr style='border:solid;'>");
                    out.println("<th style='border:solid;' align='center'>IDEnseignant</th>");
                    out.println("<th style='border:solid;' align='center'>NomEnseignant</th>");
                    out.println("<th style='border:solid;' align='center'>PrenomEnseignant</th>");
                    out.println("<th style='border:solid;' align='center'>case à cocher</th>");
                 out.println("</tr>");
                 
                 //obtenir les colonne de créneau
                 ResultSet rs = stmt.executeQuery("select e.* from enseignant e, creneau c, inscription i\n" +
"where e.idEnseignant = c.idEnseignant\n" +
"and i.idCreneau = c.idCreneau\n" +
"and i.validite = 1\n" +
"group by e.idEnseignant\n" +
"");
                
                    
                while(rs.next()){
                    int idEnseignant = rs.getInt(1);
                    String nomEnseignant = rs.getString(2);
                    String prenomEnseignant = rs.getString(3);
                out.println("<tr style='border:solid;'>");
                    out.println("<td style='border:solid;' align='center'>"+idEnseignant+"</td>");
                    out.println("<td style='border:solid;' align='center'>"+nomEnseignant+"</td>");
                    out.println("<td style='border:solid;' align='center'>"+prenomEnseignant+"</td>");
                    
                    out.println("<form method='post' action='confirmList'>");
                    out.println("<td style='border:solid;' align='center'><INPUT type='checkbox' name='choix1' value='"+idEnseignant+"'></td>");
                    out.println("<input type='hidden' name='nomE' value='"+nomEnseignant+"'>");
                    out.println("<input type='hidden' name='prenomE' value='"+prenomEnseignant+"'>");
                    
                    
                    out.println("</tr>");
                }
            out.println("</table>");
            out.println("<input type='submit' name='suivant' value='Suivant' style='margin-left:150px'>");
            out.println("</form>");
            out.println("<br>");
            out.println("<br>");
            
            
            out.println("<form method='post' action='gererCreneau.html'>");
            out.println("<input type='submit' name='retourner' value='retourner' style='margin-left:150px'>");
            out.println("</form>");
 
            
            out.println("</section></div></div></div></div></div>");

            out.println("</body>");
            out.println("</html>");
            conn.close();
        }catch(SQLException ex){
            // On logge un message sur le serveur d'applicatiob
            Logger.getLogger(confirmConnexion.class.getName()).log(Level.SEVERE, null, ex);
            // On renvoie un message d'erreur au client
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());        
        }finally{
            out.close();
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
