package dk.asbjoern.foto.fotoorganiser.runner;

import dk.asbjoern.foto.fotoorganiser.beans.Image;
import dk.asbjoern.foto.fotoorganiser.helpers.Loggable;
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
public class Runner implements Loggable {

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


        Map<String, List<String>> moveCommandsMap = new HashMap<>();


        try {


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


                        Optional optional = commandBuilder.addToCommandMap(image.getMd5sum(), image.getOriginalLocation(), image.createAndGetNewLocation(), moveCommandsMap);

                        if (optional.isPresent()) {
                            duplicateCounter++;
                        } else {
                            originalCounter++;
                        }

                    } else {

                        directoryCounter++;
                    }

                });
            }


            moveCommandsMap.entrySet().stream().forEach(e -> {

                        logger().info("STARTKOPI: {}", e.getValue().toString());
                        linuxCommandExecuter.executeCommand(e.getValue());


                    }

            );

            System.out.println("moveCommandsMap size: " + moveCommandsMap.size());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Laver testoptælling");

        Map<String, String> testMapSource = new HashMap<>();

        for (String sourceBibliotek : billedbiblioteker) {

            try {


                Files.walk(Paths.get(sourceBibliotek)).forEach(path -> {

                    File fil = path.toFile();

                    if (fil.isFile()) {
                        testMapSource.put(linuxCommandExecuter.executeCommand(Arrays.asList("md5sum", fil.getAbsolutePath())), fil.getAbsolutePath());
                    }
                });


            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(1);
        }



        System.out.println(String.format("Antal total(mapper og filer): %d,  Antal mapper: %d, Antal filer: %d, Antal duplikater: %d, antal originaler: %d, antal linuxcopyCommands: %d ", totalCounter, directoryCounter, fileCounter, duplicateCounter, originalCounter));

        Long slutTid = System.currentTimeMillis();

        System.out.println(String.format("Antal total(mapper og filer): %d,  Antal mapper: %d, Antal filer: %d, Antal duplikater: %d, antal originaler: %d ", totalCounter, directoryCounter, fileCounter, duplicateCounter, originalCounter));


    }
}
