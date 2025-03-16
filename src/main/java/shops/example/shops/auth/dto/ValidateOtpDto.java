package shops.example.shops.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateOtpDto {
    private String email;
    private String otp;

}
