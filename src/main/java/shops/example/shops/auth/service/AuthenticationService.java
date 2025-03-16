package shops.example.shops.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.MessagingException;
import shops.example.shops.Email.EmailService;
import shops.example.shops.auth.dto.ForgotPasswordDto;
import shops.example.shops.auth.dto.LoginUserDto;
import shops.example.shops.auth.dto.RegisterUserDto;
import shops.example.shops.auth.dto.ResetPasswordDto;
import shops.example.shops.auth.entity.User;
import shops.example.shops.auth.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            EmailService emailService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    // Step 1: Forgot Password -> Generate OTP and send to email
    @Transactional
    public String forgotPassword(ForgotPasswordDto forgotPasswordDto) {
        Optional<User> userOpt = userRepository.findByEmail(forgotPasswordDto.getEmail());
    
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Email not found");
        }
    
        // Generate a 6-digit OTP
        String generatedOtp = generateOtp();
        long otpGenerationTime = System.currentTimeMillis();
    
        // Get the user's email to update OTP
        String email = forgotPasswordDto.getEmail();
        
        try {
            // Update only the OTP and OTP generation time
            userRepository.updateOtp(email, generatedOtp, otpGenerationTime);  // Custom update method
        } catch (Exception e) {
            logger.error("Error updating OTP for user", e);
            throw new RuntimeException("Error updating OTP for user", e);
        }
    
        // Send OTP to the user's email
        try {
            emailService.sendOtpEmail(forgotPasswordDto.getEmail(), generatedOtp);  // Send email with OTP
        } catch (MessagingException e) {
            logger.error("Error sending OTP email", e);
            throw new RuntimeException("Error sending OTP email");
        }
    
        return "OTP has been sent to your email.";
    }
    

    // Generate a 6-digit OTP
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // 6-digit number
        return String.valueOf(otp);
    }

    // Step 2: Validate OTP
    public boolean validateOtp(String email, String otp) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        logger.info("UserOpt: {}", userOpt);

        if (userOpt.isEmpty()) {
            return false; // User not found
        }

        User user = userOpt.get();

        if (user.getOtp() == null || !user.getOtp().equals(otp)) {
            return false; // OTP does not match
        }

        long otpExpirationTime = 10 * 60 * 1000;  // OTP expires after 10 minutes
        long timePassed = System.currentTimeMillis() - user.getOtpGenerationTime();

        if (timePassed > otpExpirationTime) {
            return false;  // OTP expired
        }

        return true;  // OTP is valid
    }

    // Step 3: Reset the Password
    @Transactional
    public User resetPassword(ResetPasswordDto resetPasswordDto) {
        // Retrieve user by email
        Optional<User> userOpt = userRepository.findByEmail(resetPasswordDto.getEmail());
    
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
    
        User user = userOpt.get();
    
        // If OTP is not passed in the DTO, check the OTP stored in the database
        if (resetPasswordDto.getOtp() == null || !user.getOtp().equals(resetPasswordDto.getOtp())) {
            throw new RuntimeException("Invalid or expired OTP");
        }
    
        // Check if OTP is expired
        long otpExpirationTime = 10 * 60 * 1000;  // 10 minutes
        long timePassed = System.currentTimeMillis() - user.getOtpGenerationTime();
        if (timePassed > otpExpirationTime) {
            throw new RuntimeException("OTP expired");
        }
    
        // Encode the new password
        String encodedPassword = passwordEncoder.encode(resetPasswordDto.getNewPassword());
    
        // Update the password directly via query
        userRepository.updatePassword(resetPasswordDto.getEmail(), encodedPassword);
    
        // Optionally return the updated user or a success message
        return user;
    }
    
    

    public User signup(RegisterUserDto input) {
        User user = new User(
                input.getUserName(),
                input.getEmail(),
                input.getFirstName(),
                input.getLastName(),
                input.getPhoneNumber(),
                passwordEncoder.encode(input.getPassword()), // encode the password here
                input.getUserRole(),
                input.getUserStatus(),
                input.getOtp(),
                input.getOtpGenerationTime()
        );
        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()));

        // Get the authenticated user (use email to find the user)
        User authenticatedUser = userRepository.findByEmail(input.getEmail()).orElseThrow();

        try {
            emailService.prepareAndSendMail(authenticatedUser.getEmail(), authenticatedUser.getUsername());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return authenticatedUser;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails.getUser();
    }


    // Logout method in AuthenticationService
@Transactional
public void logout() {
    // Find the user by email
    // Optional<User> userOpt = userRepository.findByEmail(email);
    // if (userOpt.isEmpty()) {
    //     throw new RuntimeException("User not found");
    // }

    // User user = userOpt.get();
    User currentUser = getCurrentUser();

    // Set the user status to offline
    currentUser.setOnline(false);
    userRepository.save(currentUser);

    // Optionally, invalidate the JWT token or refresh token on the backend if you want (in case you're using a refresh token system)
    // JWT tokens are stateless, so you can't really "invalidate" a token, but you could blacklist it or change the refresh token to invalidate it.
    // For now, this is handled on the frontend by deleting the token.
}
}
