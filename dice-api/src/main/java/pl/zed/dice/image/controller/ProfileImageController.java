package pl.zed.dice.image.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.zed.dice.image.service.ProfileImageService;
import pl.zed.dice.image.storage.StorageService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/profile/image/")
public class ProfileImageController {

    @Autowired
    private ProfileImageService profileImageService;

    @Autowired
    private StorageService storageService;

    @PostMapping
    public void upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        profileImageService.saveProfileImage(file);
    }

    @GetMapping("{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }
}
