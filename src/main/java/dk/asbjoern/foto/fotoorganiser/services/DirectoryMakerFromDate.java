package dk.asbjoern.foto.fotoorganiser.services;


import dk.asbjoern.foto.fotoorganiser.services.interfaces.Directorymaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;


@Service
public class DirectoryMakerFromDate implements Directorymaker {


    @Value("${tilBibliotek}")
    String tilBibliotek;


    public void DirectoryMakerFromDate() throws IOException {

        Path path = Paths.get(tilBibliotek);


        if (!Files.exists(path)) {

            Files.createDirectories(path);
            System.out.println("Directory mediefiler created");
        } else {

            System.out.println("Directory mediefiler already exists");
        }
    }

    private void makeIfNotExists(String directory) throws IOException {

        Path path = Paths.get(directory);

        if (!Files.exists(path)) {
            Files.createDirectories(path);
            System.out.println("Directory created");
        } else {
            System.out.println("Directory already exists");
        }
    }

    @Override
    public String makeDirectoryPath(LocalDate localDate) throws IOException {


        StringBuilder stringBuilder = new StringBuilder(tilBibliotek);

        if (localDate == null) {


            stringBuilder.append("/");
            stringBuilder.append("noExif");


        } else {

            stringBuilder.append("/");
            stringBuilder.append(localDate.getYear());
            stringBuilder.append("/");
            stringBuilder.append(localDate.getMonth());

        }

        System.out.println("bibliotek dannes: " + stringBuilder.toString());


        String newLocation = stringBuilder.toString();

        makeIfNotExists(newLocation);

        return newLocation;


    }


}
