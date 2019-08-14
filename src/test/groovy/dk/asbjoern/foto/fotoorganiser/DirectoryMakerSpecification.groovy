package dk.asbjoern.foto.fotoorganiser

import dk.asbjoern.foto.fotoorganiser.beans.Image
import dk.asbjoern.foto.fotoorganiser.configuration.YMLConfiguration
import dk.asbjoern.foto.fotoorganiser.services.directoryMaking.DirectorymakerNoExif
import dk.asbjoern.foto.fotoorganiser.services.directoryMaking.interfaces.Directorymaker
import spock.lang.Specification

import java.nio.file.Paths

class DirectoryMakerSpecification extends Specification {


    def "testFilnavne"() {

        YMLConfiguration configuration = Mock(){
            1 * getNoExifPath() >>  Paths.get('noExif')
        }

        Image image = Mock(){ // /media/asbjoern/925bd160-9cb1-4f8c-b329-1a01a1555203/hovedmappe
            getBasePath() >> Paths.get('/media/asbjoern/925bd160-9cb1-4f8c-b329-1a01a1555203/hovedmappe')
            getPathOriginalLocation() >> Paths.get('/media/asbjoern/925bd160-9cb1-4f8c-b329-1a01a1555203/hovedmappe/gavl')
        }
        Directorymaker directorymaker = new DirectorymakerNoExif(configuration)

        when:
        directorymaker.makeDirectory(image)

        then:
        noExceptionThrown()
        2==2
    }

}
