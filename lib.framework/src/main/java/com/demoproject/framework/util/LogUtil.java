package com.demoproject.framework.util;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import java.io.File;

public class LogUtil { 

    public static boolean initialized;

    public static void init() {
        init("");
    }

    public static void init(final String prefix) {
        if (LogUtil.initialized) {
            return;
        }

        String APP_PATH = System.getProperty("apppath");
        String APP_ENV = System.getProperty("appenv");

        if (APP_ENV == null) {
            APP_ENV = "";
        }
        if (!"".equals(APP_ENV)) {
            APP_ENV += ".";
        }
        String file = APP_PATH + File.separator + prefix + "conf" + File.separator + APP_ENV + "log4j.ini";
        if (APP_PATH == null || APP_PATH.isEmpty()) {
            file = "conf/service.log4j.ini";
        }

        if (new File(file).exists()) {
            PropertyConfigurator.configure(file);
            LogUtil.initialized = true;
        }
    }

    public static void dumpLog(final String content) {
        Logger.getLogger((Class) LogUtil.class).info((Object) content);
    }

    public static Logger getLogger(final Class cls) {
        return Logger.getLogger(cls.getCanonicalName());
    }

    public static String stackTrace(final Throwable ex) {
        final StringWriter sw = new StringWriter();
        try (final PrintWriter pw = new PrintWriter(sw)) {
            ex.printStackTrace(pw);
            final String out = sw.toString();
            pw.flush();
            return out;
        }
    }

    private static String time() {
        final Date date = new Date();
        final DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:s");
        return df.format(date);
    }

    public static void setLogLevel(final String logger, String level) {
        final Logger loggerObj = LogManager.getLogger(logger);
        if (null == loggerObj) {
            return;
        }
        final String upperCase;
        level = (upperCase = level.toUpperCase());
        switch (upperCase) {
            case "DEBUG": {
                loggerObj.setLevel(Level.DEBUG);
                break;
            }
            case "ERROR": {
                loggerObj.setLevel(Level.ERROR);
                break;
            }
            case "FATAL": {
                loggerObj.setLevel(Level.FATAL);
                break;
            }
            case "INFO": {
                loggerObj.setLevel(Level.INFO);
                break;
            }
            case "OFF": {
                loggerObj.setLevel(Level.OFF);
                break;
            }
            case "WARN": {
                loggerObj.setLevel(Level.WARN);
                break;
            }
            default: {
                loggerObj.setLevel(Level.ALL);
                break;
            }
        }
    }

    public static String throwableToString(final Throwable ex) {
        final StringBuilder sbuf = new StringBuilder("");
        final String trace = stackTrace(ex);
        sbuf.append("exception was generated at : ").append(time()).append(" on thread ").append(Thread.currentThread().getName());
        sbuf.append(System.getProperty("line.separator"));
        final String message = ex.getMessage();
        if (message != null) {
            sbuf.append(message);
        }
        sbuf.append(System.getProperty("line.separator")).append(trace);
        return sbuf.toString();
    }

    public static String getLogMessage(final String message) {
        final StringBuilder sbuf = new StringBuilder("log started at : " + time());
        sbuf.append(File.separator).append(message);
        return sbuf.toString();
    }

    static {
        LogUtil.initialized = false;
        init();
    }
}
