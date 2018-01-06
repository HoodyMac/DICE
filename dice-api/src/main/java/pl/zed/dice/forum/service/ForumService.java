package pl.zed.dice.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zed.dice.forum.asm.ForumAsm;
import pl.zed.dice.forum.domain.ForumQuestion;
import pl.zed.dice.forum.domain.ForumReply;
import pl.zed.dice.forum.domain.Tag;
import pl.zed.dice.forum.model.*;
import pl.zed.dice.forum.repository.ForumQuestionRepository;
import pl.zed.dice.forum.repository.ForumReplyRepository;
import pl.zed.dice.forum.repository.TagRepository;
import pl.zed.dice.like.asm.LikeAsm;
import pl.zed.dice.like.model.LikeDTO;
import pl.zed.dice.user.profile.asm.UserAsm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForumService {

    @Autowired
    private ForumQuestionRepository forumQuestionRepository;

    @Autowired
    private ForumReplyRepository forumReplyRepository;

    @Autowired
    private ForumAsm forumAsm;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private LikeAsm likeAsm;

    @Autowired
    private UserAsm userAsm;

    public ForumQuestionViewDTO createForumQuestion(ForumQuestionCreateDTO forumQuestionCreateDTO) {
        ForumQuestion forumQuestion = forumAsm.makeForumQuestion(forumQuestionCreateDTO);
        forumQuestionRepository.save(forumQuestion);
        return forumAsm.makeForumQuestionViewDTO(forumQuestion);
    }

    public List<ForumQuestionViewDTO> getAllForumQuestions() {
        List<ForumQuestionViewDTO> forumQuestionViewDTOS = new ArrayList<>();
        forumQuestionRepository.findAll().forEach(forumQuestion -> {
            List<LikeDTO> likes = getAndConvertLikes(forumQuestion);
            ForumQuestionViewDTO forumQuestionViewDTO = forumAsm.makeForumQuestionViewDTO(forumQuestion);
            forumQuestionViewDTO.setLikes(likes);
            forumQuestionViewDTOS.add(forumQuestionViewDTO);
        });
        return forumQuestionViewDTOS;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public ForumQuestionDetailsDTO getForumQuestion(Long postId) {
        ForumQuestion forumQuestion = forumQuestionRepository.getOne(postId);
        ForumQuestionDetailsDTO forumQuestionDetailsDTO = forumAsm.makeForumQuestionDetailsDTO(forumQuestion);
        forumQuestionDetailsDTO.setLikes(getAndConvertLikes(forumQuestion));
        return forumQuestionDetailsDTO;
    }

    public ForumReplyViewDTO replyToQuestion(ForumReplyCreateDTO forumReplyCreateDTO, Long questionId) {
        ForumQuestion forumQuestion = forumQuestionRepository.getOne(questionId);
        ForumReply forumReply = forumAsm.makeForumReply(forumReplyCreateDTO);
        forumQuestion.getReplies().add(forumReply);
        forumReply.setForumQuestion(forumQuestion);
        forumReplyRepository.save(forumReply);
        return forumAsm.makeForumReplyViewDTO(forumReply);
    }

    private List<LikeDTO> getAndConvertLikes(ForumQuestion question){
        return question.getLikesEntities().stream()
                .map(like -> likeAsm.makeLikeDTO(like, userAsm.makeUserProfileDTO(like.getUser())))
                .collect(Collectors.toList());
    }
}
