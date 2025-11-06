
package com.collabnex.domain.message;

import com.collabnex.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "message_threads")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MessageThread {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false) @JoinColumn(name="created_by_user_id")
    private User createdBy;

    @Column(name="created_at", nullable=false, updatable=false)
    private Instant createdAt;

    @PrePersist public void pre(){ this.createdAt = Instant.now(); }
}
