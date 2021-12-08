package org.aleks4ay.developer.tools;

import java.util.Locale;
import java.util.ResourceBundle;

public final class Constants {

    public static final String DBF_PATH;
    public static final String CLIENT_FILE;
    public static final String WORKER_FILE;
    public static final String JOURNAL_FILE;
    public static final String TMC_FILE;
    public static final String EMBODIMENT_FILE;
    public static final String ORDER_FILE;
    public static final String DESCRIPTION_FILE;
    public static final String MANUFACTURE_FILE;
    public static final String INVOICE_FILE;
    public static final String INVOICE_DESCRIPTION_FILE;
    public static final String TMC_BALANCE_FILE;


    public static final String TMC_FILE_TEST;

    static {
        final ResourceBundle config = ResourceBundle
                .getBundle("persistence", Locale.ENGLISH);
        DBF_PATH = config.getString("dbf.serverPath");
        CLIENT_FILE = config.getString("fNameClient");
        WORKER_FILE = config.getString("fNameWorker");
        JOURNAL_FILE = config.getString("fNameJournal");
        TMC_FILE = config.getString("fNameTmc");
        TMC_FILE_TEST = config.getString("fNameTmcTest");
        EMBODIMENT_FILE = config.getString("fNameEmbodiment");
        ORDER_FILE = config.getString("fNameOrder");
        DESCRIPTION_FILE = config.getString("fNameDescr");
        MANUFACTURE_FILE = config.getString("fNameManuf");
        INVOICE_FILE = config.getString("fNameInvoice");
        INVOICE_DESCRIPTION_FILE = config.getString("fNameInvoiceDescr");
        TMC_BALANCE_FILE = config.getString("fNameTmcBalance");
    }
}
