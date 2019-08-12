package dk.asbjoern.foto.fotoorganiser

import dk.asbjoern.foto.fotoorganiser.services.filewriting.FileWriter
import dk.asbjoern.foto.fotoorganiser.services.filewriting.FileWriterWithNameCheck
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class TestFileWriterWithNameCheck extends Specification {


    def "Opret en røvfuld filer og forsøg at overskrive dem med de samme navne - da skal der eksistrer filer med de samme navne dog med ændret 'slutning'"(){


        FileWriter fileWriter = new FileWriterWithNameCheck();


        when:
        String nytFilNavn = fileWriter.writeFile("tmp", "startFil.tst")

        then:
        nytFilNavn == "startFil.tst-renamed1"

    }


}
