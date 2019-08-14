package dk.asbjoern.foto.fotoorganiser.services.filewriting;

import java.io.IOException;
import java.nio.file.Path;

public interface FileWriter {

    void writeFile(Path originalpath, Path newPath, Path filename) throws Exception;

}
