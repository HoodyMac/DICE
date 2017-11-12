package pl.zed.dice.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zed.dice.chat.domain.Attachment;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
