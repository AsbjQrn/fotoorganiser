package dk.asbjoern.foto.fotoorganiser.services.directoryMaking;


import dk.asbjoern.foto.fotoorganiser.beans.Image;
import dk.asbjoern.foto.fotoorganiser.services.directoryMaking.interfaces.Directorymaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
class DirectorymakerNoExif implements Directorymaker {



    @Value("${tilBibliotek}")
    String tilBibliotek;

    @Override
    public String makeDirectory(Image image) throws IOException {

        StringBuilder stringBuilder = new StringBuilder(tilBibliotek);

        stringBuilder.append("/");

        stringBuilder.append("noExif");

        stringBuilder.append(image.getParentPathOriginalLocation().toString().substring(image.getSourceBibliotek().length()));

        String newLocation = stringBuilder.toString();

        makeIfNotExists(newLocation);

        return newLocation;


    }
}
