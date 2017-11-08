package pl.zed.dice.security.asm;

import org.springframework.stereotype.Component;
import pl.zed.dice.security.domain.UserAccount;
import pl.zed.dice.security.model.UserInfoDTO;

@Component
public class UserAccountAsm {

    public UserInfoDTO convertAccountToUserInfoDTO(UserAccount userAccount) {
        return new UserInfoDTO(
                userAccount.getEmail(),
                userAccount.getProfile().getFirstname(),
                userAccount.getProfile().getLastname(),
                userAccount.getProfile().getGender(),
                userAccount.getId(),
                userAccount.getProfile().getId()
        );
    }
}
