package test;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class sendEmail extends HttpServlet {

    
private static final String CONTEXT = "bonjour!!";
    private static final String SUJET = "simple test";
    private static final String LOGINMAIL = "wangli19930828@gmail.com";
    private static final String LOGINMOT = "wangli24241234";
    private static final String DESTMAIL = "wangli19930828@gmail.com";
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

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
        Transport ts = session.getTransport();
        //3,login par identifiant et mot de passe et authentifie par le server smtp
        ts.connect("smtp.gmail.com", LOGINMAIL, LOGINMOT);
        //4,creer un mail
        Message message = createSimpleMail(session);
        //5,envoyer le mail
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }

    /**
     * @Method: createSimpleMail
     * @Description: creer un mail
     * @Anthor:wangli
     *
     * @param session
     * @return
     * @throws Exception
     */
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
}
