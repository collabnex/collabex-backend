
package com.collabnex.controller;

import com.collabnex.common.dto.ApiResponse;
import com.collabnex.domain.post.Post;
import com.collabnex.domain.post.PostComment;
import com.collabnex.domain.user.User;
import com.collabnex.service.PostService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<Post>> create(@AuthenticationPrincipal User user, @RequestBody CreatePost req) {
        return ResponseEntity.ok(ApiResponse.ok(postService.create(user, req.getContent())));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Post>>> feed(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.ok(postService.feed(pageable)));
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<ApiResponse<List<PostComment>>> comments(@PathVariable Long postId) {
        return ResponseEntity.ok(ApiResponse.ok(postService.comments(postId)));
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<ApiResponse<PostComment>> comment(@AuthenticationPrincipal User user,
                                                            @PathVariable Long postId,
                                                            @RequestBody CreateComment req) {
        return ResponseEntity.ok(ApiResponse.ok(postService.comment(user, postId, req.getBody())));
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<ApiResponse<Void>> like(@AuthenticationPrincipal User user, @PathVariable Long postId) {
        postService.like(user, postId);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }

    @Data
    public static class CreatePost { private String content; }
    @Data
    public static class CreateComment { private String body; }
}
