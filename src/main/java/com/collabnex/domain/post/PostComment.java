
package com.collabnex.domain.post;

import com.collabnex.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "post_comments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PostComment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false) @JoinColumn(name="post_id")
    private Post post;

    @ManyToOne(optional=false) @JoinColumn(name="author_user_id")
    private User author;

    @Column(nullable=false, columnDefinition="TEXT")
    private String body;

    @Column(name="created_at", nullable=false, updatable=false)
    private Instant createdAt;

    @PrePersist
    public void prePersist(){ this.createdAt = Instant.now(); }
}
