package ec.edu.espam.api.caja.controller;

import ec.edu.espam.api.caja.domain.Account;
import ec.edu.espam.api.caja.domain.dto.AccountDto;
import ec.edu.espam.api.caja.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/cuentas")
public class AccountController {

    private final AccountService service;

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<AccountDto> create(@RequestBody @Valid AccountDto account) {
        return new ResponseEntity<>(service.create(account), HttpStatus.CREATED);
    }

}
