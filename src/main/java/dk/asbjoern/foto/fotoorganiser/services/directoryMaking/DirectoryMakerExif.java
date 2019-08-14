package dk.asbjoern.foto.fotoorganiser.services.directoryMaking;


import dk.asbjoern.foto.fotoorganiser.beans.Image;
import dk.asbjoern.foto.fotoorganiser.configuration.YMLConfiguration;
import dk.asbjoern.foto.fotoorganiser.services.directoryMaking.interfaces.Directorymaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
class DirectoryMakerExif implements Directorymaker {


    private YMLConfiguration config;

    public DirectoryMakerExif(YMLConfiguration ymlConfiguration) {
        this.config = ymlConfiguration;
    }

    @Override
    public Path makeDirectory(Image image) throws IOException {


        Path yearMonthPath = Paths.get(Integer.toString(image.getDateTaken().get().getYear()), image.getDateTaken().get().getMonth().toString());
        Path newLocation = config.getDestinationsPath().resolve(yearMonthPath);

        makeIfNotExists(newLocation);

        return newLocation;


    }


}
