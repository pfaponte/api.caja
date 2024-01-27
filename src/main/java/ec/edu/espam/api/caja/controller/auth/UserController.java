package ec.edu.espam.api.caja.controller.auth;

import ec.edu.espam.api.caja.domain.auth.AuthRequest;
import ec.edu.espam.api.caja.domain.auth.UserInfo;
import ec.edu.espam.api.caja.service.auth.JwtService;
import ec.edu.espam.api.caja.service.auth.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserInfoService service;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/add-new-user")
    public ResponseEntity<UserInfo> addNewUser(@RequestBody UserInfo userInfo) {
        userInfo.setRoles("ROLE_ADMIN");
        return new ResponseEntity<>(service.addUser(userInfo), HttpStatus.CREATED);
    }

    @PostMapping("/generate-token")
    public ResponseEntity<Map<String, String>> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok(Map.of("token", jwtService.generateToken(authRequest.getUsername())));
        } else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }

    @GetMapping("renew-token")
    public ResponseEntity<Map<String, String>> renewToken(@RequestHeader("Authorization") String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        String username = jwtService.extractUsername(token);

        return ResponseEntity.ok(Map.of("token", jwtService.generateToken(username)));
    }

    private String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
