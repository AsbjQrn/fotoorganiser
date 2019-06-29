package dk.asbjoern.foto.fotoorganiser.imagefactory;

import dk.asbjoern.foto.fotoorganiser.beans.Image;
import dk.asbjoern.foto.fotoorganiser.services.LinuxCommandExecuter;
import dk.asbjoern.foto.fotoorganiser.services.directoryMaking.interfaces.DirectoryMakerFactory;
import dk.asbjoern.foto.fotoorganiser.services.directoryMaking.interfaces.Directorymaker;
import dk.asbjoern.foto.fotoorganiser.services.interfaces.ExifService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class ImageFactoryDefault implements ImageFactory {


    private ExifService exifService;
    private DirectoryMakerFactory directoryMakerFactory;


    private LinuxCommandExecuter linuxCommandExecuter;

    public ImageFactoryDefault(ExifService exifService, DirectoryMakerFactory directoryMakerFactory, LinuxCommandExecuter linuxCommandExecuter) {
        this.exifService = exifService;
        this.directoryMakerFactory = directoryMakerFactory;
        this.linuxCommandExecuter = linuxCommandExecuter;
    }

    @Override
    public Image createImage(Path path, String sourceBibliotek) throws IOException {

        Image image = new Image();

        image.setSourceBibliotek(sourceBibliotek);

        image.setParentPathOriginalLocation(path.getParent());

        image.setFilename(path.getFileName());

        image.setOriginalLocation(path.toFile().getAbsolutePath());

        image.setMetadata(exifService.readExif(path.toFile()));

        if (image.getMetadata().isPresent()) {
            image.setDateTaken(exifService.readExifDate(image.getMetadata()));
        }

        image.setMd5sum(linuxCommandExecuter.executeCommand(Arrays.asList("md5sum", image.getOriginalLocation())));

        image.setParentPathToNewLocation(makeNewDirectory(image));

        return image;
    }

    private String makeNewDirectory(Image image) throws IOException {
        Directorymaker directorymaker = directoryMakerFactory.produceDirectoryMaker(image.getDateTaken().isPresent());
        return directorymaker.makeDirectory(image);
    }
}
