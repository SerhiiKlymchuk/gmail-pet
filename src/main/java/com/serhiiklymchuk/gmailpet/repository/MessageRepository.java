package com.serhiiklymchuk.gmailpet.repository;

import com.serhiiklymchuk.gmailpet.domain.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> findAllByReceiverUserIdOrderByDateDesc(Long receiverUserId, Pageable pageable);

    List<Message> findAllBySenderUserIdOrderByDateDesc(Long senderUserId, Pageable pageable);

    List<Message> findAllByReceiverUserIdAndSubjectContains(Long senderUserId, String searchQuery, Pageable pageable);

    List<Message> findAllBySenderUserIdAndSubjectContains(Long senderUserId, String searchQuery, Pageable pageable);

    Long countAllByReceiverUserIdAndSubjectContains(Long id, String searchQuery);

    Long countAllBySenderUserIdAndSubjectContains(Long id, String searchQuery);

    Long countAllByReceiverUserId(Long id);

    Long countAllBySenderUserId(Long id);
}
