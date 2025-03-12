
package shops.example.shops.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import shops.example.shops.auth.dto.LoginUserDto;
import shops.example.shops.auth.dto.RegisterUserDto;
import shops.example.shops.auth.entity.User;
import shops.example.shops.auth.repository.UserRepository;
import shops.example.shops.notifications.service.NotificationService;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final NotificationService notificationService;


    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,NotificationService notificationService ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.notificationService = notificationService;
    }

    public User signup(RegisterUserDto input) {
        User user = new User()
                .setUsername(input.getUserName())
                .setEmail(input.getEmail())
                .setFirstName(input.getFirstName())
                .setLastName(input.getLastName())
                .setPhoneNumber(input.getPhoneNumber())
                .setPassword(passwordEncoder.encode(input.getPassword()))
                .setUserRole(input.getUserRole())
                .setUserStatus(input.getUserStatus());
        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()));
    
        // Get the authenticated user (use email to find the user)
        User authenticatedUser = userRepository.findByEmail(input.getEmail()).orElseThrow();
    
        // Send a welcome notification to the user
        String welcomeMessage = "New Sign In, Welcome Again, " + authenticatedUser.getUsername() + "!";
        notificationService.sendNotification(authenticatedUser, welcomeMessage);
    
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
}
