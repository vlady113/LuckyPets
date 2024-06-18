package com.vladyslav.luckypets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ClassPathResource;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("vgolovatyit01@educantabria.es");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            helper.addInline("image1", new ClassPathResource("static/images/image-1.png"));
            helper.addInline("image6", new ClassPathResource("static/images/image-6.png"));
            helper.addInline("image7", new ClassPathResource("static/images/image-7.png"));

            mailSender.send(message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
}
