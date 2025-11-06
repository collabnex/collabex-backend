
package com.collabnex.service;

import com.collabnex.domain.post.Post;
import com.collabnex.domain.post.PostComment;
import com.collabnex.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PostService {
    Post create(User author, String content);
    Page<Post> feed(Pageable pageable);
    List<PostComment> comments(Long postId);
    PostComment comment(User author, Long postId, String body);
    void like(User user, Long postId);
}
