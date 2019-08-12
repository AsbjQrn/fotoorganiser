package dk.asbjoern.foto.fotoorganiser.services.filewriting;

import java.io.IOException;

public interface FileWriter {

    void writeFile(String fileName, String FileName) throws IOException;

}
