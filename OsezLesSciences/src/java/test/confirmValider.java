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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static test.sendEmail.createSimpleMail;

/**
 *
 * @author zhaomengzi
 */
@WebServlet(name = "confirmValider", urlPatterns = {"/confirmValider"})
public class confirmValider extends HttpServlet {
    private static final String URL = "jdbc:mysql://localhost:3306/osezlessciences";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "397949844";
    
    
    
    
    
    
    private static final String CONTEXT = "Bonjour, votre inscription a été validé!!";
    private static final String CONTEXT2 = "Bonjour, votre inscription a été rejeté !";
    private static final String SUJET = "simple test";
    private static final String LOGINMAIL = "creneauchampollion@gmail.com";
    private static final String LOGINMOT = "champollionJFC";
    private static String DESTMAIL ;
    
    
    
    
    
    
    
    
    
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
        
        
        try {
             //On se connecte au serveur
            Connection conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            //On prépare une requête SQL
            Statement stmt = conn.createStatement();
            String[] checkbox = request.getParameterValues("choix1");
            int value = Integer.parseInt(checkbox[0]);

            String valider = request.getParameter("Valider");
            String idEleve = request.getParameter("idE");
            String v = "Valider";
            String r = "Rejeter";
            
            int idE = Integer.parseInt(idEleve);

            ResultSet rs = stmt.executeQuery("select mailEleve from Eleve where idEleve = '"+idE+"'");
            rs.next();
            String mail = rs.getString(1);
            
            DESTMAIL = mail;
            
            
            if(valider.equals(v)){
                PreparedStatement editStatement = conn.prepareStatement(
                                    "update inscription\n" +
    "set validite = 1\n" +
    "where idCreneau = ?\n" +
    "and idEleve = ?");

                editStatement.setInt(1, value);
                editStatement.setInt(2, idE);
                editStatement.executeUpdate();
                editStatement.close();


                editStatement = conn.prepareStatement("update creneau\n" +
    "set nbEleveMax = nbEleveMax - 1\n" +
    "where idCreneau = ?");
                editStatement.setInt(1, value);
                editStatement.executeUpdate();
                editStatement.close();
                


        /**
         * envoyer des mails
         */
        Properties prop = new Properties();
        //ios port 465
        //windows port 587
        prop.setProperty("mail.smtp.port", "465");
        prop.setProperty("mail.host", "smtp.gmail.com");
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.EnableSSL.enable", "true");

        prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.setProperty("mail.smtp.socketFactory.fallback", "false");
        //5 etape d'envoyer un mail par javamail
        //1,creer session
        Session session = Session.getInstance(prop);
        //activer setDebug pour optimiser le processus 
        session.setDebug(true);
        //2,obtenir l'objet Transport par session
        Transport ts = null;
                try {
                    ts = session.getTransport();
                } catch (NoSuchProviderException ex) {
                    Logger.getLogger(confirmValider.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    //3,login par identifiant et mot de passe et authentifie par le server smtp
                    ts.connect("smtp.gmail.com", LOGINMAIL, LOGINMOT);
                } catch (MessagingException ex) {
                    Logger.getLogger(confirmValider.class.getName()).log(Level.SEVERE, null, ex);
                }
        //4,creer un mail
        Message message = null;
                try {
                    message = createSimpleMail(session);
                } catch (Exception ex) {
                    Logger.getLogger(confirmValider.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    //5,envoyer le mail
                    ts.sendMessage(message, message.getAllRecipients());
                } catch (MessagingException ex) {
                    Logger.getLogger(confirmValider.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    ts.close();
                } catch (MessagingException ex) {
                    Logger.getLogger(confirmValider.class.getName()).log(Level.SEVERE, null, ex);
                }
                response.sendRedirect("validerCreneau");


            }else{
                PreparedStatement editStatement = conn.prepareStatement(
                                    "delete from inscription\n" +
    "where idCreneau = ?\n" +
    "and idEleve = ?");

                editStatement.setInt(1, value);
                editStatement.setInt(2, idE);
                editStatement.executeUpdate();
                editStatement.close();
                
                /**
                    * envoyer des mails
                */
        Properties prop = new Properties();
        //ios port 465
        //windows port 587
        prop.setProperty("mail.smtp.port", "465");
        prop.setProperty("mail.host", "smtp.gmail.com");
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.EnableSSL.enable", "true");

        prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.setProperty("mail.smtp.socketFactory.fallback", "false");
        //5 etape d'envoyer un mail par javamail
        //1,creer session
        Session session = Session.getInstance(prop);
        //activer setDebug pour optimiser le processus 
        session.setDebug(true);
        //2,obtenir l'objet Transport par session
        Transport ts = null;
                try {
                    ts = session.getTransport();
                } catch (NoSuchProviderException ex) {
                    Logger.getLogger(confirmValider.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    //3,login par identifiant et mot de passe et authentifie par le server smtp
                    ts.connect("smtp.gmail.com", LOGINMAIL, LOGINMOT);
                } catch (MessagingException ex) {
                    Logger.getLogger(confirmValider.class.getName()).log(Level.SEVERE, null, ex);
                }
        //4,creer un mail
        Message message = null;
                try {
                    message = createSimpleMail2(session);
                } catch (Exception ex) {
                    Logger.getLogger(confirmValider.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    //5,envoyer le mail
                    ts.sendMessage(message, message.getAllRecipients());
                } catch (MessagingException ex) {
                    Logger.getLogger(confirmValider.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    ts.close();
                } catch (MessagingException ex) {
                    Logger.getLogger(confirmValider.class.getName()).log(Level.SEVERE, null, ex);
                }

                response.sendRedirect("validerCreneau");

            }
            
        }catch(SQLException ex){
            // On logge un message sur le serveur d'applicatiob
            Logger.getLogger(confirmConnexion.class.getName()).log(Level.SEVERE, null, ex);
            // On renvoie un message d'erreur au client
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());        
        }finally{
            out.close();
        }
    }
    
    
    
    
    
    
    
    
    
    
    public static MimeMessage createSimpleMail(Session session)
            throws Exception {
        //creer un mail
        MimeMessage message;
        message = new MimeMessage(session);
        //envoyeur
        message.setFrom(new InternetAddress(LOGINMAIL));
        //destinaire
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(DESTMAIL));
        //titre
        message.setSubject(SUJET);
        //context
        message.setContent(CONTEXT, "text/html;charset=UTF-8");
        //renvoyer le mail
        return message;
    }
    
    
    public static MimeMessage createSimpleMail2(Session session)
            throws Exception {
        //creer un mail
        MimeMessage message;
        message = new MimeMessage(session);
        //envoyeur
        message.setFrom(new InternetAddress(LOGINMAIL));
        //destinaire
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(DESTMAIL));
        //titre
        message.setSubject(SUJET);
        //context
        message.setContent(CONTEXT2, "text/html;charset=UTF-8");
        //renvoyer le mail
        return message;
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
