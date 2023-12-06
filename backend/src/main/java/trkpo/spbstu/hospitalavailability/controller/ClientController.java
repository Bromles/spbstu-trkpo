package trkpo.spbstu.hospitalavailability.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import trkpo.spbstu.hospitalavailability.dto.ClientRequestDto;
import trkpo.spbstu.hospitalavailability.service.ClientService;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/client")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = {"http://localhost:4200"})
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    public void create(@RequestBody @Valid ClientRequestDto clientRequestDto) {
        clientService.create(clientRequestDto);
    }
}