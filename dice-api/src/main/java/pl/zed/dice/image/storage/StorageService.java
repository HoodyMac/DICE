package pl.zed.dice.image.storage;

import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void store(InputStream inputStream, String token);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    Boolean delete(String filename);

    void deleteAll();

}
