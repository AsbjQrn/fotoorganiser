package dk.asbjoern.foto.fotoorganiser.services;


import dk.asbjoern.foto.fotoorganiser.helpers.Loggable;
import dk.asbjoern.foto.fotoorganiser.services.interfaces.CommandExecuter;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


@Service
public class LinuxCommandExecuter implements CommandExecuter, Loggable {


    @Override
    public String executeCommand(List<String> commandList) {

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(commandList);

        StringBuilder output = new StringBuilder();

        try {


            Process process = processBuilder.start();


            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal != 0) {
                logger().error("Linux process sluttede med fejl {} ", commandList);
                System.exit(1);
            }


        } catch (IOException e) {
            logger().error("Linux process sluttede med fejl {} ", commandList);
            e.printStackTrace();
            System.exit(1);
        } catch (InterruptedException e) {
            logger().error("Linux process sluttede med fejl {} ", commandList);
            e.printStackTrace();
            System.exit(1);
        }


        String result = output.toString();
        return result.substring(0, result.indexOf(" ") + 1);

    }


}
