package shops.example.shops.auth.dto;


import lombok.Getter;
import lombok.Setter;
import shops.example.shops.auth.entity.enums.UserRole;
import shops.example.shops.auth.entity.enums.UserStatus;

@Getter
@Setter
public class RegisterUserDto {
    private String email;
    private String password;
    private String userName;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private UserRole userRole = UserRole.USER; // Default value
    private UserStatus userStatus = UserStatus.ACTIVE; // Default value 

 
}
