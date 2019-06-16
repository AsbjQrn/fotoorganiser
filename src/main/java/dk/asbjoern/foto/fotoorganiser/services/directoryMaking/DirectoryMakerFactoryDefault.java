package dk.asbjoern.foto.fotoorganiser.services.directoryMaking;


import dk.asbjoern.foto.fotoorganiser.services.directoryMaking.interfaces.DirectoryMakerFactory;
import dk.asbjoern.foto.fotoorganiser.services.directoryMaking.interfaces.Directorymaker;
import org.springframework.stereotype.Service;

@Service
class DirectoryMakerFactoryDefault implements DirectoryMakerFactory {


    Directorymaker directorymakerNoExif;
    Directorymaker directoryMakerExif;

    public DirectoryMakerFactoryDefault(DirectorymakerNoExif directorymakerNoExif, DirectoryMakerExif directoryMakerExif) {
        this.directoryMakerExif = directoryMakerExif;
        this.directorymakerNoExif = directorymakerNoExif;
    }

    @Override
    public Directorymaker produceDirectoryMaker(boolean exifDate) {

        if (exifDate) {
            return directoryMakerExif;
        } else {
            return directorymakerNoExif;
        }
    }
}
