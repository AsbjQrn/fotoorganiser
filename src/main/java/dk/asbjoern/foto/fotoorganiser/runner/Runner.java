package dk.asbjoern.foto.fotoorganiser.runner;

import dk.asbjoern.foto.fotoorganiser.configuration.PathsToReadFrom;
import dk.asbjoern.foto.fotoorganiser.services.FileMapper;
import dk.asbjoern.foto.fotoorganiser.services.FileMover;
import dk.asbjoern.foto.fotoorganiser.services.SimpleFileVisitorDefault;
import dk.asbjoern.foto.fotoorganiser.services.interfaces.Directorymaker;
import dk.asbjoern.foto.fotoorganiser.services.interfaces.ExifDateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

@Component
public class Runner {

    private PathsToReadFrom pathsToReadFrom;

    private FileMapper fileMapper;
    private FileMover fileMover;
    private ExifDateService exifDateService;
    private Directorymaker directorymaker;

    @Value("${billedbiblioteker}")
    private String[] billedbiblioteker;

    public Runner(FileMapper fileMapper, PathsToReadFrom pathsToReadFrom, FileMover fileMover, ExifDateService exifDateService, Directorymaker directorymaker) {
        this.fileMapper = fileMapper;
        this.pathsToReadFrom = pathsToReadFrom;
        this.fileMover = fileMover;
        this.exifDateService = exifDateService;
        this.directorymaker = directorymaker;
    }

    public void run() throws IOException {


        FileVisitor fileVisitor = new SimpleFileVisitorDefault();

//        Files.walkFileTree( Paths.get(billedbiblioteker[0]), fileVisitor);

        Files.walk(Paths.get(billedbiblioteker[0])).forEach(p -> {

            File file = p.toFile();

            if(file.isFile()) {
                LocalDate localDate = exifDateService.readExif(file);
                try {
                    directorymaker.makeDirectoryPath(localDate);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println();
            }

        });






        
    }

// exifDateService.readExif();

//    fileMover.moveFile();
//    System.out.println(this.billedbiblioteker.toString());
//
//    List<String> bibs = Arrays.asList(billedbiblioteker);
//
//
//    HashMap<Long, List<File>> imagesSameSize = new HashMap<Long, List<File>>();
//
//        for (String bib : billedbiblioteker)
//            fileMapper.mapBasedOnLength(imagesSameSize, Paths.get(bib));


}
