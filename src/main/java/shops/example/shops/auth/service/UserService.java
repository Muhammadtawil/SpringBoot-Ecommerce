
package shops.example.shops.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shops.example.shops.auth.dto.ProfileUpdateDto;
import shops.example.shops.auth.dto.UserDTO;
import shops.example.shops.auth.entity.User;
import shops.example.shops.auth.entity.enums.UserRole;
import shops.example.shops.auth.repository.UserRepository;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;

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

      public User updateProfile(ProfileUpdateDto profileUpdateDto) throws IllegalAccessException {
        // Get the currently authenticated user
        User currentUser = authenticationService.getCurrentUser();
        
        // Check if the user exists (in this case, it should always exist, since we fetch the current user)
        Optional<User> userOpt = userRepository.findByEmail(currentUser.getEmail());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();
        
        // Get the fields to update from the DTO
        Map<String, Object> fieldsToUpdate = profileUpdateDto.getFields();
        
        // Iterate over the fields in the Map
        for (Map.Entry<String, Object> entry : fieldsToUpdate.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();

            // Check if the field exists in the User entity and update it
            try {
                Field field = User.class.getDeclaredField(fieldName);
                field.setAccessible(true); // Allows us to access private fields
                
                // Set the value if the field is not null and the type matches
                if (value != null && field.getType().isAssignableFrom(value.getClass())) {
                    field.set(user, value); // Update the field dynamically
                }
            } catch (NoSuchFieldException e) {
                // Field doesn't exist in the User class, log a warning or ignore it
                System.out.println("Field " + fieldName + " doesn't exist in the User class.");
            }
        }

        // Save the updated user object to the database
        userRepository.save(user);

        // Return the updated user (or a UserDTO if you prefer)
        return user;
    }

 
}
