
package com.collabnex.service;

import com.collabnex.domain.message.Message;
import com.collabnex.domain.message.MessageThread;
import com.collabnex.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {
    MessageThread startThread(User creator, Long participantUserId);
    Message send(User sender, Long threadId, String body);
    Page<Message> history(Long threadId, Pageable pageable);
}
