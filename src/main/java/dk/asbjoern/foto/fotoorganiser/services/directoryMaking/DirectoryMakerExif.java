package dk.asbjoern.foto.fotoorganiser.services.directoryMaking;


import dk.asbjoern.foto.fotoorganiser.beans.Image;
import dk.asbjoern.foto.fotoorganiser.services.directoryMaking.interfaces.Directorymaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;


@Service
class DirectoryMakerExif implements Directorymaker {


    @Value("${tilBibliotek}")
    String tilBibliotek;



    @Override
    public String makeDirectory(Image image) throws IOException {


        StringBuilder stringBuilder = new StringBuilder(tilBibliotek);


        stringBuilder.append("/");
        stringBuilder.append(image.getDateTaken().get().getYear());
        stringBuilder.append("/");
        stringBuilder.append(image.getDateTaken().get().getMonth());

        String newLocation = stringBuilder.toString();

        makeIfNotExists(newLocation);

        return newLocation;


    }


}