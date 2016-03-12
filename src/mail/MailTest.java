package mail;


/**
 * Created by Valk on 09.01.16.
 */
import com.sun.mail.smtp.SMTPTransport;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class MailTest {
    public static void main(String[] args) throws IOException, MessagingException {
        Properties properties = new Properties();
        //
        //properties.load(new FileInputStream("mail.properties"));
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");
        //
        Session session = Session.getDefaultInstance(properties);
        //
        MimeMessage message = new MimeMessage(session);
        message.setSubject("письмо из Java");
        message.setText("Hellow !");
        //message.addRecipient(Message.RecipientType.TO, new InternetAddress("do110473sva@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("do110473sva@gmail.com", false));
        //message.setSender(new InternetAddress("do110473sva@gmail.com"));
        message.setSentDate(new Date());
        //
        String userLogin = "do110473sva@gmail.com";
        String password = "valklanap";
        //Transport transport = session.getTransport();
        SMTPTransport transport = (SMTPTransport)session.getTransport("smtps");
        //transport.connect("smtp.gmail.com", 465, userLogin, password);
        transport.connect("smtp.gmail.com", userLogin, password);
        //
        transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
        transport.close();


    }
}
