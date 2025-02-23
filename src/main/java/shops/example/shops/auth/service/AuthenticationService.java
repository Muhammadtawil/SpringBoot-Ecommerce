
package shops.example.shops.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import shops.example.shops.auth.dto.LoginUserDto;
import shops.example.shops.auth.dto.RegisterUserDto;
import shops.example.shops.auth.entity.User;
import shops.example.shops.auth.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
}
