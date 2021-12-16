package org.aleks4ay.developer.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class FileWriter {
    static final Logger log = LoggerFactory.getLogger(FileWriter.class);


    public static boolean writeTimeChange(String role){
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(new File(Constants.FILE_CHANGES), true), StandardCharsets.UTF_8))) {
            String timeString = LocalDateTime.now().format(Constants.dayTimeFormatter);
            bw.write( timeString + " < " + role + " >" + System.lineSeparator());
            bw.flush();
        }
        catch(IOException ex){
            log.warn("Exception during writing time of new records. Changing is from '{}'.", role, ex);
            return false;
        }
        return true;
    }

    public static String readTimeChange() {
        String line;
        String result = "";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File(Constants.FILE_CHANGES)), StandardCharsets.UTF_8))) {

            while ( (line = br.readLine()) != null ) {
                result = line;
            }
            return result;
        }
        catch(IOException ex){
            log.warn("Exception during reading time of last records.", ex);
            return "";
        }
    }
}
