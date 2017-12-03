package pl.zed.dice.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zed.dice.comment.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
}
