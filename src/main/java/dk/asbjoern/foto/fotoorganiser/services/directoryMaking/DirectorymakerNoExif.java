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

        //Hvis base og billedPath er lige lange betyder det at billedet nye lokation skal være i parentdirectory
        return makeIfNotExists(image.getBasePath().getNameCount() == image.getPathOriginalLocation().getNameCount() ? config.getNoExifPath() : config.getNoExifPath().resolve(image.getPathOriginalLocation().subpath(image.getBasePath().getNameCount(), image.getPathOriginalLocation().getNameCount())));

    }
}
