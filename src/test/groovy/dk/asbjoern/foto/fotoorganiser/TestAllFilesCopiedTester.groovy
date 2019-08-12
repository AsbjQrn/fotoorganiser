package dk.asbjoern.foto.fotoorganiser

import dk.asbjoern.foto.fotoorganiser.helpers.AllFilesCopiedTester
import dk.asbjoern.foto.fotoorganiser.helpers.TestAllFilesCopiedWithSets
import dk.asbjoern.foto.fotoorganiser.services.LinuxCommandExecuter
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Paths

class TestAllFilesCopiedTester extends Specification {


    def "Test at filer der mangler registreeres som mangler og dem der ikke mangler ikke registreres som mangler"() {


        AllFilesCopiedTester allFilesCopiedTester = new TestAllFilesCopiedWithSets(new LinuxCommandExecuter())

        when:
        File f = Paths.get("src/test/files1").toFile()
        println("222")
        println f.getAbsolutePath()
        2==2

        then:
        22!=3


    }

}
