package dk.asbjoern.foto.fotoorganiser.services.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CommandBuilder {
    List<String> addToCommandMap(String checksum, String stiFra, String stiTil, Map<String, List<String>> mapOfCommands);
}
