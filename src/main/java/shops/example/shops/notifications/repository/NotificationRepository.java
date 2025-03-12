package shops.example.shops.notifications.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shops.example.shops.notifications.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findByUserIdAndRead(UUID userId, boolean read);
    List<Notification> findByUserId(UUID userId);
}
