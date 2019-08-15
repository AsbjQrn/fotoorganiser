package dk.asbjoern.foto.fotoorganiser

import dk.asbjoern.foto.fotoorganiser.services.filewriting.FileWriter
import dk.asbjoern.foto.fotoorganiser.services.filewriting.FileWriterWithNameCheck
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class FileWriterWithNameCheckSpecification extends Specification {


    def "Test writeFile"() {

        given:
        FileWriter fileWriter = new FileWriterWithNameCheck();
        Path source = Paths.get("src/test/files1/file1")
        Path newLocation = Paths.get("src/test/folder/")
        Path filename = source.getFileName()

        when:
        fileWriter.writeFile(source, newLocation, filename)


        then:
        Path newLocationWithFile = newLocation.resolve(filename)
        Files.exists(newLocationWithFile)
        newLocationWithFile.getFileName().equals(Paths.get("file1"))
        println newLocationWithFile.toAbsolutePath().toString()

        when:
        fileWriter.writeFile(source, newLocation, filename)


        then:
        Files.exists(newLocation.resolve(Paths.get(filename.toString() + "(1)")))

        cleanup:
        Files.delete(newLocationWithFile)
        Files.delete(newLocation.resolve(Paths.get(filename.toString() + "(1)")))


    }

    def "Test makeNewFileName"() {

        FileWriter fileWriter = new FileWriterWithNameCheck();

        when:
        Path nytFilNavn = fileWriter.makeNewFileName(Paths.get('filUdenEfterNavn'))

        then:
        nytFilNavn.equals(Paths.get('filUdenEfterNavn(1)'))
        noExceptionThrown()

        when:
        fileWriter.renameNumber = 0;
        nytFilNavn = fileWriter.makeNewFileName(Paths.get('fil.A.jpg'))

        then:
        noExceptionThrown()
        nytFilNavn.equals(Paths.get('filA(1).jpg'))

    }


}
