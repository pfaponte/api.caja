package ec.edu.espam.api.caja.controller;

import ec.edu.espam.api.caja.domain.dto.ClientDto;
import ec.edu.espam.api.caja.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/clientes")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDto> create(@Valid @RequestBody ClientDto client) {
        return new ResponseEntity<>(clientService.create(client), HttpStatus.CREATED);
    }
}
