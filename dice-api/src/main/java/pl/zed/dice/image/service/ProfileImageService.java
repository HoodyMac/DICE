package pl.zed.dice.image.service;

import com.google.common.io.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.zed.dice.image.storage.StorageService;
import pl.zed.dice.security.service.SecurityContextService;
import pl.zed.dice.user.profile.domain.UserProfile;
import pl.zed.dice.user.profile.repository.UserProfileRepository;

import java.io.IOException;

@Service
public class ProfileImageService {
    private static final char DOT_SEPARATOR = '.';

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private SecurityContextService securityContextService;

    @Autowired
    private StorageService storageService;


    public void saveProfileImage(MultipartFile file) {
        if (file == null) {
            return;
        }
        String token = null;
        try {
            token = DigestUtils.md5DigestAsHex(file.getInputStream())
                    + DOT_SEPARATOR
                    + Files.getFileExtension(file.getOriginalFilename()).toLowerCase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserProfile currentUserProfile = securityContextService.getCurrentUserProfile();
        currentUserProfile.setOrigImage(token);
        storageService.store(file, token);
        userProfileRepository.save(currentUserProfile);
    }
}
