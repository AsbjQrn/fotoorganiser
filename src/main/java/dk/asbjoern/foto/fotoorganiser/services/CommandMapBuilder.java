package dk.asbjoern.foto.fotoorganiser.services;

import dk.asbjoern.foto.fotoorganiser.services.interfaces.CommandBuilder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
class CommandMapBuilder implements CommandBuilder {

    public static final String linuxKopi = "cp";

    @Override
    public Optional<List<String>> addToCommandMap(String checksum, String stiFra, String stiTil, Map<String, List<String>> mapOfCommands) {

        List<String> command = Arrays.asList(linuxKopi, stiFra, stiTil);

        return Optional.ofNullable(mapOfCommands.put(checksum, command));

    }

}
