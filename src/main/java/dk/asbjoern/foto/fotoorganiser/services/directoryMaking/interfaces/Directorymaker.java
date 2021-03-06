package dk.asbjoern.foto.fotoorganiser.services.directoryMaking.interfaces;

import dk.asbjoern.foto.fotoorganiser.beans.Image;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface Directorymaker {





    Path makeDirectory(Image image) throws IOException;

    default Path makeIfNotExists(Path path) throws IOException {

        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        return path;
    }
}
