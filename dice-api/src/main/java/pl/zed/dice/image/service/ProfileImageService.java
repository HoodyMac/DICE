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
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProfileImageService {
    private static final char DOT_SEPARATOR = '.';
    private static final String PNG_EXTENSION = "png";
    private static final int MAX_WIDTH = 800;

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

        String token;

        InputStream inputStream = file.getInputStream();
        BufferedImage uploadedImage = ImageIO.read(inputStream);
        inputStream.close();
        int width = uploadedImage.getWidth();
        int height = uploadedImage.getHeight();

        if (width > MAX_WIDTH) {
            int desiredWidth = MAX_WIDTH;
            int desiredHeight = height * width / MAX_WIDTH;

            BufferedImage scaledImage = getScaledImage(uploadedImage, desiredWidth, desiredHeight);

            File croppedImage = new File(Files.getNameWithoutExtension(file.getName()) + DOT_SEPARATOR + PNG_EXTENSION);
            ImageIO.write(scaledImage, PNG_EXTENSION, croppedImage);
            token = saveProfileImage(croppedImage);
            if (!croppedImage.delete()) {
                croppedImage.deleteOnExit();
            }
        } else {

            token = saveProfileImage(file);
        }
        UserProfile currentUserProfile = securityContextService.getCurrentUserProfile();
        String imageToDeleteFileName = currentUserProfile.getOrigImage();
        currentUserProfile.setOrigImage(token);
        userProfileRepository.save(currentUserProfile);

        if (!Objects.isNull(imageToDeleteFileName) && userProfileRepository.findByOrigImageOrCropImage(imageToDeleteFileName).equals(0)) {
            storageService.delete(imageToDeleteFileName);
        }

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

        String imageToDeleteFileName = currentUserProfile.getCropImage();
        currentUserProfile.setCropImage(token);
        userProfileRepository.save(currentUserProfile);

        if (!Objects.isNull(imageToDeleteFileName)
                && userProfileRepository.findByOrigImageOrCropImage(imageToDeleteFileName).equals(0)) {
            storageService.delete(imageToDeleteFileName);
        }

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

    private BufferedImage getScaledImage(BufferedImage src, int w, int h) {
        int finalw = w;
        int finalh = h;
        double factor = 1.0d;
        if (src.getWidth() > src.getHeight()) {
            factor = ((double) src.getHeight() / (double) src.getWidth());
            finalh = (int) (finalw * factor);
        } else {
            factor = ((double) src.getWidth() / (double) src.getHeight());
            finalw = (int) (finalh * factor);
        }

        BufferedImage resizedImg = new BufferedImage(finalw, finalh, BufferedImage.TRANSLUCENT);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(src, 0, 0, finalw, finalh, null);
        g2.dispose();
        return resizedImg;
    }
}
