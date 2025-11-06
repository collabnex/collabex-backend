
package com.collabnex.domain.post;

import com.collabnex.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post_likes",
       uniqueConstraints = @UniqueConstraint(name="uq_post_like", columnNames={"post_id","user_id"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PostLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false) @JoinColumn(name="post_id")
    private Post post;

    @ManyToOne(optional=false) @JoinColumn(name="user_id")
    private User user;
}
