package pl.zed.dice.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zed.dice.security.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
