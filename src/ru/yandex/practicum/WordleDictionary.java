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
    protected static DictionaryLogger wdLogger;
    final List<String> normalizedDictionary;

    public WordleDictionary(String path) throws IOException, IllegalAccessException {
        //логирование WordleDictionary
        wdLogger = new DictionaryLogger(WordleDictionary.class);

        //получение "сырого" словаря
        WordleDictionaryLoader wdl = new WordleDictionaryLoader();
        List<String> rawDictionary = wdl.loadFromFile(path);

        //процесс нормализации
        this.normalizedDictionary = filteredWords(rawDictionary);
    }

    private List<String> filteredWords(List<String> rawDictionary) throws IllegalAccessException {
        List<String> tempDict = new ArrayList<>();
        wdLogger.infoLog("Начата нормализация словаря");
        for (String word : rawDictionary) {
            if (word.length() == 5) {
                if(isRussianWord(word.toLowerCase())){
                    tempDict.add(word.toLowerCase().replace("ё", "е"));
                }
            }
        }

        if (tempDict.isEmpty()) {
            wdLogger.crushLog("Вероятно список пуст");
            throw new IllegalAccessException();
        }

        return tempDict;
    }

    private boolean isRussianWord(String word) {
        /* Коммент для себя в будущем
         * Проверка того, что слово состоит ТОЛЬКО из русских букв (а мало ли?)
         *
         * 1. Превращаем строку в IntStream кодов символов (chars())
         * 2. Для каждого символа определяем его Unicode-блок
         * 3. Убеждаемся, что ВСЕ символы принадлежат блоку CYRILLIC
         *
         * allMatch чтобы не пропускать слова из смешанных символов rus/eng
         */

        return word.chars()
                .mapToObj(Character.UnicodeBlock::of)
                .allMatch(b -> b.equals(Character.UnicodeBlock.CYRILLIC));
    }

    public List<String> getNormalizedDictionary() {
        return normalizedDictionary;
    }
}
