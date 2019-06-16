package dk.asbjoern.foto.fotoorganiser.services.directoryMaking.interfaces;

public interface DirectoryMakerFactory {


    Directorymaker produceDirectoryMaker(boolean exifOrNoExif);
}
