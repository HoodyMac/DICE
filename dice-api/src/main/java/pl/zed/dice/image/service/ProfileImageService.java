package pl.zed.dice.image.service;

import com.google.common.io.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.zed.dice.image.model.ProfileImageCropDTO;
import pl.zed.dice.image.storage.StorageService;
import pl.zed.dice.security.service.SecurityContextService;
import pl.zed.dice.user.profile.domain.UserProfile;
import pl.zed.dice.user.profile.repository.UserProfileRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class ProfileImageService {
    private static final char DOT_SEPARATOR = '.';
    public static final String PNG_EXTENSION = "png";

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private SecurityContextService securityContextService;

    @Autowired
    private StorageService storageService;


    public void saveOriginalProfileImage(MultipartFile file) throws IOException {
        String token = saveProfileImage(multipartToFile(file));
        UserProfile currentUserProfile = securityContextService.getCurrentUserProfile();
        currentUserProfile.setOrigImage(token);
        userProfileRepository.save(currentUserProfile);
    }

    public void createAndSaveCropedProfileImage(ProfileImageCropDTO profileImageCropDTO) {
        UserProfile currentUserProfile = securityContextService.getCurrentUserProfile();
        File originalImage = storageService.load(currentUserProfile.getOrigImage()).toFile();

        try {
            BufferedImage bufferedOriginalImage = ImageIO.read(originalImage);
            BufferedImage bufferedCroppedImage = bufferedOriginalImage.getSubimage(
                    profileImageCropDTO.getX(),
                    profileImageCropDTO.getY(),
                    profileImageCropDTO.getW(),
                    profileImageCropDTO.getH()
            );

            File croppedImage = new File(Files.getNameWithoutExtension(originalImage.getName()) + DOT_SEPARATOR + PNG_EXTENSION);
            ImageIO.write(bufferedCroppedImage, PNG_EXTENSION, croppedImage);
            String token = saveProfileImage(croppedImage);

            currentUserProfile.setCropImage(token);
            userProfileRepository.save(currentUserProfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String saveProfileImage(File file) {
        if (file == null) {
            throw new IllegalArgumentException("File is null");
        }
        String token = null;
        try {
            token = DigestUtils.md5DigestAsHex(new FileInputStream(file))
                    + DOT_SEPARATOR
                    + Files.getFileExtension(file.getName()).toLowerCase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        storageService.store(file, token);
        return token;
    }

    private File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File resultFile = new File(multipart.getOriginalFilename());
        multipart.transferTo(resultFile);
        return resultFile;
    }

}
