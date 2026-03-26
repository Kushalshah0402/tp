package seedu.finbro.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Formatter;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.finbro.Finbro;
import seedu.finbro.exception.FinbroException;

public class LogFormatter extends Formatter {
    private static final String LOGS_DIR = "logs";
    private static final String LOG_CONFIG = "/logging.properties";

    private static final DateTimeFormatter dtf =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * @author natmloclam
     */
    @Override
    public String format(LogRecord record) {
        String time = LocalDateTime.now().format(dtf);
        String level = record.getLevel().getName();
        String loggerName = record.getLoggerName();
        String message = formatMessage(record);

        return String.format("%s [%s] %s - %s%n",
                time, level, loggerName, message);
    }
    /**
     * @author natmloclam
     */
    public static void initLogger() throws FinbroException {
        createFile();
        try {
            LogManager.getLogManager().readConfiguration(Finbro.class.getResourceAsStream(LOG_CONFIG));
        } catch (IOException e) {
            throw new FinbroException("Unable to read configuration file: " + LOG_CONFIG);
        }
    }
    /**
     * @author natmloclam
     */
    private static void createFile() throws FinbroException {
        // create logs folder if it doesn't exist
        File logsFolder = new File(LOGS_DIR);
        if (!logsFolder.exists()) {
            boolean dirCreated = logsFolder.mkdir();
            if (!dirCreated) {
                throw new FinbroException("Unable to create directory" + logsFolder.getAbsolutePath());
            }
        }
    }
}
