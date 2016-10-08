package de.wintercloud;

import com.hubspot.jinjava.Jinjava;
import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by nathan on 10/8/16.
 */
public class JinjaJavaCli {
    /**
     * Compiles the template from the file in args[0] using the variables from the files in args[1] and saves the
     * result to the file in args[2]
     * @param args 3 Strings: The template file name, the variables file name and the output file Name
     */
    static public void main(String [] args) {
        if (args.length != 3) {
            // Wrong number of arguments
            System.out.println("Usage: <template-file> <variables-yaml-file> <output-file>");
            System.exit(-1);
        }
        // Store the arguments in better named variables
        String templateFile = args[0];
        String varFile = args[1];
        String outFile = args[2];

        try {
            // Load the parameters
            Yaml yaml = new Yaml();
            Map<String, Object> context = (Map<String, Object>) yaml.load(FileUtils.readFileToString(new File(varFile)));

            // Load template
            Jinjava jinjava = new Jinjava();
            String template = FileUtils.readFileToString(new File(templateFile));

            // Render and save
            String rendered = jinjava.render(template, context);
            FileUtils.writeStringToFile(new File(outFile), rendered);
        } catch(IOException e) {
            // Print error and exit with -1
            System.out.println(e.getLocalizedMessage());
            System.exit(-1);
        }
    }
}
