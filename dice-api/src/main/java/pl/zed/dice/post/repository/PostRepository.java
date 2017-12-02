package pl.zed.dice.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zed.dice.post.domain.Post;
import pl.zed.dice.user.profile.domain.UserProfile;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

    List<Post> findByAuthorOrderByIdDesc(UserProfile author);

}
