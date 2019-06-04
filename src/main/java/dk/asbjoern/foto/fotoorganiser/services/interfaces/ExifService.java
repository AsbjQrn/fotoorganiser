package dk.asbjoern.foto.fotoorganiser.services.interfaces;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Optional;

public interface ExifService {

    Optional<LocalDate> readExif(File file);
}
