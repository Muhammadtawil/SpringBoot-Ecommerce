package shops.example.shops.auth.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginUserDto {
    private String email;
    private String password;
    private String userName;

}
