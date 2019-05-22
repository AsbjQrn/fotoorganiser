package dk.asbjoern.foto.fotoorganiser.services;

import dk.asbjoern.foto.fotoorganiser.enums.TypeOfCheckSumCalculation;
import dk.asbjoern.foto.fotoorganiser.helpers.Functions;
import dk.asbjoern.foto.fotoorganiser.helpers.Predikater;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dk.asbjoern.foto.fotoorganiser.helpers.Consumers.addToMap;

@Service
public class FileMapper {


    int fileCounter;

    public void mapBasedOnLength(HashMap<Long, List<File>> mediaMap, Path path) throws IOException {
        Files.walk(path)
                .forEach(p -> {
                    if (isFile(p)) {
                            File file = p.toFile();
                            addToMap.accept(file.length(), file, mediaMap);
                        System.out.println(file.toString());

                        fileCounter++;
                    }
                });
    }

    public static boolean isFile(Path path){
        return path.toFile().isFile();
    }

    private HashMap<Long, List<File>> mapDuplicatesBasedOnChecksum(Map<Long, List<File>> duplicatesMap,
                                                                   TypeOfCheckSumCalculation typeOfCheckSumCalculation) {
        HashMap<Long, List<File>> duplicatesMapBasedOnChecksum = new HashMap<>();

        duplicatesMap.entrySet().stream().filter(e -> Predikater.sizeGreaterThanOne.test(e.getValue())).forEach(listEntry ->
                listEntry.getValue().forEach(
                        listItem -> addToMap.accept(
                                Functions.calculateChecksum.apply(listItem, typeOfCheckSumCalculation),
                                listItem, duplicatesMapBasedOnChecksum)));

        return duplicatesMapBasedOnChecksum;
    }






}
