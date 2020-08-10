package com.szells.membership.mail;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@Data
@Slf4j
public class MessageSender {
    static final String SUBJECT = "Szells - Welcome Mail";
    @Value("${member.mail.HOST}")
    private String HOST;
    @Value("${member.mail.PORT}")
    private int PORT;
    @Value("${member.mail.SMTP_USERNAME}")
    private String SMTP_USERNAME;
    @Value("${member.mail.SMTP_PASSWORD}")
    private String SMTP_PASSWORD;
    @Value("${member.mail.FROM}")
    private String FROM;
    @Value("${member.mail.FROMNAME}")
    private String FROMNAME;

    public String send(String to, String memberName) {
        log.info("mail sent to {}, {}", memberName, to);
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", PORT);
//        props.put("mail.smtp.starttls.enable", "false");
//        props.put("mail.smtp.ssl.trust", HOST);
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        try (Transport transport = session.getTransport()) {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(FROM, FROMNAME));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(SUBJECT);
            msg.setContent(IWelcomeTemplate.emailContent(memberName), "text/html");

            log.info("Sending...");
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());
            log.info("Email Sent");
        } catch (Exception ex) {
            log.error("Error Sending email", ex);
        }
        return "E-Mail Sent";
    }
    public String onboardSend(String emailId, String activationCode) {
        log.info("mail sent to {}, {}", activationCode, emailId);
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", PORT);
//        props.put("mail.smtp.starttls.enable", "false");
//        props.put("mail.smtp.ssl.trust", HOST);
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        try (Transport transport = session.getTransport()) {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(FROM, FROMNAME));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(emailId));
            msg.setSubject(SUBJECT);
            msg.setContent(IActivationTemplate.emailContent(emailId,activationCode), "text/html");

            log.info("Sending...");
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());
            log.info("Email Sent");
        } catch (Exception ex) {
            log.error("Error Sending email", ex);
        }
        return "E-Mail Sent";
    }

}
