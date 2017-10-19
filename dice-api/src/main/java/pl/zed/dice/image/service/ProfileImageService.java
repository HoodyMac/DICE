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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileImageService {
    private static final char DOT_SEPARATOR = '.';
    private static final String PNG_EXTENSION = "png";

    private final List<String> ALLOWED_EXTENSIONS = new ArrayList<String>() {{
        add("bmp");
        add("jpeg");
        add("jpg");
        add("png");
    }};

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private SecurityContextService securityContextService;

    @Autowired
    private StorageService storageService;


    public String saveOriginalProfileImage(MultipartFile file) throws IOException {
        String extension = Files.getFileExtension(file.getOriginalFilename()).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException(String.format("Can't save file with %s extension", extension));
        }
        String token = saveProfileImage(file);
        UserProfile currentUserProfile = securityContextService.getCurrentUserProfile();
        currentUserProfile.setOrigImage(token);
        userProfileRepository.save(currentUserProfile);
        return token;
    }

    public String createAndSaveCropedProfileImage(ProfileImageCropDTO profileImageCropDTO) throws IOException {
        UserProfile currentUserProfile = securityContextService.getCurrentUserProfile();
        File originalImage = storageService.load(currentUserProfile.getOrigImage()).toFile();


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
        if (!croppedImage.delete()) {
            croppedImage.deleteOnExit();
        }

        currentUserProfile.setCropImage(token);
        userProfileRepository.save(currentUserProfile);

        return token;
    }

    private String saveProfileImage(MultipartFile file) throws IOException {
        String token = generateFilename(file.getInputStream(), file.getOriginalFilename());
        storageService.store(file.getInputStream(), token);
        return token;
    }

    private String saveProfileImage(File file) throws IOException {
        String token = generateFilename(new FileInputStream(file), file.getName());
        storageService.store(new FileInputStream(file), token);
        return token;
    }

    private String generateFilename(InputStream inputStream, String originalFileName) throws IOException {
        String token = DigestUtils.md5DigestAsHex(inputStream)
                + DOT_SEPARATOR
                + Files.getFileExtension(originalFileName).toLowerCase();
        inputStream.close();
        return token;
    }
}
