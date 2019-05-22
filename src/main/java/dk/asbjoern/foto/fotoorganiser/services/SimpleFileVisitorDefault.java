package dk.asbjoern.foto.fotoorganiser.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;


@Service
public class SimpleFileVisitorDefault extends SimpleFileVisitor {

    @Override
    public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {


        Objects.requireNonNull(file);
        Objects.requireNonNull(attrs);


        Path sti = (Path) file;





        return FileVisitResult.CONTINUE;
    }


    @Override
    public FileVisitResult visitFileFailed(Object file, IOException exc)
            throws IOException {
        return FileVisitResult.CONTINUE;
    }

}
