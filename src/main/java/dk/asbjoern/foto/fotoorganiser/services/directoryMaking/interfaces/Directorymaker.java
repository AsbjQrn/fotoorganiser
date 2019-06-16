package dk.asbjoern.foto.fotoorganiser.services.directoryMaking.interfaces;

import dk.asbjoern.foto.fotoorganiser.beans.Image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface Directorymaker {


    String makeDirectory(Image image) throws IOException;

    default void makeIfNotExists(String directory) throws IOException {

        Path path = Paths.get(directory);

        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }
}
