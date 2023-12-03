package trkpo.spbstu.hospitalavailability.controller;

import lombok.RequiredArgsConstructor;
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
    public void sendMail() {
        sender.sendMessage("spbstu.trkpo@yandex.ru", "Появились талоны к врачу", "Я работаю!");
    }
}
