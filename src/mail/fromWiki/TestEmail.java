package mail.fromWiki;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Properties;

/**
 * Пример отправки текстового сообщения
 * https://ru.wikipedia.org/wiki/JavaMail
 */
public class TestEmail {
    public static void main(String[] args) {

        // Сюда необходимо подставить адрес получателя сообщения
        String to = "do110473sva@gmail.com";
        String from = "do110473sva@gmail.com";
        // Сюда необходимо подставить SMTP сервер, используемый для отправки
        String host = "smtp.gmail.com";

        // Создание свойств, получение сессии
        Properties props = new Properties();

        // При использовании статического метода Transport.send()
        // необходимо указать через какой хост будет передано сообщение
        props.put("mail.smtp.host", host);
        //от сюда http://stackoverflow.com/questions/15597616/sending-email-via-gmail-smtp-server-in-java
        props.put("mail.smtp.starttls.enable", true); // added this line
        props.put("mail.smtp.user", "do110473sva");
        props.put("mail.smtp.password", "valklanap");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", true);
        // ... от сюда
        //
        // Включение debug-режима
        props.put("mail.debug", "true");
        Session session = Session.getInstance(props, null);

        try {
            // Создание объекта сообщения
            Message msg = new MimeMessage(session);

            // Установка атрибутов сообщения
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject("Test E-Mail through Java");
            msg.setSentDate(new Date());

            // Установка тела сообщения
            msg.setText("This is a test of sending a " +
                    "plain text e-mail through Java.\n" +
                    "Here is line 2.");

            // Отправка сообщения
            //от сюда http://stackoverflow.com/questions/15597616/sending-email-via-gmail-smtp-server-in-java
            Transport transport = session.getTransport("smtp");
            String mfrom = "do110473sva";// example laabidiraissi
            transport.connect("smtp.gmail.com", mfrom, "valklanap");
            transport.sendMessage(msg, msg.getAllRecipients());
            //Transport.send(msg); //оригинал из вики
        }
        catch (MessagingException mex) {
            // Печать информации об исключении в случае его возникновения
            mex.printStackTrace();
        }
    }
}
