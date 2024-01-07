package ec.edu.espam.api.caja.controller;

import ec.edu.espam.api.caja.domain.Cuenta;
import ec.edu.espam.api.caja.service.CuentaServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/cuentas")
public class CuentaControlador {

    private final CuentaServicio cuentaServicio;

    @GetMapping
    public ResponseEntity<List<Cuenta>> obtenerTodos() {
        return ResponseEntity.ok(cuentaServicio.obtenerTodos());
    }

    @PostMapping
    public ResponseEntity<Cuenta> guardar(@RequestBody @Valid Cuenta cuenta) {
        return new ResponseEntity<>(cuentaServicio.guardar(cuenta), HttpStatus.CREATED);
    }

}
