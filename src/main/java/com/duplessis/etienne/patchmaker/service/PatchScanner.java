/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duplessis.etienne.patchmaker.service;

import com.duplessis.etienne.patchmaker.utils.PropertyManagerFactory;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author etien
 */
public class PatchScanner {
     
    private String patchDir;
    private String patchName;

    public PatchScanner() {
    }
    

    public PatchScanner(String patchDir, String patchName) {
        this.patchDir = patchDir;
        this.patchName = patchName;
    }

    public String getPatchDir() {
        return patchDir;
    }

    public void setPatchDir(String patchDir) {
        this.patchDir = patchDir;
    }

    public String getPatchName() {
        return patchName;
    }

    public void setPatchName(String patchName) {
        this.patchName = patchName;
    }
    

    

    private final Logger logger = LoggerFactory.getLogger(PatchScanner.class);

    public void listAllFiles(String path) {

        File rootDir;
        File[] fileList;
        String rootPath ="";
  

        rootDir = new File(path);
        fileList = rootDir.listFiles();

        if (fileList != null) {
            for (File f : fileList) {
                if (f.isDirectory()) {
                    logger.info(f.getName()); 
                    listAllFiles(f.getAbsolutePath());  
                } else { 
                   if(f.isFile()){
                    logger.info("parent: " + f.getParent());
                    logger.info("filename: "+ f.getName());
                    }
                }

            }
        }

    }

}
