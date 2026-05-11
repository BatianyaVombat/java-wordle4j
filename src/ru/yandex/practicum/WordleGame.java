package ru.yandex.practicum;

import java.util.LinkedList;
import java.util.Scanner;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
class WordleGame {
    private final String answer;
    private int steps;
    private final WordleDictionary dictionary;
    final LinkedList<Turn> history = new LinkedList<>();

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
        this.answer = dictionary.getRandomWord();
        this.steps = 6;
    }

    public void startGame() {
        Scanner sc = new Scanner(System.in);
        String userAnswer;
        String clue;

        printInfo();

        WordleLogger.infoLog(STR."Компьютер загадал слово - \{answer}");

        do {
            System.out.println("\n" + "-".repeat(10));
            System.out.println("Введите ваш ответ");
            userAnswer = sc.nextLine().toLowerCase().replace("ё", "е").trim();

            try {
                if (userAnswer.isEmpty()) {
                    userAnswer = suggestWord();

                    System.out.println(userAnswer);
                    WordleLogger.infoLog("Текущее количество попыток: " + steps + "\nКомпьютер предложил слово: " + userAnswer);
                }

                validateWord(userAnswer);
                if(userAnswer.equals(answer)){
                    System.out.println("Ура, вы победили!");
                    WordleLogger.infoLog("Слово угадано");
                    return;
                } else {
                    clue = dictionary.compareWords(answer, userAnswer);
                    System.out.println(clue);
                    steps--;
                    history.add(new Turn(userAnswer, clue));//записываем слово-попытку и последовательность символов

                    System.out.println("У вас осталось " + steps + " попыток");
                    WordleLogger.infoLog("Слово не угадано.\nСтрока подсказка: " + clue);
                }
            } catch (InvalidWordException e) {
                WordleLogger.infoLog(e.getMessage());
            }

        } while (steps > 0);
        System.out.println("Кажется у вас кончились попытки :(\nЗагаданное слово: " + answer);
        WordleLogger.infoLog("Игра закончилась проигрышем пользователя");
    }

    //Валидация слова пользователя
    void validateWord(String word) {
        if (word.length() != 5 || !dictionary.containWord(word)) {
            throw new InvalidWordException("Такого слова нет словаре или оно состоит не из 5 букв!");
        }
    }

    //Предлагает слово
    protected String suggestWord() {
        if(history.isEmpty()){
            return dictionary.getRandomWord();
        }

        for (String candidate : dictionary.normalizedDictionary) {
            boolean match = true;

            for (Turn turn : history) {
                if ((!dictionary.compareWords(candidate, turn.guess).equals(turn.hint))) {
                    match = false;
                    break;
                }
            }
            if (match) {
                return candidate;
            }
        }
        return dictionary.getRandomWord();
    }

    private void printInfo() {
        System.out.println("""
                Я загадал существительное из 5 букв в единственном числе и у тебя есть 6 попыток чтобы его угадать.
                После каждого хода ты будешь получать подсказку, которая будет выглядеть так -^++-
                Не пугайся, а лучше присмотрись к символам!
                
                - обозначает, что в загаданном слове нет такой буквы.
                ^ ты угадал букву, но она не на своём месте.
                + ты угадал букву и она на своём месте.
                Удачной игры!""");
    }

    //Класс для сохранения попытки
    static class Turn {
        String guess;
        String hint;


        public Turn(String guess, String hint) {
            this.guess = guess;
            this.hint = hint;
        }
    }
}
