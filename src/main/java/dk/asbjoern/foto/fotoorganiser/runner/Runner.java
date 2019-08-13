package dk.asbjoern.foto.fotoorganiser.runner;

import dk.asbjoern.foto.fotoorganiser.beans.Image;
import dk.asbjoern.foto.fotoorganiser.configuration.YMLConfiguration;
import dk.asbjoern.foto.fotoorganiser.helpers.Loggable;
import dk.asbjoern.foto.fotoorganiser.imagefactory.ImageFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

@Component
public class Runner implements Loggable {

    private ImageFactory imageFactory;
    private int fileCounter;
    private int duplicateCounter;
    private int originalCounter;
    private int directoryCounter;
    private int totalCounter;
    private YMLConfiguration config;

    public Runner(ImageFactory imageFactory, YMLConfiguration config) {
        this.imageFactory = imageFactory;
        this.config = config;
    }

    public void run() throws IOException {

        Set<Image> images = new HashSet<>();

        try {


            for (Path sourcePath : config.getSourcePaths()) {

                Files.walk(sourcePath).forEach(path -> {

                    totalCounter++;


                    if (Files.isRegularFile(path)) {
                        fileCounter++;


                        Image image = null;
                        try {
                            image = imageFactory.createImage(path, sourcePath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (images.add(image)) {
                            originalCounter++;
                        } else {
                            duplicateCounter++;
                        }

                    } else {

                        directoryCounter++;
                    }

                });


                System.out.println(String.format("Antal total(mapper og filer): %d,  Antal mapper: %d, Antal filer: %d, Antal duplikater: %d, antal originaler: %d ", totalCounter, directoryCounter, fileCounter, duplicateCounter, originalCounter));

                Long slutTid = System.currentTimeMillis();



            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        System.out.println("uniquefiles found (ImagesSet): " + images.size());

        for(Image image : images){
            Files.copy(image.getPathOriginalLocationAndFilename(), image.getPathToNewLocationAndFilename());
        }


    }
}
