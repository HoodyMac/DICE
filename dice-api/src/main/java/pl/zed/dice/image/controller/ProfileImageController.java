package pl.zed.dice.image.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.zed.dice.image.service.ProfileImageService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/profile/image/")
public class ProfileImageController {

    @Autowired
    private ProfileImageService profileImageService;

    @PostMapping
    public void upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        profileImageService.saveProfileImage(file);
    }
}
