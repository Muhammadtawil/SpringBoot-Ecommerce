package shops.example.shops.notifications.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import shops.example.shops.auth.entity.User;
import shops.example.shops.auth.service.AuthenticationService;
import shops.example.shops.notifications.DTO.NotificationDTO;
import shops.example.shops.notifications.entity.Notification;
import shops.example.shops.notifications.repository.NotificationRepository;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    
      private final AuthenticationService authenticationService;

    public NotificationService(NotificationRepository notificationRepository,@Lazy AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        this.notificationRepository = notificationRepository;
    }

    // public List<Notification> getAllNotificationsForSuperAdmin(User user) {
    //     // Check if the user has the SuperAdmin role
    //     if (!user.getUserRole().equals("SuperAdmin")) {
    //         throw new SecurityException("You are not authorized to access this resource");
    //     }
    
    //     // If user is SuperAdmin, return all notifications
    //     return notificationRepository.findAll();
    // }
    

    // Create and save a new notification
    public void sendNotification(User user, String message) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);

        notificationRepository.save(notification);
    }

       // Get unread notifications for the current user and return as DTOs
    public List<NotificationDTO> getUnreadNotifications() {
        User currentUser = authenticationService.getCurrentUser();
        UUID userId = currentUser.getId();

        // Get the unread notifications and map them to DTOs
        // List<Notification> notifications = notificationRepository.findByUserIdAndRead(userId, false);
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        return notifications.stream()
                            .map(NotificationDTO::fromEntity)  // Convert each Notification to NotificationDTO
                            .collect(Collectors.toList());
    }

    // Mark a notification as read
    public void markAsRead(UUID notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow();
        notification.setRead(true);
        notificationRepository.save(notification);
    }
}
