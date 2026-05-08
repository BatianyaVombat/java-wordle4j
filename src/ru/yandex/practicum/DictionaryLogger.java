package ru.yandex.practicum;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class DictionaryLogger {
    protected Logger logger;
    protected FileHandler fileHandler;

    public <T> DictionaryLogger(Class<T> cls) {
        try {
            logger = Logger.getLogger(cls.getName());
            fileHandler = new FileHandler(cls.getName() + ".log", false);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void infoLog(String str){
        logger.info(str);
    }

    public void crushLog(String str){
        logger.warning(str);
    }

    public void dictionaryLoggerClose(){
        logger.info("fileHandler закрыт.");
        fileHandler.close();
    }
}
