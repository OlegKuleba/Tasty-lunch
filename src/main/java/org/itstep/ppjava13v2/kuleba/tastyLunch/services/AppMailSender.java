package org.itstep.ppjava13v2.kuleba.tastyLunch.services;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;


@Service
public class AppMailSender {

    MailSender mailSender;

    public void sendMailFromApp(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender = new JavaMailSenderImpl();
        mailSender.send(message);
    }

}
