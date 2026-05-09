package ru.yandex.practicum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {
    private final Random random = new Random();
    final List<String> normalizedDictionary;

    public WordleDictionary(String path) throws IOException, IllegalAccessException {
        //получение "сырого" словаря
        WordleDictionaryLoader wdl = new WordleDictionaryLoader();
        List<String> rawDictionary = wdl.loadFromFile(path);

        //нормализация
        this.normalizedDictionary = filteredWords(rawDictionary);
    }

    //метод для нормализации словаря
    private List<String> filteredWords(List<String> rawDictionary) throws IllegalAccessException {
        List<String> tempDict = new ArrayList<>();
        WordleLogger.infoLog("Начата нормализация словаря");
        for (String word : rawDictionary) {
            if (word.length() == 5) {
                if (isRussianWord(word)) {
                    tempDict.add(word.toLowerCase().replace("ё", "е"));
                }
            }
        }

        if (tempDict.isEmpty()) {
            WordleLogger.crushLog("Проблема со словарём, возможно он пусто!");
            throw new IllegalAccessException();
        }
        WordleLogger.infoLog("Словарь нормализован!");
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

    //получение случайного слова для цикла игры
    public String getRandomWord() {
        return normalizedDictionary.get(random.nextInt(normalizedDictionary.size()));
    }

    public String compareWords(String target, String userGuess) {
        char[] result = new char[5];
        boolean[] used = new boolean[5];

        //буквы угаданные и на своём месте +
        for (int i = 0; i < target.length(); i++) {
            if (userGuess.charAt(i) == target.charAt(i)) {
                result[i] = '+';
                used[i] = true;
            } else {
                result[i] = '?';
                used[i] = false;
            }
        }

        //буквы угаданные и не на своём месте ^ и неверные -
        for (int i = 0; i < userGuess.length(); i++) {
            if (result[i] != '+') {
                boolean found = false;
                for (int j = 0; j < target.length(); j++) {
                    if (!used[j] && userGuess.charAt(i) == target.charAt(j)) {
                        result[i] = '^';
                        used[j] = true;
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    result[i] = '-';
                }
            }
        }
        return String.valueOf(result);
    }

    public boolean containWord(String word){
        return normalizedDictionary.contains(word);
    }
}
