package dk.asbjoern.foto.fotoorganiser.services.interfaces;

import java.util.List;
import java.util.Map;

public interface CommandWriter {
    String makeListOfCommandParameters(String checksum, String stiFra, String stiTil, Map<String, List<String>> mapOfCommands);
}
