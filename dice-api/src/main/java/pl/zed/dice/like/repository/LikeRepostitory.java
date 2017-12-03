package pl.zed.dice.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zed.dice.like.domain.LikesEntity;
import pl.zed.dice.post.domain.Post;
import pl.zed.dice.user.profile.domain.UserProfile;

@Repository
public interface LikeRepostitory extends JpaRepository<LikesEntity, Long> {

    LikesEntity findOneByPostAndUser(Post post, UserProfile user);

}
