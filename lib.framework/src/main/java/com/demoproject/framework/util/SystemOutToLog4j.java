package com.demoproject.framework.util;

import java.io.PrintStream;

public class SystemOutToLog4j extends PrintStream {

    private static final PrintStream originalSystemOut = System.out;
    private static SystemOutToLog4j systemOutToLogger;

    @SuppressWarnings("rawtypes")
    public static void enableForClass(Class className) {
        systemOutToLogger = new SystemOutToLog4j(originalSystemOut, className.getName());
        System.setOut(systemOutToLogger);
    }

    public static void enableForPackage(String packageToLog) {
        systemOutToLogger = new SystemOutToLog4j(originalSystemOut, packageToLog);
        System.setOut(systemOutToLogger);
    }

    public static void disable() {
        System.setOut(originalSystemOut);
        systemOutToLogger = null;
    }

    private String packageOrClassToLog;

    private SystemOutToLog4j(PrintStream original, String packageOrClassToLog) {
        super(original);
        this.packageOrClassToLog = packageOrClassToLog;
    }

    @Override
    public void println(final String line) {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        StackTraceElement caller = stack[2];
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(caller.getClassName());
        logger.info(String.format("%s:%d - %s",
                caller.getClassName().substring(caller.getClassName().lastIndexOf(".") + 1),
                caller.getLineNumber(), line));
    }

    @Override
    public void print(final String line) {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        StackTraceElement caller = stack[2];
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(caller.getClassName());
        logger.info(String.format("%s:%d - %s",
                caller.getClassName().substring(caller.getClassName().lastIndexOf(".") + 1),
                caller.getLineNumber(), line));
    }

    public StackTraceElement findCallerToLog(StackTraceElement[] stack) {
        for (StackTraceElement element : stack) {
            if (element.getClassName().startsWith(packageOrClassToLog)) {
                return element;
            }
        }
        return null;
    }
}
