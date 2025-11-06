
package com.collabnex.service.impl;

import com.collabnex.common.exception.NotFoundException;
import com.collabnex.domain.post.*;
import com.collabnex.domain.user.User;
import com.collabnex.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostCommentRepository commentRepository;
    private final PostLikeRepository likeRepository;

    @Override
    @Transactional
    public Post create(User author, String content) {
        Post p = Post.builder().author(author).content(content).build();
        return postRepository.save(p);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Post> feed(Pageable pageable) {
        return postRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostComment> comments(Long postId) {
        return commentRepository.findByPostIdOrderByCreatedAtAsc(postId);
    }

    @Override
    @Transactional
    public PostComment comment(User author, Long postId, String body) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found"));
        PostComment c = PostComment.builder().post(post).author(author).body(body).build();
        post.setCommentsCount(post.getCommentsCount() + 1);
        postRepository.save(post);
        return commentRepository.save(c);
    }

    @Override
    @Transactional
    public void like(User user, Long postId) {
        if (!likeRepository.existsByPostIdAndUserId(postId, user.getId())) {
            Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found"));
            PostLike like = PostLike.builder().post(post).user(user).build();
            likeRepository.save(like);
            post.setLikesCount(post.getLikesCount() + 1);
            postRepository.save(post);
        }
    }
}
