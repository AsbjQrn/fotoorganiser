package dk.asbjoern.foto.fotoorganiser.services;


import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class FileMover {

    public void moveFile(String stiFra, String StiTil){

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("mv", stiFra, StiTil );
//        processBuilder.command("mv", "/media/asbjorn/925bd160-9cb1-4f8c-b329-1a01a1555203/Documents/gavl", "/media/asbjorn/925bd160-9cb1-4f8c-b329-1a01a1555203/" );

        try {


            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                System.out.println(output);
                System.exit(0);
            } else {
                System.out.println("flyt af " + stiFra + " lykkedes ikke");
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }




}
