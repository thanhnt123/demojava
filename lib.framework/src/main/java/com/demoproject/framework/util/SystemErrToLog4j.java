package com.demoproject.framework.util;

import java.io.PrintStream;

public class SystemErrToLog4j extends PrintStream {

    private static final PrintStream originalSystemErr = System.err;
    private static SystemErrToLog4j systemErrToLogger;

    @SuppressWarnings("rawtypes")
    public static void enableForClass(Class className) {
        systemErrToLogger = new SystemErrToLog4j(originalSystemErr, className.getName());
        System.setErr(systemErrToLogger);
    }

    public static void enableForPackage(String packageToLog) {
        systemErrToLogger = new SystemErrToLog4j(originalSystemErr, packageToLog);
        System.setErr(systemErrToLogger);
    }

    public static void disable() {
        System.setErr(originalSystemErr);
        systemErrToLogger = null;
    }

    private final String packageOrClassToLog;

    private SystemErrToLog4j(PrintStream original, String packageOrClassToLog) {
        super(original);
        this.packageOrClassToLog = packageOrClassToLog;
    }

    public void printlnTextOnly(final String line) {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(stack[stack.length - 1].getClassName());
        logger.error(line);
    }

    @Override
    public void println(final String line) {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(stack[stack.length - 1].getClassName());
        printByLog4j(line, stack, logger);
    }

    @Override
    public void print(final String line) {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(stack[stack.length - 1].getClassName());
        printByLog4j(line, stack, logger);
    }

    private void printByLog4j(String line, StackTraceElement[] stack, org.apache.log4j.Logger logger) {
        StringBuilder reportString = new StringBuilder();
        StringBuilder stackString = new StringBuilder();
        reportString.append(line).append("\n");
        for (int i = stack.length - 1; i >= 0; i--) {
            StackTraceElement element = stack[i];
            if (element.getMethodName().equals("printStackTrace")) {
                break;
            }
            if (i == stack.length - 1) {
                stackString.insert(0, "\t" + element.toString());
            } else {
                stackString.insert(0, "\t" + element.toString() + "\n");
            }
        }
        reportString.append(stackString);
        logger.error(reportString);
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
