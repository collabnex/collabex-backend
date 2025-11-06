
package com.collabnex.domain.message;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "thread_participants")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@IdClass(ThreadParticipantId.class)
public class ThreadParticipant {

    @Id
    @Column(name="thread_id")
    private Long threadId;

    @Id
    @Column(name="user_id")
    private Long userId;

    @Column(name="last_read_at")
    private Instant lastReadAt;

    @Column(name="muted", nullable=false)
    private boolean muted = false;
}
