package shops.example.shops.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shops.example.shops.auth.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

     @Modifying
    @Query("UPDATE User u SET u.otp = :otp, u.otpGenerationTime = :otpGenerationTime WHERE u.email = :email")
    void updateOtp(@Param("email") String email, @Param("otp") String otp, @Param("otpGenerationTime") long otpGenerationTime);
    

    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.email = :email")
    void updatePassword(@Param("email") String email, @Param("password") String password);
    
}