
package com.collabnex.domain.post;

import com.collabnex.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "posts")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false) @JoinColumn(name="author_user_id")
    private User author;

    @Column(nullable=false, length=280)
    private String content;

    @Column(name="likes_count", nullable=false)
    private Integer likesCount = 0;

    @Column(name="comments_count", nullable=false)
    private Integer commentsCount = 0;

    @Column(name="created_at", nullable=false, updatable=false)
    private Instant createdAt;

    @PrePersist
    public void prePersist() { this.createdAt = Instant.now(); }
}
