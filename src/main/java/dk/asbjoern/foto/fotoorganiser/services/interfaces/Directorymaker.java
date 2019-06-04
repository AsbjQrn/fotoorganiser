package dk.asbjoern.foto.fotoorganiser.services.interfaces;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public interface Directorymaker {


    String makeDirectoryPathBasedOnLocalDate(Optional<LocalDate> localDate) throws IOException;
}
