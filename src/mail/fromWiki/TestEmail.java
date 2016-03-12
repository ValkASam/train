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
 * ������ �������� ���������� ���������
 * https://ru.wikipedia.org/wiki/JavaMail
 */
public class TestEmail {
    public static void main(String[] args) {

        // ���� ���������� ���������� ����� ���������� ���������
        String to = "do110473sva@gmail.com";
        String from = "do110473sva@gmail.com";
        // ���� ���������� ���������� SMTP ������, ������������ ��� ��������
        String host = "smtp.gmail.com";

        // �������� �������, ��������� ������
        Properties props = new Properties();

        // ��� ������������� ������������ ������ Transport.send()
        // ���������� ������� ����� ����� ���� ����� �������� ���������
        props.put("mail.smtp.host", host);
        //�� ���� http://stackoverflow.com/questions/15597616/sending-email-via-gmail-smtp-server-in-java
        props.put("mail.smtp.starttls.enable", true); // added this line
        props.put("mail.smtp.user", "do110473sva");
        props.put("mail.smtp.password", "valklanap");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", true);
        // ... �� ����
        //
        // ��������� debug-������
        props.put("mail.debug", "true");
        Session session = Session.getInstance(props, null);

        try {
            // �������� ������� ���������
            Message msg = new MimeMessage(session);

            // ��������� ��������� ���������
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject("Test E-Mail through Java");
            msg.setSentDate(new Date());

            // ��������� ���� ���������
            msg.setText("This is a test of sending a " +
                    "plain text e-mail through Java.\n" +
                    "Here is line 2.");

            // �������� ���������
            //�� ���� http://stackoverflow.com/questions/15597616/sending-email-via-gmail-smtp-server-in-java
            Transport transport = session.getTransport("smtp");
            String mfrom = "do110473sva";// example laabidiraissi
            transport.connect("smtp.gmail.com", mfrom, "valklanap");
            transport.sendMessage(msg, msg.getAllRecipients());
            //Transport.send(msg); //�������� �� ����
        }
        catch (MessagingException mex) {
            // ������ ���������� �� ���������� � ������ ��� �������������
            mex.printStackTrace();
        }
    }
}
