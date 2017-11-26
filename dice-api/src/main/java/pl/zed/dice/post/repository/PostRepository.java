package pl.zed.dice.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zed.dice.post.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
}
