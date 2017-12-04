package pl.zed.dice.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zed.dice.forum.asm.ForumAsm;
import pl.zed.dice.forum.domain.ForumQuestion;
import pl.zed.dice.forum.domain.Tag;
import pl.zed.dice.forum.model.ForumQuestionCreateDTO;
import pl.zed.dice.forum.model.ForumQuestionDetailsDTO;
import pl.zed.dice.forum.model.ForumQuestionViewDTO;
import pl.zed.dice.forum.repository.ForumQuestionRepository;
import pl.zed.dice.forum.repository.TagRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForumService {

    @Autowired
    private ForumQuestionRepository forumQuestionRepository;

    @Autowired
    private ForumAsm forumAsm;

    @Autowired
    private TagRepository tagRepository;

    public ForumQuestionViewDTO createForumQuestion(ForumQuestionCreateDTO forumQuestionCreateDTO) {
        ForumQuestion forumQuestion = forumAsm.makeForumQuestion(forumQuestionCreateDTO);
        forumQuestionRepository.save(forumQuestion);
        return forumAsm.makeForumQuestionViewDTO(forumQuestion);
    }

    public List<ForumQuestionViewDTO> getAllForumQuestions() {
        return forumQuestionRepository.findAll()
                .stream()
                .map(forumAsm::makeForumQuestionViewDTO)
                .collect(Collectors.toList());
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public ForumQuestionDetailsDTO getForumQuestion(Long postId) {
        ForumQuestion forumQuestion = forumQuestionRepository.getOne(postId);
        return forumAsm.makeForumQuestionDetailsDTO(forumQuestion);
    }
}
