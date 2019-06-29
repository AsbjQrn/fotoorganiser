package dk.asbjoern.foto.fotoorganiser.runner;

import dk.asbjoern.foto.fotoorganiser.beans.Image;
import dk.asbjoern.foto.fotoorganiser.imagefactory.ImageFactory;
import dk.asbjoern.foto.fotoorganiser.services.LinuxCommandExecuter;
import dk.asbjoern.foto.fotoorganiser.services.interfaces.CommandBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Component
public class Runner {

    private LinuxCommandExecuter linuxCommandExecuter;
    private CommandBuilder commandBuilder;
    private ImageFactory imageFactory;
    private int fileCounter;
    private int duplicateCounter;
    private int originalCounter;
    private int directoryCounter;
    private int totalCounter;
    ;

    @Value("${billedbiblioteker}")
    private String[] billedbiblioteker;

    @Value("${tilBibliotek}")
    String tilBibliotek = "";

    public Runner(ImageFactory imageFactory, LinuxCommandExecuter linuxCommandExecuter, CommandBuilder commandBuilder) {
        this.imageFactory = imageFactory;
        this.linuxCommandExecuter = linuxCommandExecuter;
        this.commandBuilder = commandBuilder;
    }

    public void run() {


        try {
            Map<String, List<String>> moveCommands = new HashMap<>();

            for (String sourceBibliotek : billedbiblioteker) {

                Files.walk(Paths.get(sourceBibliotek)).forEach(path -> {

                    totalCounter++;


                    if (path.toFile().isFile()) {
                        fileCounter++;


                        Image image = null;
                        try {
                            image = imageFactory.createImage(path, sourceBibliotek);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (commandBuilder.addToCommandMap(image.getMd5sum(), image.getOriginalLocation(), image.createAndGetNewLocation(), moveCommands) != null) {
                            duplicateCounter++;
                        } else {
                            originalCounter++;
                        }

                    } else {

                        directoryCounter++;
                    }

                });
            }

            System.out.println("Starter billedflytning");
            moveCommands.entrySet().stream().forEach(e ->

                    linuxCommandExecuter.executeCommand(e.getValue())

            );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Laver testoptælling");

        Set<String> testSetSource = new HashSet();

        for (String sourceBibliotek : billedbiblioteker) {

            try {


                Files.walk(Paths.get(sourceBibliotek)).forEach(path -> {

                    File fil = path.toFile();

                    if (fil.isFile()) {
                        testSetSource.add(linuxCommandExecuter.executeCommand(Arrays.asList("md5sum", fil.getAbsolutePath())));
                    }
                });


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Set<String> testSetDestination = new HashSet();

        try {


            Files.walk(Paths.get(tilBibliotek)).forEach(path -> {

                File fil = path.toFile();

                if (fil.isFile()) {
                    testSetDestination.add(linuxCommandExecuter.executeCommand(Arrays.asList("md5sum", fil.getAbsolutePath())));
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(String.format("Antal total(mapper og filer): %d,  Antal mapper: %d, Antal filer: %d, Antal duplikater: %d, antal originaler: %d ", totalCounter, directoryCounter, fileCounter, duplicateCounter, originalCounter));
        System.out.println("Kildebibliotekerne indeholder " +  testSetSource.size() +  " antal unikke filer og destinationsmappen indeholder " + testSetDestination.size() + " antal unikke filer.");

        testSetSource.removeAll(testSetDestination);



    }
}
