package com.duplessis.etienne.patchmaker.service;

import com.duplessis.etienne.patchmaker.controler.PatchController;
import com.duplessis.etienne.patchmaker.model.Patch;
import com.duplessis.etienne.patchmaker.utils.PropertyManagerFactory;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FreeMakerConsoleEx {

    private final Logger logger = LoggerFactory.getLogger(FreeMakerConsoleEx.class);
    private String patchNumber;
    private String patchName;
    private String patchDescription;
    private String patchVersion;
    private ArrayList<String> procedure;
    private ArrayList<String> function;
    private ArrayList<String> type;
    private ArrayList<String> apex;
    private ArrayList<String> data;
    private ArrayList<String> view;
    private ArrayList<String> trigger;
    private ArrayList<String> table;
    private String filePath = "";

    public void writeToFile(Patch patch) throws IOException, TemplateException {

        logger.info("Starting FreeMarker Write File");

        // Properties prop = new Properties();
        // ClassLoader loader = Thread.currentThread().getContextClassLoader();
        // InputStream stream = loader.getResourceAsStream("/application.properties");
        // prop.load(stream);
        // filePath = prop.getProperty("filepath");
        try {
            this.filePath = PropertyManagerFactory.getInstance().getString("filepath");
        } catch (Exception e) {
            logger.error("Couldn't load config");
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
        logger.info("Setting fields");
        if (patchName != null || patchVersion != null || patchNumber != null || patchDescription != null) {
            templateData.put("patchname", patchName);
            templateData.put("versioncontrol", patchVersion);
            templateData.put("patchnumber", patchNumber);
            templateData.put("pacthdescription", patchDescription);
        } else {
            logger.info("Your patch requires a Name,Version, Number and Description");
        }

        if (procedure != null) {
            templateData.put("procedures", procedure);
        }
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
        logger.info("All fields set");
        logger.info("Starting to Create the file"); 
        logger.info(filePath);
        String fileName = filePath + "InstallScript_" + patch.getPatchNumber() + ".sql";
        logger.info(fileName);
        
        FileWriter fileWriter = new FileWriter(fileName);
        try ( StringWriter out = new StringWriter()) {
            template.process(templateData, out);
            fileWriter.write(out.getBuffer().toString());
            fileWriter.close();
            out.flush();
            logger.info("InstallScript Created");
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            fileWriter.close();
        }
    }

}
