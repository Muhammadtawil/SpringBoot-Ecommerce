package shops.example.shops.auth.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ResetPasswordDto {
    private String email;
    private String otp;
    private String newPassword;
}
