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
@WebServlet(name = "confirmList", urlPatterns = {"/confirmList"})
public class confirmList extends HttpServlet {
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
            
        /**
             * afficher des liste de créneau
             */
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Liste des étudiants</title>"); 
            out.println("<meta charset=\"utf-8\" />\n" +
"		<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
"		<link rel=\"stylesheet\" href=\"assets3/css/main.css\" />");
            out.println("</head>");
            
            out.println("<body>");
            out.println("<div id=\"content\">\n" +
"				<div class=\"inner\">");
            
            String nomE = request.getParameter("nomE");
            String prenomE = request.getParameter("prenomE");
            String[] checkbox = request.getParameterValues("choix1");
            int value = Integer.parseInt(checkbox[0]);
            
            out.println("<article class=\"box post post-excerpt\">\n" +
"							<header>"
                    + "                                         <h2>Liste des étudiants de M. '"+nomE+"' '"+prenomE+"'</h2>\n" +
"							</header>");
            
            out.println("<table border='2' align='center' cellpadding='15' cellspacing='10' width='150%'>");
                out.println("<tr style='border:solid;'>");
                    out.println("<th style='border:solid;' align='center'>Matiere</th>");
                    out.println("<th style='border:solid;' align='center'>Date</th>");
                    out.println("<th style='border:solid;' align='center'>HeureDebut</th>");
                    out.println("<th style='border:solid;' align='center'>HeureFin</th>");
                    out.println("<th style='border:solid;' align='center'>NomEtudiant</th>");
                    out.println("<th style='border:solid;' align='center'>PrenomEtudiant</th>");
                    
                    out.println("<th style='border:solid;' align='center'>Présent/Absent</th>");
                 out.println("</tr>");
                 
                 //obtenir les colonne de créneau
                 ResultSet rs = stmt.executeQuery("select e.*, el.*, i.*, c.*, m.nomMatiere from enseignant e,eleve el,Inscription i, creneau c,matiere m\n" +
"where i.idEleve = el.idEleve\n" +
"and c.idMatiere = m.idMatiere\n" +
"and c.idEnseignant = e.idEnseignant\n" +
"and e.idEnseignant = '"+value+"'\n"+ 
"and validite = 1\n" +
"order by c.idCreneau");
                
                    
                while(rs.next()){
                    String nomCreneau = rs.getString(19);
                    String date = rs.getString(13);
                    Time heureDebut = rs.getTime(14);
                    Time heureFin = rs.getTime(15);
                    String nomEtudiant = rs.getString(5);
                    String prenomEtudiant = rs.getString(6);
                    
                    
                out.println("<tr style='border:solid;'>");
                    out.println("<td style='border:solid;' align='center'>"+nomCreneau+"</td>");
                    out.println("<td style='border:solid;' align='center'>"+date+"</td>");
                    out.println("<td style='border:solid;' align='center'>"+heureDebut+"</td>");
                    out.println("<td style='border:solid;' align='center'>"+heureFin+"</td>");
                    out.println("<td style='border:solid;' align='center'>"+nomEtudiant+"</td>");
                    out.println("<td style='border:solid;' align='center'>"+prenomEtudiant+"</td>");

                out.println("</tr>");
                }
            out.println("</table>");
            
            out.println("<form method='post' action='gererCreneau.html'>");
            out.println("<input type='submit' name='retourner' value='retourner' style='margin-left:285px'>");
            out.println("</form>");
            out.println("</div>"
                    + "</div>");
            
            out.println("<!-- Sidebar -->\n" +
"			<div id=\"sidebar\">\n" +
"\n" +
"				<!-- Logo -->\n" +
"					<h1 id=\"logo\"><a href=\"#\">Osez les Sciences</a></h1>\n" +
"				<!-- Nav -->\n" +
"					<nav id=\"nav\">\n" +
"						<ul>\n" +
"							<li><a href=\"gererCreneau.html\">Gérer des créneaux</a></li>\n" +
"							<li><a href=\"connexionAdmin.html\">Déconnectez-vous</a></li>\n" +
"						</ul>\n" +
"					</nav>\n" +
"				<!-- Copyright -->\n" +
"					<ul id=\"copyright\">\n" +
"						<li>&copy; ZHAO Mengzi WANG Li.</li>\n" +
"					</ul>\n" +
"			</div>\n" +
"\n" +
"		<!-- Scripts -->\n" +
"			<script src=\"assets/js/jquery.min.js\"></script>\n" +
"			<script src=\"assets/js/skel.min.js\"></script>\n" +
"			<script src=\"assets/js/util.js\"></script>\n" +
"			<!--[if lte IE 8]><script src=\"assets/js/ie/respond.min.js\"></script><![endif]-->\n" +
"			<script src=\"assets/js/main.js\"></script>");
            out.println("</body>");
            out.println("</html>");
            rs.close();
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
