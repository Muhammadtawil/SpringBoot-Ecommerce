package shops.example.shops.auth.dto;

import lombok.Data;
import java.util.Map;

@Data
public class ProfileUpdateDto {
    private Map<String, Object> fields; // This will hold the field names and values to be updated
}
