package dk.asbjoern.foto.fotoorganiser

import dk.asbjoern.foto.fotoorganiser.services.CommandMapBuilder
import dk.asbjoern.foto.fotoorganiser.services.interfaces.CommandBuilder
import spock.lang.Specification

class TestCommandWriter extends Specification {


    def "Test at duplikater forårsager returværdi"() {

        Map<String, List<String>> mapOfCommands = new HashMap<>();
        CommandBuilder builder = new CommandMapBuilder();

        when:
        Optional o = builder.addToCommandMap("checksum", "stiFra1", "stiTil1", mapOfCommands)

        then:
        o.isPresent() == false

        when:
        o = builder.addToCommandMap("checksum", "stiFra1", "stiTil1", mapOfCommands)

        then:
        o.isPresent() == true

        when:
        o = builder.addToCommandMap("checksumNy", "stiFra1", "stiTil1", mapOfCommands)

        then:
        o.isPresent() == false


    }

}
