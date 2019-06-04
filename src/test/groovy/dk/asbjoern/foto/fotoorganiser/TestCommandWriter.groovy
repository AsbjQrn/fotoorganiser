package dk.asbjoern.foto.fotoorganiser

import dk.asbjoern.foto.fotoorganiser.services.CommandMapBuilder
import dk.asbjoern.foto.fotoorganiser.services.interfaces.CommandBuilder
import spock.lang.Specification

class TestCommandWriter extends Specification {


    def "Test at duplikater forårsager returværdi"() {

        Map<String, List<String>> mapOfCommands = new HashMap<>();
        CommandBuilder builder = new CommandMapBuilder();

        when:
        Object o = builder.addToCommandMap("checksum", "stiFra1", "stiTil1", mapOfCommands)

        then:
        o == null

        when:
        o = builder.addToCommandMap("checksum", "stiFra1", "stiTil1", mapOfCommands)

        then:
        o != null


    }

}
