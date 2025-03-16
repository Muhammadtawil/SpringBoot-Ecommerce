package shops.example.shops.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shops.example.shops.auth.dto.ProfileUpdateDto;
import shops.example.shops.auth.dto.UserDTO;
import shops.example.shops.auth.entity.User;
import shops.example.shops.auth.service.CustomUserDetails;
import shops.example.shops.auth.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User currentUser = customUserDetails.getUser();
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> allUsers() {
        List<UserDTO> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }

     @PutMapping("/update-profile")
    public User updateProfile(@RequestBody ProfileUpdateDto profileUpdateDto) throws IllegalAccessException {
        return userService.updateProfile(profileUpdateDto);
    }
}
