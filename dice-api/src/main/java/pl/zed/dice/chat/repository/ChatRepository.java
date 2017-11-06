package pl.zed.dice.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zed.dice.chat.domain.Chat;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    Chat findChatByParticipants(Long firstParticipantId, Long secondParticipantId);
    List<Chat> findChatWithParticipant(Long participantId);
}
