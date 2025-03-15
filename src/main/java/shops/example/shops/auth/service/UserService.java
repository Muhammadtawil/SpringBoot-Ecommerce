
package shops.example.shops.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shops.example.shops.auth.dto.UserDTO;
import shops.example.shops.auth.entity.User;
import shops.example.shops.auth.entity.enums.UserRole;
import shops.example.shops.auth.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> allUsers() {
        List<UserDTO> users = new ArrayList<>();
        User currentUser = authenticationService.getCurrentUser();
          if(currentUser.getUserRole() !=UserRole.ADMIN && currentUser.getUserRole() !=UserRole.SUPER_ADMIN){
        throw new RuntimeException("You are not authorized to visit this");
    }
        userRepository.findAll().forEach(user -> users.add(new UserDTO(user)));

        return users;
    }
}
