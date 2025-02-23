// package shops.example.shops.auth.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// import shops.example.shops.auth.repository.UserRepository;
// import shops.example.shops.auth.service.CustomUserDetails;



// @Configuration // Indicates that this class provides Spring Beans
// public class ApplicationConfiguration {
//     private final UserRepository userRepository;

//     // Inject UserRepository dependency through the constructor
//     public ApplicationConfiguration(UserRepository userRepository) {
//         this.userRepository = userRepository;
//     }

//     @Bean // Defines a UserDetailsService Bean
//     UserDetailsService userDetailsService() {
//         return username -> 
//             // Fetch user by email; if not found, throw an exception
//             userRepository.findByEmail(username)
//                 .map(CustomUserDetails::new) // Wrap user in CustomUserDetails
//                 .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//     }

//     @Bean // Defines a Bean for password encoding
//     BCryptPasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder(); // Use BCrypt for hashing passwords
//     }

//     @Bean // Creates an AuthenticationManager Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//         return config.getAuthenticationManager(); // Retrieve AuthenticationManager from configuration
//     }

//     @Bean // Defines an AuthenticationProvider Bean
//     AuthenticationProvider authenticationProvider() {
//         DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//         authProvider.setUserDetailsService(userDetailsService()); // Set custom UserDetailsService
//         authProvider.setPasswordEncoder(passwordEncoder()); // Set password encoder
//         return authProvider; // Return the configured provider
//     }
// }
