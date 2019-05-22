package dk.asbjoern.foto.fotoorganiser.services.interfaces;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;

public interface ExifDateService {

    LocalDate readExif(File file);
}
