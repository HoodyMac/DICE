package pl.zed.dice.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.zed.dice.security.JwtUserFactory;
import pl.zed.dice.security.domain.UserAccount;
import pl.zed.dice.security.repository.UserAccountRepository;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository.findByEmail(username);

        if(userAccount == null) {
            throw new UsernameNotFoundException(String.format("No userAccount found with email: '%s'.", username));
        } else {
            return JwtUserFactory.create(userAccount);
        }
    }
}
