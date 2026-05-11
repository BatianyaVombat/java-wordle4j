package ru.yandex.practicum;

import java.io.IOException;
import java.util.logging.*;

public class WordleLogger {
    private static Logger logger;
    private static FileHandler fileHandler;

    public void initFileLogging(String fileName) {
        logger = Logger.getLogger(fileName);
        try {
            fileHandler = new FileHandler(fileName, false); //обработчик сообщений, false = файл перезаписывается
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SimpleFormatter formatter = new SimpleFormatter(); //создаём обработчик формата, который делает сообщения читаемыми
        fileHandler.setFormatter(formatter); //задаём обработчику этот формат форматирования
        logger.addHandler(fileHandler); //подключаем обработчик к логгеру
        logger.setUseParentHandlers(false); //запрещаем передачу в консоль
    }

    //для тестов
    public void initConsoleLogging() {
        logger = Logger.getLogger("Wordle");
        logger.setUseParentHandlers(false);

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);

    }

    public static void infoLog(String str) {
        logger.info(str);
    }

    public static void crushLog(String str) {
        logger.warning(str);
    }

    public static void wordleLoggerClose() {
        infoLog("Логгер закрыт.");
        fileHandler.close();
    }
}
