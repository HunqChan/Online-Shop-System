package service;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

public class MailService {

    private static final String USERNAME = "hung9a112004@gmail.com";
    private static final String PASSWORD = "cjdy gyip nccv kche";

    public static void sendWelcomeEmail(String toEmail, String fullName) {
        String subject = "Welcome to OSS Shop!";
        String content = "Hello " + fullName + ",\n\n"
                + "Welcome to OSS! Your account has been successfully created.\n\n"
                + "We're happy to have you!\n\n"
                + "Regards,\nOSS Team";

        // Cấu hình SMTP server
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Xác thực tài khoản gửi
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            // Tạo email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(content);

            // Gửi
            Transport.send(message);

            System.out.println("Welcome email sent to " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
