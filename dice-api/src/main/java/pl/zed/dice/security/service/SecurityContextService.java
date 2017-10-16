package pl.zed.dice.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.zed.dice.security.domain.UserAccount;
import pl.zed.dice.security.repository.UserAccountRepository;
import pl.zed.dice.user.profile.domain.UserProfile;

@Service
public class SecurityContextService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    public UserAccount getCurrentUserAccount() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userAccountRepository.findByEmail(authentication.getName());
    }

    public UserProfile getCurrentUserProfile() {
        return getCurrentUserAccount().getProfile();
    }
}
