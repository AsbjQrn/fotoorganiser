package dk.asbjoern.foto.fotoorganiser.services;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDescriptor;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import dk.asbjoern.foto.fotoorganiser.beans.Image;
import dk.asbjoern.foto.fotoorganiser.services.interfaces.ExifDateService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


@Service
public class ExifServiceDateExtract implements ExifDateService {


    public LocalDate readExif(File file) {


        System.out.println("Extracter Exif fra: " + file.toString());

        Date date = null;
        LocalDate localDate = null;

        try {

            Metadata metadata = ImageMetadataReader.readMetadata(file);
            Collection<ExifIFD0Directory> list1 = Objects.requireNonNull(metadata.getDirectoriesOfType(ExifIFD0Directory.class));
            Collection<ExifSubIFDDirectory> list2 = Objects.requireNonNull(metadata.getDirectoriesOfType(ExifSubIFDDirectory.class));
            if (list1.size() > 1)
                throw new Exception("\"Der er mere en eet ExifIFD0Directory i filen \" +  file");


            for (ExifIFD0Directory exifIFD0Directory : list1) {
                date = exifIFD0Directory.getDate(306);
            }

            if(date==null){
                for (ExifSubIFDDirectory exifSubIFDDirectory : list2) {
                    date = exifSubIFDDirectory.getDate(306);
                    if(date== null){
                        date = exifSubIFDDirectory.getDateDigitized();
                    }
                    if(date== null){
                        date = exifSubIFDDirectory.getDateOriginal();
                    }
                }
            }

            localDate = convertToLocalDateViaInstant(date);

        } catch (NullPointerException n) {
            System.out.println("ExifIFD0Directory findes ikke i filen " + file);
        } catch (ImageProcessingException | IOException e) {
            e.printStackTrace();
        } catch (Exception n) {
            n.printStackTrace();
        }


        return localDate;

    }


    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        Objects.requireNonNull(dateToConvert);
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

}
