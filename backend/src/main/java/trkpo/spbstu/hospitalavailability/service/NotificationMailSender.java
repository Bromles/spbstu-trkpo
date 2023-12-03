package trkpo.spbstu.hospitalavailability.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import trkpo.spbstu.hospitalavailability.exception.BackendUnavailableException;

import javax.validation.constraints.Email;

@Service
@RequiredArgsConstructor
public class NotificationMailSender {
    private final MailSender mailSender;

    @Retryable(value = MailException.class, maxAttempts = 2, backoff = @Backoff(delay = 100))
    public void sendMessage(@Email String userMail, String message) throws MailException {
        final SimpleMailMessage simpleMail = new SimpleMailMessage();
        String senderEmail = "spbstu.trkpo@yandex.ru";

        simpleMail.setFrom(senderEmail);
        simpleMail.setTo(userMail);
        simpleMail.setSubject("Появились талоны к врачу");
        simpleMail.setText(message);

        this.mailSender.send(simpleMail);
    }

    @Recover
    public void recoverSendMessage(@Email String userMail, String message, MailException e) {
        throw new BackendUnavailableException("Cannot send email to user: " + userMail);
    }
}
