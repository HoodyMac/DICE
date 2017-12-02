package pl.zed.dice.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zed.dice.forum.domain.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
