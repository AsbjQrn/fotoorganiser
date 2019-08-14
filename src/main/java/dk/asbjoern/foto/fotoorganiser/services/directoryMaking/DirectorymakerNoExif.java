package dk.asbjoern.foto.fotoorganiser.services.directoryMaking;


import dk.asbjoern.foto.fotoorganiser.beans.Image;
import dk.asbjoern.foto.fotoorganiser.configuration.YMLConfiguration;
import dk.asbjoern.foto.fotoorganiser.services.directoryMaking.interfaces.Directorymaker;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

@Service
class DirectorymakerNoExif implements Directorymaker {



    private YMLConfiguration config;

    public DirectorymakerNoExif(YMLConfiguration config) {
        this.config = config;
    }


    @Override
    public Path makeDirectory(Image image) throws IOException {


        int numberOfNamesInSourceLibrary = image.getSourcePath().getNameCount();
        int numberOfNamesInOriginalLocationOfImage = image.getPathOriginalLocation().getNameCount();

        Path newLocation = config.getNoExifPath().resolve(image.getPathOriginalLocation().subpath(numberOfNamesInSourceLibrary, numberOfNamesInOriginalLocationOfImage ));

        makeIfNotExists(newLocation);

        return newLocation;


    }
}
