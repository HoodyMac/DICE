package pl.zed.dice.image.model;

public class ProfileImageResponseDTO {

    private String newImageFileName;

    public ProfileImageResponseDTO(String newImageFileName) {
        this.newImageFileName = newImageFileName;
    }

    public String getNewImageFileName() {
        return newImageFileName;
    }

    public void setNewImageFileName(String newImageFileName) {
        this.newImageFileName = newImageFileName;
    }
}
