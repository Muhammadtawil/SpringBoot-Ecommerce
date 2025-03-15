package shops.example.shops.Email;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import org.thymeleaf.context.Context;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    private static TemplateEngine templateEngine;
    private static  Context context;
    private JavaMailSender emailSender;
    private Logger LOG= LoggerFactory.getLogger(EmailService.class.getName());

    @Autowired
    public void setEmailSender(JavaMailSender emailSender){
        this.emailSender=emailSender;
    }


    @Value("${spring.mail.username}")
    private String senderEmail;

    private String senderName="Clickers";

    public void prepareAndSendMail(String email,String name)throws MessagingException{
        String htmlTemplate = "templates/email-template";
        String to=email;
        String toName=name;
        initializeTemplateEngine();
        // context.setVariable("sender", senderName);
        context.setVariable("sender", "Clickers");
        context.setVariable("to", to);
        context.setVariable("toName", toName);

        String body = templateEngine.process(htmlTemplate, context);
        sendEmail(to, "Welcome to our platform", body,toName);
    }

    private void sendEmail(String to, String subject, String body,String toName) {
        try{
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            helper.setReplyTo(toName);
            helper.setFrom(senderEmail,senderName);
            emailSender.send(message);

            LOG.info("Email sent to: "+to);
        }catch(Exception e){
            LOG.error("Error sending email: "+e.getMessage());
        }
    }


    public static void initializeTemplateEngine(){
ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
resolver.setTemplateMode("HTML");
resolver.setSuffix(".html");
resolver.setCharacterEncoding("UTF-8");
templateEngine=new TemplateEngine();
templateEngine.setTemplateResolver(resolver);
context = new Context(Locale.US);


    }
}
