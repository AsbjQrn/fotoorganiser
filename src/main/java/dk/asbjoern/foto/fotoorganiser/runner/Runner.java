package dk.asbjoern.foto.fotoorganiser.runner;

import dk.asbjoern.foto.fotoorganiser.services.LinuxCommandExecuter;
import dk.asbjoern.foto.fotoorganiser.services.interfaces.CommandWriter;
import dk.asbjoern.foto.fotoorganiser.services.interfaces.Directorymaker;
import dk.asbjoern.foto.fotoorganiser.services.interfaces.ExifService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Runner {

    private ExifService exifDateService;
    private Directorymaker directorymaker;
    private LinuxCommandExecuter linuxCommandExecuter;
    private CommandWriter commandWriter;

    @Value("${billedbiblioteker}")
    private String[] billedbiblioteker;

    public Runner(ExifService exifDateService, Directorymaker directorymaker, LinuxCommandExecuter linuxCommandExecuter, CommandWriter commandWriter) {
        this.exifDateService = exifDateService;
        this.directorymaker = directorymaker;
        this.linuxCommandExecuter = linuxCommandExecuter;
        this.commandWriter = commandWriter;
    }

    public void run() throws IOException {


        Map<String, List<String>> moveCommands = new HashMap<>();

        Files.walk(Paths.get(billedbiblioteker[0])).forEach(p -> {

            File file = p.toFile();

            if (file.isFile()) {
                LocalDate localDate = exifDateService.readExif(file);
                String pathToNewLocation;
                String md5sum;
                try {
                    pathToNewLocation = directorymaker.makeDirectoryPath(localDate);
                    md5sum = linuxCommandExecuter.executeCommand(Arrays.asList("md5sum", file.getAbsolutePath()));
                    commandWriter.makeListOfCommandParameters(md5sum, file.getAbsolutePath(), pathToNewLocation, moveCommands);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println();
            }

        });

        System.out.println();
        moveCommands.entrySet().stream().forEach(e ->

                linuxCommandExecuter.executeCommand(e.getValue())

        );

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
