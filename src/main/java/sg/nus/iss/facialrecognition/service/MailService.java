package sg.nus.iss.facialrecognition.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.Properties;
import sg.nus.iss.facialrecognition.model.User;

@Service
public class MailService {
    @Value("${app.email.from}")
    private String fromEmail;
    
    @Value("${app.url}")
    private String appUrl;

    @Value("${app.email.support}")
    private String supportEmail;
    
    @Autowired 
    private MailSender mailSender;
    
    public void sendMail(String to, String subject, String text) {
        try {
            /*SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(to);
            email.setSubject(subject);
            email.setFrom(fromEmail);
            email.setText(text);
            mailSender.send(email);*/
        	
        	Properties props = new Properties();
    		props.put("mail.smtp.host", "smtp.gmail.com");
    		props.put("mail.smtp.socketFactory.port", "465");
    		props.put("mail.smtp.socketFactory.class",
    				"javax.net.ssl.SSLSocketFactory");
    		props.put("mail.smtp.auth", "true");
    		props.put("mail.smtp.port", "465");

    		Session session = Session.getDefaultInstance(props,
    			new javax.mail.Authenticator() {
    				protected PasswordAuthentication getPasswordAuthentication() {
    					return new PasswordAuthentication("autisticteam4","autistic4");
    				}
    			});

    			Message message = new MimeMessage(session);
    			message.setFrom(new InternetAddress("autisticteam4@gmail.com"));
    			message.setRecipients(Message.RecipientType.TO,
    					InternetAddress.parse(to));
    			message.setSubject(subject);
    			message.setText(text);

    			Transport.send(message);
        	
            System.out.println("SENT EMAIL: TO=" + to + "|SUBJECT:" + subject + "|TEXT:" + text);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    public void sendResetPassword(String to, String token) {
        String url = appUrl + "/user/reset-password-change?token=" + token;
        String subject = "Reset Password";
        String text = "Please click the following link to reset your password: " + url;
        sendMail(to, subject, text);
    }
    
    public void sendUserDetails(String to, User user) {
        String subject = "Account details";
        String text = "Registred account details are: /n" + "UseName: "+ user.getUserName() + "/n"+ "Email: "+user.getEmail();
        //no. of users in group
        sendMail(to, subject, text);
    }
    
    public void sendNewRegistration(String to, String token) {
        String url = appUrl + "/user/activate?activation=" + token;
        String subject = "Please activate your account";
        String text = "Please click the following link to activate your account: " + url;
        sendMail(to, subject, text);
    }
    
    public void sendNewActivationRequest(String to, String token) {
        sendNewRegistration(to, token);
    }
    
    public void sendErrorEmail(Exception e, HttpServletRequest req, User user) {
        String subject = "Application Error: " + req.getRequestURL();
        String text = "An error occured in your application: " + e + "\r\nFor User:  " + user.getEmail();
        sendMail(supportEmail, subject, text);
    }
}
