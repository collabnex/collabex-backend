
package com.collabnex.service.impl;

import com.collabnex.common.exception.NotFoundException;
import com.collabnex.domain.message.*;
import com.collabnex.domain.user.User;
import com.collabnex.domain.user.UserRepository;
import com.collabnex.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageThreadRepository threadRepo;
    private final ThreadParticipantRepository participantRepo;
    private final MessageRepository messageRepo;
    private final UserRepository userRepo;

    @Override @Transactional
    public MessageThread startThread(User creator, Long participantUserId) {
        var other = userRepo.findById(participantUserId).orElseThrow(() -> new NotFoundException("User not found"));
        MessageThread t = threadRepo.save(MessageThread.builder().createdBy(creator).build());
        participantRepo.save(ThreadParticipant.builder().threadId(t.getId()).userId(creator.getId()).build());
        participantRepo.save(ThreadParticipant.builder().threadId(t.getId()).userId(other.getId()).build());
        return t;
    }

    @Override @Transactional
    public Message send(User sender, Long threadId, String body) {
        MessageThread t = threadRepo.findById(threadId).orElseThrow(() -> new NotFoundException("Thread not found"));
        Message m = Message.builder().thread(t).sender(sender).body(body).build();
        return messageRepo.save(m);
    }

    @Override @Transactional(readOnly = true)
    public Page<Message> history(Long threadId, Pageable pageable) {
        return messageRepo.findByThreadIdOrderBySentAtAsc(threadId, pageable);
    }
}
