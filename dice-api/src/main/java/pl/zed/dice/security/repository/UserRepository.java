package pl.zed.dice.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zed.dice.security.domain.UserAccount;

public interface UserRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findByEmail(String email);
}
