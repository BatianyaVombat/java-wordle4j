package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class WordleTest {
    private static WordleDictionaryLoader wdl;
    private static WordleDictionary wD;
    private static WordleGame game;
    private static Path emptyDictionary;

    @BeforeAll
    public static void beforeAll() throws IOException {
        WordleLogger wordleLogger = new WordleLogger();
        wordleLogger.initConsoleLogging();
        wD = new WordleDictionary("src/words_ru.txt");

        //--------------------------------------------------
        emptyDictionary = Files.createTempFile("empty_dict", "txt");
    }

    @BeforeEach
    public void beforeEach() {
        game = new WordleGame(wD);
    }

    //Проверяем поведение загрузчика, если файл пустой
    @Test
    public void shouldThrowExceptionWhenDictionaryFileIsEmpty(){
        Assertions.assertThrows(IOException.class, () -> {
            new WordleDictionaryLoader().loadFromFile(emptyDictionary.toString());
        });

        try {
            Files.deleteIfExists(emptyDictionary);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Проверка правильности подсказок для пользователя
    @Test
    public void isCompareWordReturnCompleteCoincidence() {
        //полное совпадение
        Assertions.assertEquals("+++++", wD.compareWords("арбуз", "арбуз"));
    }

    @Test
    public void isCompareWordReturnCompleteMismatch() {
        //все буквы разные
        Assertions.assertEquals("-----", wD.compareWords("берег", "масло"));
    }

    @Test
    public void isCompareWordReturnPartialMatch() {
        //частичное совпадение
        Assertions.assertEquals("++---", wD.compareWords("катер", "кабан"));
    }

    @Test
    public void isCompareWordReturnPartialMatchPlus() {
        //частичное совпадение + правильные буквы не на своём месте
        Assertions.assertEquals("+^-^-", wD.compareWords("арбуз", "азарт"));
    }

    //проверка функций валидации
    @Test
    public void shouldThrowExceptionWhenWordTooShort() {
        //длина менее 5 букв
        Assertions.assertThrows(InvalidWordException.class, () -> game.validateWord("душа"));
    }

    @Test
    public void shouldNotThrowExceptionWhenWordCorrect() {
        //длина 5 букв, есть в словаре
        game.validateWord("выдра");
    }

    @Test
    public void shouldThrowExceptionWhenWordTooLong() {
        //длина более 5 букв
        Assertions.assertThrows(InvalidWordException.class, () -> game.validateWord("просека"));
    }

    @Test
    public void shouldThrowExceptionWhenWordNotInDictionaryButCorrect() {
        //длина 5 букв, нет в словаре
        Assertions.assertThrows(InvalidWordException.class, () -> game.validateWord("азарт"));
    }

    //тестирование функционала игы с ПК при разной наполненности истории
    @Test
    public void shouldReturnRandomWordIfHistoryIsEmpty() {
        //пустая история игры
        String suggested = game.suggestWord();
        Assertions.assertTrue(wD.containWord(suggested));
    }

    @Test
    public void shouldReturnWordMatchingSingleHistory() {
        //один ход
        game.history.add(new WordleGame.Turn("кабан", "++---"));
        String suggested = game.suggestWord();

        Assertions.assertTrue(wD.containWord(suggested));
        Assertions.assertEquals("++---", wD.compareWords("кабан", suggested));
    }

    @Test
    public void shouldReturnWordMatchingNotSingleHistory() {
        //несколько ходов в истории
        String target = "катер";
        String guess1 = "кабан";
        String hint1 = wD.compareWords(target, guess1);
        game.history.add(new WordleGame.Turn(guess1, hint1));

        String guess2 = "лампа";
        String hint2 = wD.compareWords(target, guess2);
        game.history.add(new WordleGame.Turn(guess2, hint2));

        String suggested = game.suggestWord();
        Assertions.assertTrue(wD.containWord(suggested));

        //проверим что догадка согласована с историей
        Assertions.assertEquals(hint1, wD.compareWords(suggested, guess1));
        Assertions.assertEquals(hint2, wD.compareWords(suggested, guess2));
    }
}
