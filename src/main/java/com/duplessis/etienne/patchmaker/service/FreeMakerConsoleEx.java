package com.duplessis.etienne.patchmaker.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.duplessis.etienne.patchmaker.model.Patch;
import com.duplessis.etienne.patchmaker.utils.PropertyManagerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;


public class FreeMakerConsoleEx {


    private static final Logger LOGGER = LogManager.getLogger(FreeMakerConsoleEx.class);
    private String patchNumber;
    private String patchName;
    private String patchDescription;
    private String patchVersion;
    private List<String> procedure;
    private List<String> function;
    private List<String> type;
    private List<String> apex;
    private List<String> data;
    private List<String> view;
    private List<String> trigger;
    private List<String> table;
    private String filePath = "";

    private List<String> cleanList(List<String> inputList) {
        if (inputList == null) return new ArrayList<>();

        // Remove trailing null or empty strings
        while (!inputList.isEmpty() &&
               (inputList.get(inputList.size() - 1) == null ||
                inputList.get(inputList.size() - 1).trim().isEmpty())) {
            inputList.remove(inputList.size() - 1);
        }

        return inputList;
    }

    public void writeToFile(Patch patch) throws IOException, TemplateException, ConfigurationException {

        LOGGER.info("Starting FreeMarker Write File");

        try {
            this.filePath = PropertyManagerFactory.getInstance().getString("filepath");

            // Ensure the directory exists
            Path directory = Paths.get(filePath);
            if (!Files.exists(directory)) {
                try {
                    Files.createDirectories(directory);
                    LOGGER.info("Created directory: {}", directory);
                } catch (IOException e) {
                    LOGGER.error("Failed to create directory: {}", directory, e);
                    throw new IOException("Cannot create output directory", e);
                }
            }

            // Validate file path
            if (!Files.isWritable(directory)) {
                LOGGER.error("Directory is not writable: {}", directory);
                throw new IOException("Cannot write to specified directory");
            }

            patchNumber = patch.getPatchNumber();
            patchName = patch.getPatchName();
            patchDescription = patch.getPatchDescription();
            patchVersion = patch.getPatchVersion();
            procedure = patch.getProcedure();
            function = patch.getFunction();
            type = patch.getType();
            apex = patch.getApex();
            table = patch.getTable();
            trigger = patch.getTrigger();
            data = patch.getSql();
            view = patch.getView();

            Configuration cfg = new Configuration(new Version("2.3.23"));

            cfg.setClassForTemplateLoading(FreeMakerConsoleEx.class, "/");

            Template template = cfg.getTemplate("test.ftl");

            Map<String, Object> templateData = new HashMap<>();
            LOGGER.info("Setting fields");
            if (patchName != null || patchVersion != null || patchNumber != null || patchDescription != null) {
                templateData.put("patchname", patchName);
                templateData.put("versioncontrol", patchVersion);
                templateData.put("patchnumber", patchNumber);
                templateData.put("pacthdescription", patchDescription);
            } else {
                LOGGER.info("Your patch requires a Name,Version, Number and Description");
            }

            // Clean up procedures list
            List<String> cleanProcedures = cleanList(procedure);
            templateData.put("procedures", cleanProcedures);

            if (function != null) {
                templateData.put("functions", function);
            }

            if (type != null) {
                templateData.put("types", type);
            }
            if (apex != null) {
                templateData.put("apexpages", apex);
            }
            if (table != null) {
                templateData.put("tables", table);
            }
            if (trigger != null) {
                templateData.put("triggers", trigger);
            }
            if (view != null) {
                templateData.put("views", view);
            }
            if (data != null) {
                templateData.put("datas", data);
            }
            LOGGER.info("All fields set");
            LOGGER.info("Starting to Create the file");
            LOGGER.info(filePath);
            String fileName = Paths.get(filePath, "InstallScript_" + patch.getPatchNumber() + ".sql").toString();
            LOGGER.info("Attempting to write file: {}", fileName);

            // Use try-with-resources to ensure file is properly closed
            try (FileWriter fileWriter = new FileWriter(fileName)) {
                StringWriter out = new StringWriter();
                template.process(templateData, out);
                fileWriter.write(out.getBuffer().toString());
                LOGGER.info("InstallScript Created successfully: {}", fileName);
            }
        } catch (Exception e) {
            LOGGER.error("Error writing install script", e);
            throw e;
        }
    }

}
