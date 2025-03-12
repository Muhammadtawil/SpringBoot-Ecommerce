package shops.example.shops.auth.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shops.example.shops.auth.dto.LoginUserDto;
import shops.example.shops.auth.dto.RegisterUserDto;
import shops.example.shops.auth.entity.User;
import shops.example.shops.auth.responses.LoginResponse;
import shops.example.shops.auth.service.AuthenticationService;
import shops.example.shops.auth.service.JwtService;



@RequestMapping("/api/v1/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
    
        String jwtToken = jwtService.generateToken(authenticatedUser);
    
        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());
    
        return ResponseEntity.ok(loginResponse);
    }
    

}