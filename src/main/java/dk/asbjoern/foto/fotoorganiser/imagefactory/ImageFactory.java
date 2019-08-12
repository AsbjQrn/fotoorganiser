package dk.asbjoern.foto.fotoorganiser.imagefactory;

import dk.asbjoern.foto.fotoorganiser.beans.Image;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public interface ImageFactory {

    Image createImage(Path path, Path sourcePath) throws IOException;

}
