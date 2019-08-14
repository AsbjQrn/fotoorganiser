package dk.asbjoern.foto.fotoorganiser.services.filewriting;

import dk.asbjoern.foto.fotoorganiser.helpers.Loggable;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class FileWriterWithNameCheck implements FileWriter, Loggable {

    private final static String RENAME_NAME = "-renamed";
    private int renameNumber;

    public void writeFile(Path originalpath, Path newPath, Path fileName) throws Exception {


        renameNumber = 0;

        while (Files.exists(newPath.resolve(fileName))) {
            fileName = makeNewFileName(fileName);

        }


    }

    private Path makeNewFileName(Path fileName) throws Exception {

        String[] fileNameParts = fileName.toString().split(".");

        StringBuilder fileFirstName = new StringBuilder();
        for (int i = 0; i < fileNameParts.length -2 ; i++) {
            fileFirstName.append(fileNameParts[i]);
        }

        String fileType = fileNameParts[fileNameParts.length -1];


        renameNumber++;
        return Paths.get(fileFirstName.toString() + RENAME_NAME + renameNumber + "." + fileType);

    }

}
