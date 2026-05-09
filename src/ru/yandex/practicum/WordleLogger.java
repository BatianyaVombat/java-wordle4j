package ru.yandex.practicum;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class WordleLogger {
    private static Logger logger;
    private static FileHandler fileHandler;

    public void init() {
        logger = Logger.getLogger("Wordle");
        try {
            fileHandler = new FileHandler("wordle.log", false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
        logger.addHandler(fileHandler);
        logger.setUseParentHandlers(false);
    }

    public static void infoLog(String str) {
        logger.info(str);
    }

    public static void crushLog(String str) {
        logger.warning(str);
    }

    public static void dictionaryLoggerClose() {
        logger.info("fileHandler закрыт.");
        fileHandler.close();
    }
}
