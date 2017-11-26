package pl.zed.dice.image.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.zed.dice.image.model.ProfileImageCropDTO;
import pl.zed.dice.image.model.ProfileImageResponseDTO;
import pl.zed.dice.image.service.ProfileImageService;
import pl.zed.dice.image.storage.StorageService;

import java.io.IOException;

@RestController
@RequestMapping("/api/profile/image")
public class ProfileImageController {

    @Autowired
    private ProfileImageService profileImageService;

    @Autowired
    private StorageService storageService;

    @PostMapping
    public ResponseEntity<ProfileImageResponseDTO> uploadProfileImage(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = profileImageService.saveOriginalProfileImage(file);
        return ResponseEntity
                .ok()
                .body(new ProfileImageResponseDTO(fileName));
    }

    @PostMapping("/crop")
    public ResponseEntity<ProfileImageResponseDTO> cropProfileImage(@RequestBody ProfileImageCropDTO profileImageCropDTO) throws IOException {
        String fileName = profileImageService.createAndSaveCropedProfileImage(profileImageCropDTO);
        return ResponseEntity
                .ok()
                .body(new ProfileImageResponseDTO(fileName));
    }

    @GetMapping("/get/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }
}
