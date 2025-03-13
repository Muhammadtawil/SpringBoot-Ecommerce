package shops.example.shops.Email;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void prepareAndSendMail()throws MessagingException{
        String htmlTemplate = "templates/email-template";
        String to="muhammad.tawil@hotmail.com";
        initializeTemplateEngine();
        context.setVariable("sender", "Clickers");
        context.setVariable("to", to);
        String body = templateEngine.process(htmlTemplate, context);
        sendEmail(to, "Welcome to our platform", body);
    }

    private void sendEmail(String to, String subject, String body){
        try{
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
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
