package dk.asbjoern.foto.fotoorganiser.services;

import dk.asbjoern.foto.fotoorganiser.services.interfaces.CommandWriter;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommandMapWriter implements CommandWriter {

    public static final String linuxKopi = "cp";

    @Override
    public String makeListOfCommandParameters(String checksum, String stiFra, String stiTil, Map<String,List<String>> mapOfCommands){



        List<String> command = Arrays.asList(linuxKopi, stiFra, stiTil);

        return mapOfCommands.put(checksum, command) ==  null ? null : stiFra ;

    }

}
