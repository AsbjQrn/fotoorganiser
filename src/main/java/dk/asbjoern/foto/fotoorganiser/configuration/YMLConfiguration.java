package dk.asbjoern.foto.fotoorganiser.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class YMLConfiguration {


    private List<Path> sourcePaths = new ArrayList<>();
    private Path destinationsPath;
    private Path noExifPath;


    @Autowired
    public YMLConfiguration(@Value("${sourceLibs}") final String[] sourceLibs, @Value("${destinationLib}") final String destinationLib
            , @Value("${noExifFolderName}") final String noExifFolderName) {
        for (String sourceBib : sourceLibs) {
            this.sourcePaths.add(Paths.get(sourceBib));
        }
        this.destinationsPath = Paths.get(destinationLib);
        this.noExifPath = destinationsPath.resolve(Paths.get(noExifFolderName));
    }

    public List<Path> getSourcePaths() {
        return sourcePaths;
    }

    public Path getDestinationsPath() {
        return destinationsPath;
    }

    public Path getNoExifPath() {
        return noExifPath;
    }
}
