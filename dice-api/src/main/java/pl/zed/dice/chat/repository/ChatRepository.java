package pl.zed.dice.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zed.dice.chat.domain.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
}
