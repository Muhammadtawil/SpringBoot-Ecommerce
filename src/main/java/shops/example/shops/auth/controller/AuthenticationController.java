package shops.example.shops.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shops.example.shops.auth.dto.ForgotPasswordDto;
import shops.example.shops.auth.dto.LoginUserDto;
import shops.example.shops.auth.dto.RegisterUserDto;
import shops.example.shops.auth.dto.ResetPasswordDto;
import shops.example.shops.auth.dto.ValidateOtpDto;
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
        // Authenticate the user and get the authenticated user object
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        // Generate the JWT token
        String jwtToken = jwtService.generateToken(authenticatedUser);

        // Create a login response
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        // Set the userName from the authenticatedUser, not the loginUserDto
        loginResponse.setUserName(authenticatedUser.getUsername());

        return ResponseEntity.ok(loginResponse);
    }

    // Endpoint for requesting a forgot password OTP
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) {
        String response = authenticationService.forgotPassword(forgotPasswordDto);
        return ResponseEntity.ok(response);
    }

    // Endpoint for validating OTP and resetting the password
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        authenticationService.resetPassword(resetPasswordDto);
        return ResponseEntity.ok("Password reset successfully.");
    }

    // New Endpoint: Validate OTP
    @PostMapping("/validate-otp")
    public ResponseEntity<String> validateOtp(@RequestBody ValidateOtpDto validateOtpDto) {
        boolean isOtpValid = authenticationService.validateOtp(validateOtpDto.getEmail(), validateOtpDto.getOtp());
        if (isOtpValid) {
            return ResponseEntity.ok("OTP validated successfully. You can now reset your password.");
        } else {
            return ResponseEntity.status(400).body("Invalid or expired OTP.");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // Call the service to logout
        authenticationService.logout();

        // Return a success response
        return ResponseEntity.ok("Logged out successfully.");
    }

}