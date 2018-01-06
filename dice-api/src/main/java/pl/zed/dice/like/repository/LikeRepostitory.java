package pl.zed.dice.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zed.dice.forum.domain.ForumQuestion;
import pl.zed.dice.forum.domain.ForumReply;
import pl.zed.dice.like.domain.LikesEntity;
import pl.zed.dice.post.domain.Post;
import pl.zed.dice.user.profile.domain.UserProfile;

@Repository
public interface LikeRepostitory extends JpaRepository<LikesEntity, Long> {

    LikesEntity findOneByPostAndUser(Post post, UserProfile user);
    LikesEntity findOneByForumQuestionAndUser(ForumQuestion forumQuestion, UserProfile user);
    LikesEntity findOneByForumReplyAndUser(ForumReply forumReply, UserProfile user);

}
