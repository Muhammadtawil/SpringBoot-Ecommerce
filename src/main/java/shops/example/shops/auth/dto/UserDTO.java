package shops.example.shops.auth.dto;

import lombok.Data;
import lombok.ToString;
import shops.example.shops.auth.entity.User;

@Data
@ToString

public class UserDTO {

    public UserDTO(User user) {
        this.email = user.getEmail();
        this.userName = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.userRole = user.getUserRole().name(); // Assuming you want to return the name of the role
        this.userStatus = user.getUserStatus().name(); // Assuming you want to return the name of the status
    }
    private String email;
    private String userName;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String userRole;
    private String userStatus;

}
