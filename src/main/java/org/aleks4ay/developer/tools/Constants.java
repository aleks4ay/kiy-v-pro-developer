package org.aleks4ay.developer.tools;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public final class Constants {

    public static final String DBF_PATH;
    public static final String FILE_CHANGES;
    public static final DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter dayTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    static {
        final ResourceBundle config = ResourceBundle
                .getBundle("persistence", Locale.ENGLISH);
        DBF_PATH = config.getString("dbf.serverPath");
        FILE_CHANGES = config.getString("fileName");
    }
}
