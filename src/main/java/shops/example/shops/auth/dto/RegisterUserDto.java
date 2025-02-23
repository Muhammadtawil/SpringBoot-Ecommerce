package shops.example.shops.auth.dto;

import shops.example.shops.auth.entity.enums.UserRole;
import shops.example.shops.auth.entity.enums.UserStatus;

public class RegisterUserDto {
    private String email;
    private String password;
    private String userName;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private UserRole userRole = UserRole.USER; // Default value
    private UserStatus userStatus = UserStatus.ACTIVE; // Default value 

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public RegisterUserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterUserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public RegisterUserDto setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public RegisterUserDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public RegisterUserDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public RegisterUserDto setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public RegisterUserDto setUserRole(UserRole userRole) {
        this.userRole = userRole;
        return this;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public RegisterUserDto setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
        return this;
    }
}
