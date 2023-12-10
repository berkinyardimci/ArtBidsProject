package com.artbids.service;

import com.artbids.model.SendEmailModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;
    @Autowired
    private Configuration config;

    public void sendEmail(SendEmailModel model) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            //helper.addAttachment("logo.png", new ClassPathResource("logo.png"));

            Template t = config.getTemplate("email-template.ftl");

            Map<String, Object> data = new HashMap<>();
            data.put("Email", model.getEmail());
            data.put("Username", model.getUsername());
            data.put("Id", model.getAuthId());
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, data);


            helper.setFrom("berkinyardimci98@gmail.com");
            helper.setTo(model.getEmail());
            helper.setSubject("ArtBids Activation");

            helper.setText(html, true);
            javaMailSender.send(message);


        } catch (MessagingException | IOException | TemplateException e) {
            log.info("Hataaa........");
        }
    }
}
