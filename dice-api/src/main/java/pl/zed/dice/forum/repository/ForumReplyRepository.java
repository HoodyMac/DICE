package pl.zed.dice.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zed.dice.forum.domain.ForumReply;

@Repository
public interface ForumReplyRepository extends JpaRepository<ForumReply, Long> {
}
