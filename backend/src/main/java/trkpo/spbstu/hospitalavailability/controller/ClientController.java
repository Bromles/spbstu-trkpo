package trkpo.spbstu.hospitalavailability.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import trkpo.spbstu.hospitalavailability.service.ClientService;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/v1/client")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = {"http://localhost:4200"})
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/{keycloak_uuid}")
    public void create(@PathVariable @NotNull String keycloak_uuid) {
        clientService.create(keycloak_uuid);
    }
}
