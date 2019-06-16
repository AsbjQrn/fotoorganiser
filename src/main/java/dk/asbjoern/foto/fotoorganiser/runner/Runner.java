package dk.asbjoern.foto.fotoorganiser.runner;

import dk.asbjoern.foto.fotoorganiser.beans.Image;
import dk.asbjoern.foto.fotoorganiser.imagefactory.ImageFactory;
import dk.asbjoern.foto.fotoorganiser.services.LinuxCommandExecuter;
import dk.asbjoern.foto.fotoorganiser.services.interfaces.CommandBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Runner(ImageFactory imageFactory, LinuxCommandExecuter linuxCommandExecuter, CommandBuilder commandBuilder) {
        this.imageFactory = imageFactory;
        this.linuxCommandExecuter = linuxCommandExecuter;
        this.commandBuilder = commandBuilder;
    }

    public void run() {


        try {
            Map<String, List<String>> moveCommands = new HashMap<>();

            Files.walk(Paths.get(billedbiblioteker[0])).forEach(path -> {

                totalCounter++;


                if (path.toFile().isFile()) {
                    fileCounter++;


                    Image image = null;
                    try {
                        image = imageFactory.createImage(path);
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

            System.out.println("Starter billedflytning");
            moveCommands.entrySet().stream().forEach(e ->

                    linuxCommandExecuter.executeCommand(e.getValue())

            );
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println(String.format("Antal total(mapper og filer): %d,  Antal mapper: %d, Antal filer: %d, Antal duplikater: %d, antal originaler: %d ", totalCounter, directoryCounter, fileCounter, duplicateCounter, originalCounter));
            System.exit(0);

        }
    }
}
