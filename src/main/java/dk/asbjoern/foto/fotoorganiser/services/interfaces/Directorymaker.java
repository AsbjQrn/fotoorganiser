package dk.asbjoern.foto.fotoorganiser.services.interfaces;

import java.io.IOException;
import java.time.LocalDate;

public interface Directorymaker {


    String makeDirectoryPath(LocalDate localDate) throws IOException;
}
