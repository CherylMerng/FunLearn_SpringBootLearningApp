package sg.nus.iss.facialrecognition.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import sg.nus.iss.facialrecognition.model.User;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

@Component
public class EmailSender {
    @Autowired
    private JavaMailSender emailSender;
    @Value("${app.url}")
    private String appUrl;
    @Value("${app.email.support}")
    private String supportEmail;


    public void sendSimpleMessage(String from, String to, Long feedbackId, String reply) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject("FunLearn- Reply to Feedback #" + feedbackId);
        helper.setText(reply);
        emailSender.send(message);
    }
    public void sendMail(String to,String subject,String text){
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(new InternetAddress("autistic4@gmail.com"));
            helper.setTo(InternetAddress.parse(to));
            helper.setSubject("FunLearn- Register Account #" + subject);
            helper.setText(text);
            emailSender.send(message);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    public void sendMail(String to, String subject, String text,String secondText) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(new InternetAddress("autistic4@gmail.com"));
            helper.setTo(InternetAddress.parse(to));
            helper.setSubject("FunLearn- Register Account #" + subject);
            helper.setText(text+secondText);
            emailSender.send(message);

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
        String text = "Registred account details are: \r\n" + "UseName: "+ user.getUserName() + "\r\n"+ "Email: "+user.getEmail();
        //no. of users in group
        sendMail(to, subject, text);
    }

    public void sendNewRegistration(String to, String token,User user) {
        String url = appUrl + "/user/activate?activation=" + token;
        String subject = "Please activate your account";
        String activation = "Please click the following link to activate your account: " + url+"\r\n";
        String userDetails = "Registered account details are: \r\n" + "Username: "+ user.getUserName() + "\r\n"+ "Email: "+user.getEmail();
        sendMail(to, subject, activation,userDetails);
    }


    public void sendNewActivationRequest(String to, String token,User user) {
        sendNewRegistration(to, token,user);
    }

    public void sendErrorEmail(Exception e, HttpServletRequest req, User user) {
        String subject = "Application Error: " + req.getRequestURL();
        String text = "An error occured in your application: " + e + "\r\nFor User:  " + user.getEmail();
        sendMail(supportEmail, subject, text);
    }
}
