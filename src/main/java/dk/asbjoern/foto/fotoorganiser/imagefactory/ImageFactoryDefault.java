package dk.asbjoern.foto.fotoorganiser.imagefactory;

import dk.asbjoern.foto.fotoorganiser.beans.Image;
import dk.asbjoern.foto.fotoorganiser.services.LinuxCommandExecuter;
import dk.asbjoern.foto.fotoorganiser.services.directoryMaking.interfaces.DirectoryMakerFactory;
import dk.asbjoern.foto.fotoorganiser.services.directoryMaking.interfaces.Directorymaker;
import dk.asbjoern.foto.fotoorganiser.services.interfaces.ExifService;
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
    public Image createImage(Path path, Path sourcePath) throws IOException {

        Image image = new Image();

        image.setBasePath(sourcePath);

        image.setPathOriginalLocation(path.getParent());

        image.setFilename(path.getFileName());

        image.setMetadata(exifService.readExif(path.toFile()));

        if (image.getMetadata().isPresent()) {
            image.setDateTaken(exifService.readExifDate(image.getMetadata()));
        }

        String outputFromLinux = linuxCommandExecuter.executeCommand(Arrays.asList("md5sum", image.getFilePathOriginalLocationAsString()));
        String md5sum =  outputFromLinux.substring(0,outputFromLinux.indexOf(" "));
        image.setMd5sum(md5sum);

        image.setPathToNewLocation(makeNewDirectory(image));

        return image;
    }

    private Path makeNewDirectory(Image image) throws IOException {
        Directorymaker directorymaker = directoryMakerFactory.produceDirectoryMaker(image.getDateTaken().isPresent());
        return directorymaker.makeDirectory(image);
    }
}
