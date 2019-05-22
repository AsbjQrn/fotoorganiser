package dk.asbjoern.foto.fotoorganiser.helpers;

import dk.asbjoern.foto.fotoorganiser.enums.TypeOfCheckSumCalculation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.BiFunction;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;

public class Functions {

    public static final BiFunction<File, TypeOfCheckSumCalculation, Long> calculateChecksum = (f, t) -> calculate(f, t);

    private static long calculate(File file, TypeOfCheckSumCalculation typeOfCheckSumCalculation) {
        long checksum = 0;

        try {
            CheckedInputStream cis = null;

            try {
                cis = new CheckedInputStream(new FileInputStream(file), new Adler32());

            } catch (Exception e) { //TODO indsaet logging her
                System.out.println("File Not found ");
                throw e;
            }
            byte[] buffer = new byte[1024];
            //can change the size according to needs
            while (cis.read(buffer) >= 0) {
                checksum = cis.getChecksum().getValue();
                if (typeOfCheckSumCalculation == TypeOfCheckSumCalculation.PART_OF_FILE_CHECKSUM) return checksum;
            }
            checksum = cis.getChecksum().getValue();


        } catch (IOException e) {
            System.out.println("The exception has been thrown:" + e);
            System.exit(1);
        }
        return checksum;

    }
}
