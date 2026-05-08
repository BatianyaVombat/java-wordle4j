package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */

public class WordleDictionaryLoader {
    private final List<String> unsortedWords;
    private static DictionaryLogger wdlLogger;
    protected String line;

    public WordleDictionaryLoader() {
        unsortedWords = new ArrayList<>();
        wdlLogger = new DictionaryLogger(WordleDictionaryLoader.class);
    }

    public List<String> loadFromFile(String path) throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8))) {
            wdlLogger.infoLog("Начата загрузка словаря из файла: " + path);

            while (fileReader.ready()) {
                line = fileReader.readLine();

                if (!line.trim().isEmpty()) {
                    unsortedWords.add(line);
                }
            }
            wdlLogger.infoLog("Загружено: " + unsortedWords.size() + " слов.");
        } catch (IOException e) {
            wdlLogger.crushLog("Ошибка доступа к файлу: " + path);
        }

        if (unsortedWords.isEmpty()){
            wdlLogger.crushLog("Словарь не загружен: файл пуст!");
            throw new IOException();
        }

        wdlLogger.dictionaryLoggerClose();
        return unsortedWords;
    }
}
