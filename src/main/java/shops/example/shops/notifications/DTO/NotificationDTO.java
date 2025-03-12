package shops.example.shops.notifications.DTO;

import java.sql.Date;
import java.util.UUID;

import lombok.Data;
import shops.example.shops.notifications.entity.Notification;

@Data
public class NotificationDTO {
    private UUID id;
    private String message;
    private Date sendAt;
    private boolean read;

    // Static method to convert Notification entity to NotificationDTO
    public static NotificationDTO fromEntity(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setMessage(notification.getMessage());
        dto.setSendAt(notification.getSendAt());
        dto.setRead(notification.isRead());
        return dto;
    }
}
