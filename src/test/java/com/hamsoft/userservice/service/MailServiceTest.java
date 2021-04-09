package com.hamsoft.userservice.service;

import com.hamsoft.userservice.config.ApplicationProperties;
import com.hamsoft.userservice.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

/**
 * @project onewa-user-service
 * @Author kabiruahmed on 04/04/2021
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
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
        doNothing().when(javaMailSender).send(any(MimeMessage.class));
        mailService = new MailService(properties, javaMailSender, messageSource, templateEngine);
    }

    @Test
    void sendEmail() throws Exception {
        mailService.sendEmail("opeyemi.kabiru@yahoo.com", "testInvite", "inviteContent", false, false);
        verify(javaMailSender).send(messageCaptor.capture());
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
        verify(javaMailSender).send(messageCaptor.capture());
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
        verify(javaMailSender).send(messageCaptor.capture());
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
        verify(javaMailSender).send(messageCaptor.capture());
        MimeMessage message = messageCaptor.getValue();
        assertThat(message.getAllRecipients()[0].toString()).hasToString(user.getEmail());
        assertThat(message.getFrom()[0].toString()).hasToString(properties.getMail().getFrom());
        assertThat(message.getContent().toString()).isNotEmpty();
        assertThat(message.getDataHandler().getContentType()).isEqualTo("text/html;charset=UTF-8");
    }
}