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
    private int linuxCopyCounter;
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

        Long startTid = System.currentTimeMillis();

        Map<String, List<String>> moveCommands = new HashMap<>();


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

                        if (commandBuilder.addToCommandMap(image.getMd5sum(), image.getOriginalLocation(), image.createAndGetNewLocation(), moveCommands) != null) {
                            logger().info("DUPLICATE: {}", image.toString());
                            duplicateCounter++;
                        } else {
                            logger().info("ORIGINAL: {}", image.toString());
                            originalCounter++;
                        }

                    } else {

                        directoryCounter++;
                    }

                });
            }

            System.out.println("Starter billedflytning - størrelsen på map med copycommands er: " + moveCommands.size());
            moveCommands.entrySet().stream().forEach(e -> {

                        logger().info("STARTKOPI: {}", e.getValue().toString());
                        linuxCommandExecuter.executeCommand(e.getValue());
                        linuxCopyCounter++;


                    }

            );
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

//        System.out.println("Laver testoptælling");
//
//        Set<String> testSetSource = new HashSet();
//
//        for (String sourceBibliotek : billedbiblioteker) {
//
//            try {
//
//
//                Files.walk(Paths.get(sourceBibliotek)).forEach(path -> {
//
//                    File fil = path.toFile();
//
//                    if (fil.isFile()) {
//                        testSetSource.add(linuxCommandExecuter.executeCommand(Arrays.asList("md5sum", fil.getAbsolutePath())));
//                    }
//                });
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        Set<String> testSetDestination = new HashSet();
//
//        try {
//
//
//            Files.walk(Paths.get(tilBibliotek)).forEach(path -> {
//
//                File fil = path.toFile();
//
//                if (fil.isFile()) {
//                    testSetDestination.add(linuxCommandExecuter.executeCommand(Arrays.asList("md5sum", fil.getAbsolutePath())));
//                }
//            });
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        System.out.println(String.format("Antal total(mapper og filer): %d,  Antal mapper: %d, Antal filer: %d, Antal duplikater: %d, antal originaler: %d, antal linuxcopyCommands: %d ", totalCounter, directoryCounter, fileCounter, duplicateCounter, originalCounter, linuxCopyCounter));
//        System.out.println("Kildebibliotekerne indeholder " + testSetSource.size() + " antal unikke filer og destinationsmappen indeholder " + testSetDestination.size() + " antal unikke filer.");

//        testSetSource.removeAll(testSetDestination);

//        System.out.println("Flg filer mangler i destinationsmappe");
//        testSetSource.stream().forEach(s ->
//                {
//                    System.out.println(moveCommands.get(s).get(1));
//                }
//        );

        Long slutTid = System.currentTimeMillis();

        System.out.println("Køretid sekunder: " + ((slutTid - startTid) / 1000));


    }
}
