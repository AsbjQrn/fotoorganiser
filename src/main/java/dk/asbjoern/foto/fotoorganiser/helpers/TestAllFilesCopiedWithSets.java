package dk.asbjoern.foto.fotoorganiser.helpers;

import dk.asbjoern.foto.fotoorganiser.services.LinuxCommandExecuter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TestAllFilesCopiedWithSets implements AllFilesCopiedTester {

    LinuxCommandExecuter linuxCommandExecuter;

    public TestAllFilesCopiedWithSets(LinuxCommandExecuter linuxCommandExecuter) {

        this.linuxCommandExecuter = linuxCommandExecuter;
    }

    @Override
    public Set<String> test(String[] billedbiblioteker, String tilBibliotek) {
        System.out.println("Laver testoptælling");

        Set<String> testSetSource = new HashSet();

        for (String sourceBibliotek : billedbiblioteker) {

            try {


                Files.walk(Paths.get(sourceBibliotek)).forEach(path -> {

                    File fil = path.toFile();

                    if (fil.isFile()) {
                        testSetSource.add(linuxCommandExecuter.executeCommand(Arrays.asList("md5sum", fil.getAbsolutePath())));
                    }
                });


            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        Set<String> testSetDestination = new HashSet();

        try {


            Files.walk(Paths.get(tilBibliotek)).forEach(path -> {

                File fil = path.toFile();

                if (fil.isFile()) {
                    testSetDestination.add(linuxCommandExecuter.executeCommand(Arrays.asList("md5sum", fil.getAbsolutePath())));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }


        testSetSource.removeAll(testSetDestination);

        return testSetSource;

    }

}
