package com.louay.controller.verification;

import com.louay.model.entity.authentication.UsersAuthentication;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SendingVerificationEmail {

    private Properties getEmailProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.transport.protocol", "smtp");
        //prop.put("mail.smtp.host", "smtp.mailtrap.io");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.socketFactory.port", "465");
        //prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.socketFactory.fallback", "false");


        return prop;
    }

    private Session getEmailSession() {
        return Session.getDefaultInstance(getEmailProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("DevelopmentTestLNMA@gmail.com", "167943@see");
            }
        });
    }

    public void sendMessage(UsersAuthentication usersAuthentication) {
        try {
            Message message = new MimeMessage(getEmailSession());
            message.setFrom(new InternetAddress("DevelopmentTestLNMA@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("a3.juara@gmail.com"));
            message.setSubject("Course Management System verification");

            String msg = getMessageContent(usersAuthentication);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.addHeader("content-type", "text/html");
            mimeBodyPart.setContent(msg, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }

    private String getMessageContent(UsersAuthentication usersAuthentication){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"/>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no\"/>\n" +
                "    <meta name=\"apple-mobile-web-app-capable\" content=\"yes\"/>\n" +
                "    <title>Verification Account</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<header>\n" +
                "    <nav style=\"background-color: #3e3c4e;position:relative;display:-ms-flexbox;display:flex;-ms-flex-wrap:wrap;flex-wrap:wrap;-ms-flex-align:center;align-items:center;-ms-flex-pack:justify;justify-content:space-between;padding:.5rem 1rem;\">\n" +
                "        <h3 style=\"color:#f8f9fa;text-transform:capitalize;\">course management system</h3>\n" +
                "    </nav>\n" +
                "</header>\n" +
                "<main style=\"background: #dedddd no-repeat fixed;padding-top: 1%;padding-bottom: 1%;background-size: cover;\">\n" +
                "    <div style=\"width: 70%;height: auto;margin: 6% 14% 5%;background-color: white;border-radius: 1em 1em;text-align:center;\">\n" +
                "        <br>\n" +
                "        <h2 style=\"color: #3e3c4e;text-transform:capitalize;font-weight:700;\">Welcome</h2>\n" +
                "        <div style=\"font-size:1.2rem;text-transform:capitalize;color:#3e3c4e;\">\n" +
                "            <p>"+usersAuthentication.getUsers().getForename()+" "+usersAuthentication.getUsers().getSurname()+"</p>\n" +
                "            <p>Thank you for sign up for course management system.</p>\n" +
                "            <p>Please verify your email address by clicking the button below.</p>\n" +
                "            <p>\n" +
                "                <a href=\"https://192.168.1.11:8443/userVerify/{"+usersAuthentication.getUsers().getEmail()+"}/{"+usersAuthentication.getVerificationNumber()+"}\">\n" + //TODO : change localhost IP
                "                    <button type=\"button\" style=\"height: 80px;border-radius: 2.5em;background: linear-gradient(to right,#e759fd,#206490,#88c7f0);color: white;font-weight: bold;font-size: 18px;width:40%;\">\n" +
                "                        <h5>Confirm my account</h5>\n" +
                "                    </button>\n" +
                "                </a>\n" +
                "            </p>" +
                "            <p>If you didn't request this, please ignore this email.</p>\n" +
                "        </div>\n" +
                "        <div style=\"font-size:1.3rem;color: #222f39\">\n" +
                "            <hr style=\"width:75%;\">\n" +
                "            <h5 style=\"font-weight:800;font-style: oblique\">Project Developer</h5>\n" +
                "            <p style=\"font-style:italic;font-weight:500;\">\n" +
                "            <h5>Louay Amr</h5>\n" +
                "            </p>\n" +
                "            <p style=\"font-weight:300\">\n" +
                "            <h6>Louay_Amr@outlook.com</h6>\n" +
                "            </p>\n" +
                "            <br>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</main>\n" +
                "<footer>\n" +
                "    <nav style=\"background-color: #d3c7cd; height: 9em; width: 100%;margin-bottom:0;position:relative;display:-ms-flexbox;display:flex;-ms-flex-wrap:wrap;flex-wrap:wrap;-ms-flex-align:center;align-items:center;-ms-flex-pack:justify;justify-content:space-between;padding:.5rem 1rem\">\n" +
                "        <p>Louay Amr © 2020</p>\n" +
                "    </nav>\n" +
                "</footer>\n" +
                "</body>\n" +
                "</html>";
    }
}
