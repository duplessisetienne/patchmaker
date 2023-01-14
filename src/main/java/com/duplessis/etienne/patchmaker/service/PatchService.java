package com.duplessis.etienne.patchmaker.service;

import org.springframework.core.io.InputStreamResource;

import java.io.*;

public class PatchService {

    private static final String EXTENSION = ".sql";

    public  void writeToFile(String fileName, String filePath){

        try {

            fileName = fileName + EXTENSION;

            PrintWriter outputStream = new PrintWriter(fileName);
            outputStream.println("Hi There file!");
            outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public InputStreamResource downloadFile(String fileName) throws IOException {
        fileName = fileName + EXTENSION;

        File file = new File(fileName);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return resource;
    }


}
