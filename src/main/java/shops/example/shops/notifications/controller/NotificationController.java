package shops.example.shops.notifications.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shops.example.shops.notifications.DTO.NotificationDTO;
import shops.example.shops.notifications.service.NotificationService;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

 
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }



   // Get unread notifications as NotificationDTOs
   @GetMapping("/me/unread")
   public List<NotificationDTO> getUnreadNotifications() {
       return notificationService.getUnreadNotifications();
   }

    @PutMapping("/read/{notificationId}")
    public void markAsRead(@PathVariable UUID notificationId) {
        notificationService.markAsRead(notificationId);
    }
}
