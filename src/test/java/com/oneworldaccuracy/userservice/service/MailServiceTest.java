package com.oneworldaccuracy.userservice.service;

import com.oneworldaccuracy.userservice.config.ApplicationProperties;
import com.oneworldaccuracy.userservice.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

/**
 * @project onewa-user-service
 * @Author kabiruahmed on 04/04/2021
 */
@SpringBootTest
class MailServiceTest {

    @Autowired
    private ApplicationProperties properties;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Spy
    private JavaMailSenderImpl javaMailSender;

    @Captor
    private ArgumentCaptor<MimeMessage> messageCaptor;

    private MailService mailService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        doNothing().when(javaMailSender).send(any(MimeMessage.class));
        mailService = new MailService(properties, javaMailSender, messageSource, templateEngine);
    }

    @Test
    void sendEmail() throws Exception {
        mailService.sendEmail("opeyemi.kabiru@yahoo.com", "testInvite", "inviteContent", false, false);
        Mockito.verify(javaMailSender).send(messageCaptor.capture());
        MimeMessage message = messageCaptor.getValue();
        assertThat(message.getSubject()).isEqualTo("testInvite");
        assertThat(message.getContent().toString()).hasToString("inviteContent");
        assertThat(message.getAllRecipients()[0].toString()).hasToString("opeyemi.kabiru@yahoo.com");
        assertThat(message.getFrom()[0].toString()).hasToString(properties.getMail().getFrom());
        assertThat(message.getDataHandler().getContentType()).isEqualTo("text/plain; charset=UTF-8");
    }


    @Test
    void testSendEmailFromTemplate() throws Exception {
        var user = new User();
        user.setFirstName("Ahmed");
        user.setLastName("Kabiru");
        user.setEmail("opeyemi.kabiru@yahoo.com");
        mailService.sendEmailFromTemplate(user, "verificationEmail", "email.activation.title");
        Mockito.verify(javaMailSender).send(messageCaptor.capture());
        MimeMessage message = messageCaptor.getValue();
        assertThat(message.getAllRecipients()[0].toString()).hasToString(user.getEmail());
        assertThat(message.getFrom()[0].toString()).hasToString(properties.getMail().getFrom());
        assertThat(message.getDataHandler().getContentType()).isEqualTo("text/html;charset=UTF-8");
    }



    @Test
    void sendVerificationEmail() throws  Exception {
        var user = new User();
        user.setFirstName("Ahmed");
        user.setLastName("Kabiru");
        user.setEmail("opeyemi.kabiru@yahoo.com");
        mailService.sendVerificationEmail(user);
        Mockito.verify(javaMailSender).send(messageCaptor.capture());
        MimeMessage message = messageCaptor.getValue();
        assertThat(message.getAllRecipients()[0].toString()).hasToString(user.getEmail());
        assertThat(message.getFrom()[0].toString()).hasToString(properties.getMail().getFrom());
        assertThat(message.getContent().toString()).isNotEmpty();
        assertThat(message.getDataHandler().getContentType()).isEqualTo("text/html;charset=UTF-8");
    }

    @Test
    void sendDeactivationEmail() throws  Exception{
        User user = new User();
        user.setFirstName("Ahmed");
        user.setLastName("Kabiru");
        user.setEmail("opeyemi.kabiru@yahoo.com");
        mailService.sendDeactivationEmail(user);
        Mockito.verify(javaMailSender).send(messageCaptor.capture());
        MimeMessage message = messageCaptor.getValue();
        assertThat(message.getAllRecipients()[0].toString()).hasToString(user.getEmail());
        assertThat(message.getFrom()[0].toString()).hasToString(properties.getMail().getFrom());
        assertThat(message.getContent().toString()).isNotEmpty();
        assertThat(message.getDataHandler().getContentType()).isEqualTo("text/html;charset=UTF-8");
    }
}