package com.serhiiklymchuk.gmailpet.repository;

import com.serhiiklymchuk.gmailpet.domain.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> findAllByReceiverUserIdOrderByDateDesc(Long receiverUserId);
    List<Message> findAllBySenderUserIdOrderByDateDesc(Long senderUserId);
}
