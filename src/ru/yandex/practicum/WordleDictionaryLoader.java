package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WordleDictionaryLoader {
    private final List<String> unsortedWords;
    protected String line;

    public WordleDictionaryLoader() {
        unsortedWords = new ArrayList<>();
    }

    public List<String> loadFromFile(String path) throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8))) {
            WordleLogger.infoLog(STR."Начата загрузка словаря: \{path}");
            while (fileReader.ready()) {
                line = fileReader.readLine();

                if (!line.isBlank()) {
                    unsortedWords.add(line.trim());
                }
            }

        } catch (IOException e) {
            WordleLogger.crushLog(STR."Ошибка доступа к файлу: \{path}");
        }

        if (unsortedWords.isEmpty()){
            WordleLogger.crushLog("Словарь не загружен: файл пуст!");
            throw new IOException();
        }

        return unsortedWords;
    }
}
