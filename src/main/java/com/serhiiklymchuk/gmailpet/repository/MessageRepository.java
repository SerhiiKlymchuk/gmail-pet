package com.serhiiklymchuk.gmailpet.repository;

import com.serhiiklymchuk.gmailpet.domain.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends CrudRepository<Message, Long> {

    Optional<List<Message>> findAllByReceiverUserId(Long receiverUserId);

    Optional<List<Message>> findAllByOwnerUserId(Long ownerUserId);

}
