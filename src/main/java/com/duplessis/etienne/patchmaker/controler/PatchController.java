package com.duplessis.etienne.patchmaker.controler;

import com.duplessis.etienne.patchmaker.model.Patch;
import com.duplessis.etienne.patchmaker.service.FreeMakerConsoleEx;
import com.duplessis.etienne.patchmaker.service.PatchScanner;
import com.duplessis.etienne.patchmaker.service.PatchService;
import com.duplessis.etienne.patchmaker.utils.PropertyManagerFactory;
import freemarker.template.TemplateException;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/patch")
public class PatchController {

    @Autowired
    Patch patch;

    PatchService patchService = new PatchService();

    private static final Logger LOGGER = LogManager.getLogger(PatchController.class);

    @GetMapping("/patch/write/{fileName},{filePath}")
    public ResponseEntity<Resource> createPatch(@PathVariable String fileName, @PathVariable String filePath) {

        patchService.writeToFile(fileName, filePath);

        return ResponseEntity.ok().header(fileName + " written to " + filePath).body(null);

    }

//Sample Call
//http://localhost:8080/patch/download?filename=InstallScript_3465.sql
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam(value = "filename") String fileName) throws IOException {
        LOGGER.info("Starting InstallScript Download");

        String filePath = "";

        try {
            filePath = PropertyManagerFactory.getInstance().getString("filepath");
        } catch (Exception e) {
            LOGGER.error("Couldn't load config");
        }
        String fullFile = filePath.concat(fileName);
        LOGGER.info("fullFile: " + fullFile);
        File file = new File(fullFile);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();

        try {

            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("Content-Disposition", "attachment; filename=" + fileName/*fileName*/);

            LOGGER.info("Finished InstallScript Download");

        } catch (Exception e) {
            LOGGER.info("Failed to download");

        }

        return ResponseEntity.ok().headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);

    }

    @PostMapping(value = "/installscript",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Patch> createInstallScript(@RequestBody Patch patch) {
        //patch = new Patch();
        LOGGER.info("Starting InstallScript Create");

        try {
            FreeMakerConsoleEx template = new FreeMakerConsoleEx();
            template.writeToFile(patch);

            return ResponseEntity.ok().header("success").body(null);
        } catch (TemplateException | IOException e) {
            LOGGER.error(e.getMessage());
        } catch (ConfigurationException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().header("success").body(null);
    }

    @GetMapping(value = "/scan",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Resource> scanDirectory(@RequestBody PatchScanner scanner) {
        LOGGER.info("Starting Directory Scan");
        
        String rootPath ="";
        String finalPath;
        String path = "";
        
         try {
            rootPath= PropertyManagerFactory.getInstance().getString("patchPath");
        } catch (Exception e) {
             LOGGER.error("Couldn't load config");
        }
         
         
        path = scanner.getPatchDir() + "/" + scanner.getPatchName();
        
        finalPath = rootPath.concat("/"+path);

        LOGGER.info("finalPath: "+ finalPath);

        LOGGER.info("Starting Directory Scan: " + finalPath);
        try {
            PatchScanner patchScanner = new PatchScanner();
            patchScanner.listAllFiles(finalPath);
        } catch (Exception e) {

            LOGGER.error("Error Scanning Directory :" + e.getMessage());

            return ResponseEntity.badRequest().header("Failed to scan Directory: " + path).body(null);
        }

        return ResponseEntity.ok().header("success").body(null);
    }
}
