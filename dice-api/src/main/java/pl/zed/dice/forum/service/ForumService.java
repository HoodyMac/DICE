package pl.zed.dice.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zed.dice.forum.asm.ForumAsm;
import pl.zed.dice.forum.domain.ForumQuestion;
import pl.zed.dice.forum.domain.Tag;
import pl.zed.dice.forum.model.ForumQuestionCreateDTO;
import pl.zed.dice.forum.model.ForumQuestionViewDTO;
import pl.zed.dice.forum.repository.ForumQuestionRepository;
import pl.zed.dice.forum.repository.TagRepository;

import java.util.List;

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

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }
}
