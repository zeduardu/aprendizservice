package dev.aprendiz.api.aprendizservice.v1.contact;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.joda.time.DateTime;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController()
@RequestMapping(value = "/contact")
public class ContactController {

  @PostMapping(value = "/sendmessage")
  @ResponseBody
  public String sendEmail(@RequestBody Contact contact){

    final String username = "zeduardu@gmail.com";
    final String password = "qytetkipchlzwoav";
    
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");

    Session session = Session.getInstance(props, new Authenticator(){
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username , password);
      }
    });

    try {
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress("aprentizservice@gmail.com"));
      message.setRecipients(
        Message.RecipientType.TO,
        InternetAddress.parse("zeduardu@gmail.com")
      );
      message.setSubject("Mail subject");
      String msg = "This is my first email using JavaMailer";
      MimeBodyPart mimeBodyPart = new MimeBodyPart();
      mimeBodyPart.setContent(msg, "text/html");
      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(mimeBodyPart);
      message.setContent(multipart);
      // Transport.send(message);
      
      DateTime dateTime = DateTime.now();
      JSONObject json = new JSONObject();
      json.put("code", HttpStatus.OK.value());
      json.put("status", HttpStatus.OK);
      json.put("time", dateTime.toString());
      json.put("message", "Operação concluída com sucesso.");

      return json.toString();

    } catch (MessagingException e) {
      e.printStackTrace();
    }
    
    return new JSONObject("{ \"Erro\" : \"def\" }").toString();
  }
  
}
