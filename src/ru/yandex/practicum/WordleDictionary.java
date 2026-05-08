package ru.yandex.practicum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {
    private List<String> normalizedDictionary;
    private WordleDictionaryLoader wdl;
    private static DictionaryLogger wdLogger;


    public WordleDictionary(String path) {
        wdl = new WordleDictionaryLoader();
        normalizedDictionary = new ArrayList<>();
        wdLogger = new DictionaryLogger(WordleDictionary.class);
    }

    public List<String> getCleanDictionary(List<String> dictionary) throws IOException {
        wdLogger.infoLog("Начата нормализация словаря");
        try {
            for (String word : dictionary) {
                if (word.length() == 5 && isRussianWord(word.toLowerCase().replace("ё", "е"))) {
                    normalizedDictionary.add(word);
                }
            }
            wdLogger.infoLog("Нормализация выполнена успешно");
            return normalizedDictionary;
        } catch (IOException e) {
            wdLogger.crushLog();
        }
    }

    private boolean isRussianWord(String word) {
        for (char c : word.toCharArray()) {
            if (c < 'а' || c > 'я') {
                return false;
            }
        }
        return true;
    }
}
