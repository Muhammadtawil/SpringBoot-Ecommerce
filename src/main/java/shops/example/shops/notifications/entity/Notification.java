package shops.example.shops.notifications.entity;

import java.sql.Date;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import shops.example.shops.auth.entity.User;

@Entity
@Data
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name="message_body",nullable = false)
    private String message;

      @CreationTimestamp
    @Column(updatable = false, name = "send_at")
    private Date sendAt;

    @Column(name = "is_read", nullable = true)
    private boolean read=false;


    
 
}
