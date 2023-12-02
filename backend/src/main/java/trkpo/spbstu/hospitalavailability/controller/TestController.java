package trkpo.spbstu.hospitalavailability.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trkpo.spbstu.hospitalavailability.service.NotificationMailSender;

@RestController
@RequestMapping("/v1/test")
@RequiredArgsConstructor
@Validated
public class TestController {
    private final NotificationMailSender sender;

    @PostMapping("/sendMessage")
    public ResponseEntity<String> sendMail() {
        sender.sendMessage("spbstu.trkpo@yandex.ru", "Я работаю!");
        return ResponseEntity.ok().build();
    }
}
