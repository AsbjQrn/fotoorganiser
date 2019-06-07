package dk.asbjoern.foto.fotoorganiser.runner;

import dk.asbjoern.foto.fotoorganiser.services.LinuxCommandExecuter;
import dk.asbjoern.foto.fotoorganiser.services.interfaces.CommandBuilder;
import dk.asbjoern.foto.fotoorganiser.services.interfaces.Directorymaker;
import dk.asbjoern.foto.fotoorganiser.services.interfaces.ExifService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@Component
public class Runner {

    private ExifService exifDateService;
    private Directorymaker directorymaker;
    private LinuxCommandExecuter linuxCommandExecuter;
    private CommandBuilder commandBuilder;
    private int fileCounter;
    private int duplicateCounter;
    private int originalCounter;
    private int directoryCounter;

    @Value("${billedbiblioteker}")
    private String[] billedbiblioteker;

    public Runner(ExifService exifDateService, Directorymaker directorymaker, LinuxCommandExecuter linuxCommandExecuter, CommandBuilder commandBuilder) {
        this.exifDateService = exifDateService;
        this.directorymaker = directorymaker;
        this.linuxCommandExecuter = linuxCommandExecuter;
        this.commandBuilder = commandBuilder;
    }

    public void run() throws IOException {


        try {
            Map<String, List<String>> moveCommands = new HashMap<>();

            Files.walk(Paths.get(billedbiblioteker[0])).forEach(p -> {

                File file = p.toFile();

                if (file.isDirectory()) {

                    directoryCounter++;
                }
                if (file.isFile()) {
                    fileCounter++;
                    Optional<LocalDate> localDateOptional = exifDateService.readExif(file);
                    String pathToNewLocation;
                    String md5sum;
                    try {
                        pathToNewLocation = directorymaker.makeDirectoryPathBasedOnLocalDate(localDateOptional);
                        md5sum = linuxCommandExecuter.executeCommand(Arrays.asList("md5sum", file.getAbsolutePath()));
                        if (commandBuilder.addToCommandMap(md5sum, file.getAbsolutePath(), pathToNewLocation, moveCommands) != null) {
                            duplicateCounter++;
                        } else {
                            originalCounter++;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println(String.format("Antal filer: %d, Antal duplikater: %d, antal originaler: %d ", fileCounter, duplicateCounter, originalCounter));
                        System.exit(100);

                    }

                }

            });

            System.out.println();
            moveCommands.entrySet().stream().forEach(e ->

                    linuxCommandExecuter.executeCommand(e.getValue())

            );
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println(String.format("Antal mapper: %d, Antal filer: %d, Antal duplikater: %d, antal originaler: %d ", directoryCounter, fileCounter, duplicateCounter, originalCounter));
            System.exit(0);

        }
    }
}
