package pl.zed.dice.forum.asm;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.zed.dice.forum.domain.ForumQuestion;
import pl.zed.dice.forum.domain.ForumReply;
import pl.zed.dice.forum.domain.Tag;
import pl.zed.dice.forum.model.*;
import pl.zed.dice.forum.repository.TagRepository;
import pl.zed.dice.security.service.SecurityContextService;
import pl.zed.dice.user.profile.domain.UserProfile;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ForumAsm {

    @Autowired
    private SecurityContextService securityContextService;

    @Autowired
    private TagRepository tagRepository;

    public ForumQuestion makeForumQuestion(ForumQuestionCreateDTO forumQuestionCreateDTO) {
        List<Tag> tags = Lists.newArrayList();
        if (forumQuestionCreateDTO.getTags() != null) {
            for (Long tagId : forumQuestionCreateDTO.getTags()) {
                Tag tag = tagRepository.getOne(tagId);
                tags.add(tag);
            }
        }
        UserProfile currentUserProfile = securityContextService.getCurrentUserProfile();
        return new ForumQuestion(
                forumQuestionCreateDTO.getTitle(),
                forumQuestionCreateDTO.getDescription(),
                new Date(),
                currentUserProfile,
                tags);
    }

    public ForumQuestionViewDTO makeForumQuestionViewDTO(ForumQuestion forumQuestion) {
        List<TagViewDTO> tags = forumQuestion.getTags().stream().map(tag -> new TagViewDTO(tag.getId(), tag.getTitle())).collect(Collectors.toList());
        return new ForumQuestionViewDTO(
                forumQuestion.getId(),
                forumQuestion.getTitle(),
                forumQuestion.getAuthor().getFullname(),
                tags,
                forumQuestion.getCreatedAt());
    }

    public ForumQuestionDetailsDTO makeForumQuestionDetailsDTO(ForumQuestion forumQuestion) {
        List<TagViewDTO> tags = forumQuestion.getTags().stream().map(tag -> new TagViewDTO(tag.getId(), tag.getTitle())).collect(Collectors.toList());
        List<ForumReplyViewDTO> replies = forumQuestion.getReplies().stream().map(this::makeForumReplyViewDTO).collect(Collectors.toList());
        return new ForumQuestionDetailsDTO(
                forumQuestion.getId(),
                forumQuestion.getTitle(),
                forumQuestion.getAuthor().getFullname(),
                tags,
                forumQuestion.getCreatedAt(),
                forumQuestion.getContent(),
                replies);
    }

    public ForumReply makeForumReply(ForumReplyCreateDTO forumReplyCreateDTO) {
        UserProfile currentUserProfile = securityContextService.getCurrentUserProfile();
        return new ForumReply(forumReplyCreateDTO.getContent(), currentUserProfile, new Date());
    }

    public ForumReplyViewDTO makeForumReplyViewDTO(ForumReply forumReply) {
        return new ForumReplyViewDTO(
                forumReply.getId(),
                forumReply.getContent(),
                forumReply.getAuthor().getId(),
                forumReply.getAuthor().getFullname(),
                forumReply.getCreatedAt());
    }
}
