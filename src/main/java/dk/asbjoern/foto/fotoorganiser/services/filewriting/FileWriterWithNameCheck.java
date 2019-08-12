package dk.asbjoern.foto.fotoorganiser.services.filewriting;

import dk.asbjoern.foto.fotoorganiser.helpers.Loggable;

import java.io.File;
import java.io.IOException;

public class FileWriterWithNameCheck implements FileWriter, Loggable {

    private final static String RENAME_NAME = "-renamed";
    private int renameNumber = 0;

    public void writeFile(String absoluteFilePath, String fileName) throws IOException {

        boolean notCreated = true;

        String wholeName = absoluteFilePath +  "/" + fileName;

        while (notCreated) {
            File image = new File(wholeName);

            if (image.exists()) {
                wholeName = absoluteFilePath + renameFile(fileName);
            } else {
                image.createNewFile();
                System.out.println(image.getAbsolutePath());
            }
        }


    }

    private String renameFile(String fileName) {

        renameNumber++;
        return fileName + RENAME_NAME + renameNumber;

    }

}
