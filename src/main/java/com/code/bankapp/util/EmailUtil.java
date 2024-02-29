package com.code.bankapp.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailUtil {
    private final JavaMailSender javaMailSender;

    public void sendUserTokenEmail(String email, String token) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Verify Token");
        mimeMessageHelper.setText("""
                <div>
                <p>please not that the link expires within 24hrs </p>
                  <a href="http://localhost:8080/auth/verify-account?email=%s&token=%s" target="_blank">click this  link to verify account </a>
                </div>
                """.formatted(email, token), true);

        javaMailSender.send(mimeMessage);
    }

    public void sendUForgotTokenEmail(String email, String token) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Forgot Password");
        mimeMessageHelper.setText("""
                <div>
                  <a href="http://localhost:8080/auth/reset-password?email=%s&token=%s" target="_blank">click this link to reset password </a>
                </div>
                """.formatted(email, token), true);

        javaMailSender.send(mimeMessage);
    }
}
