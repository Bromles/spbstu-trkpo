package trkpo.spbstu.hospitalavailability.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;
import trkpo.spbstu.hospitalavailability.exception.BackendUnavailableException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class NotificationMailSenderTests {

    @Mock
    private MailSender mailSender;
    private NotificationMailSender notificationMailSender;
    private static final String USER_MAIL = "user@example.com";
    private static final String SUBJECT = RandomStringUtils.randomAlphabetic(5);
    private static final String MESSAGE  = RandomStringUtils.randomAlphabetic(5);

    @BeforeEach
    void setup() {
        notificationMailSender = new NotificationMailSender(mailSender);
    }

    @Test
    void testSendMessage() {
        notificationMailSender.sendMessage(USER_MAIL, SUBJECT, MESSAGE);
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testRecoverySendMessage() {
        MailException mailExceptionMock = mock(MailException.class);
        BackendUnavailableException thrown = assertThrows(BackendUnavailableException.class,
                () -> notificationMailSender.recoverSendMessage(USER_MAIL, MESSAGE, mailExceptionMock));
        assertEquals("Cannot send email to user: " + USER_MAIL, thrown.getMessage(), "Incorrect message");
    }
}

