package shops.example.shops.auth.entity;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import shops.example.shops.auth.entity.enums.UserRole;
import shops.example.shops.auth.entity.enums.UserStatus;
import shops.example.shops.orders.entity.Order;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_user_email", columnList = "email"),
        @Index(name = "idx_user_username", columnList = "username")
})
@Data
@NoArgsConstructor
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    @jakarta.validation.constraints.Size(max = 15, message = "First name should not exceed 15 characters")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @jakarta.validation.constraints.Size(max = 15, message = "Last name should not exceed 15 characters")
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true)
    @jakarta.validation.constraints.Pattern(regexp = "^\\d+$", message = "Phone number should be numeric")
    private String phoneNumber;

    @Embedded
    private Address address;

    @Column(name = "profile_image_url", nullable = true)
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus userStatus;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Order> orders;

    @Column(name = "otp", nullable = true)
    private String otp;

    @Column(name = "otp_generation_time", nullable = true)
    private long otpGenerationTime;

    @Column(name = "is_online", nullable = false)
    private boolean isOnline;
    // Constructor
    public User(String username, String email, String firstName, String lastName, String phoneNumber, String password,
            UserRole userRole, UserStatus userStatus, String otp, long otpGenerationTime) { 
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userRole = userRole;
        this.userStatus = userStatus;
        this.otp = otp;
        this.otpGenerationTime = otpGenerationTime;
    }

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities == null) {
            authorities = List.of(new SimpleGrantedAuthority(userRole.name()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
