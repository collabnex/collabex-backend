
package com.collabnex.controller;

import com.collabnex.common.dto.ApiResponse;
import com.collabnex.domain.message.Message;
import com.collabnex.domain.message.MessageThread;
import com.collabnex.domain.user.User;
import com.collabnex.service.MessageService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/threads")
    public ResponseEntity<ApiResponse<MessageThread>> start(@AuthenticationPrincipal User user, @RequestBody StartThread req) {
        return ResponseEntity.ok(ApiResponse.ok(messageService.startThread(user, req.getParticipantUserId())));
    }

    @PostMapping("/threads/{threadId}/send")
    public ResponseEntity<ApiResponse<Message>> send(@AuthenticationPrincipal User user, @PathVariable Long threadId, @RequestBody SendMessage req) {
        return ResponseEntity.ok(ApiResponse.ok(messageService.send(user, threadId, req.getBody())));
    }

    @GetMapping("/threads/{threadId}")
    public ResponseEntity<ApiResponse<Page<Message>>> history(@PathVariable Long threadId, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.ok(messageService.history(threadId, pageable)));
    }

    @Data public static class StartThread { private Long participantUserId; }
    @Data public static class SendMessage { private String body; }
}
