package dk.asbjoern.foto.fotoorganiser.services;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;

public class CustomVisitor extends SimpleFileVisitor {

    @Override
    public FileVisitResult visitFileFailed(Object file, IOException exc) throws IOException {
        return super.visitFileFailed(file, exc);
    }
}
