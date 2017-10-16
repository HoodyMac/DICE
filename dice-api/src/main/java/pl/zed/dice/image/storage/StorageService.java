package pl.zed.dice.image.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void store(MultipartFile file);

    void store(MultipartFile file, String token);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    Boolean delete(String filename);

    void deleteAll();

}
