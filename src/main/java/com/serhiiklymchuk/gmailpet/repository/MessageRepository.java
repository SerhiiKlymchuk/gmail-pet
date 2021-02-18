package com.serhiiklymchuk.gmailpet.repository;

import com.serhiiklymchuk.gmailpet.domain.Message;
import com.serhiiklymchuk.gmailpet.domain.MessageStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> findAllByReceiverUserIdAndReceiveStatus(Long receiverUserId, MessageStatus status, Pageable pageable);

    List<Message> findAllBySenderUserIdAndSendStatus(Long senderUserId, MessageStatus status, Pageable pageable);

    List<Message> findAllByReceiverUserIdAndSubjectContainsAndReceiveStatus(
            Long senderUserId, String searchQuery, MessageStatus status, Pageable pageable);

    List<Message> findAllBySenderUserIdAndSubjectContainsAndSendStatus(
            Long senderUserId, String searchQuery, MessageStatus status, Pageable pageable);

    List<Message> findAllBySenderUserIdAndSendStatusOrReceiverUserIdAndReceiveStatus(
            Long senderUserId, MessageStatus sendStatus, Long receiverUserId,
            MessageStatus receiveStatus, Pageable pageable
    );

    Long countAllByReceiverUserIdAndSubjectContainsAndReceiveStatus(Long id, String searchQuery, MessageStatus status);

    Long countAllBySenderUserIdAndSubjectContainsAndSendStatus(Long id, String searchQuery, MessageStatus status);

    Long countAllByReceiverUserIdAndReceiveStatus(Long id, MessageStatus status);

    Long countAllBySenderUserIdAndSendStatus(Long id, MessageStatus status);

    Long countAllBySenderUserIdAndSendStatusOrReceiverUserIdAndReceiveStatus(
            Long senderUserId, MessageStatus sendStatus, Long receiverUserId, MessageStatus receiveStatus);

    @Modifying
    @Query("UPDATE MESSAGE SET receive_status= :receiveStatus WHERE id = :id")
    void updateReceiveStatus(@Param("receiveStatus") MessageStatus receiveStatus, @Param("id") Long id);

    @Modifying
    @Query("UPDATE MESSAGE SET send_status= :sendStatus WHERE id = :id")
    void updateSendStatus(@Param("sendStatus") MessageStatus receiveStatus, @Param("id") Long id);

}
