
package com.collabnex.domain.message;

import com.collabnex.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "messages")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Message {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false) @JoinColumn(name="thread_id")
    private MessageThread thread;

    @ManyToOne(optional=false) @JoinColumn(name="sender_user_id")
    private User sender;

    @Column(nullable=false, columnDefinition="TEXT")
    private String body;

    @Column(name="is_read", nullable=false)
    private boolean read = false;

    @Column(name="sent_at", nullable=false, updatable=false)
    private Instant sentAt;

    @PrePersist public void pre(){ this.sentAt = Instant.now(); }
}
