package ru.yandex.practicum;

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
public class WordleGame {
    private String answer;
    private int steps;
    private WordleDictionary dictionary;

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
        this.answer = dictionary.getRandomWord();
        this.steps = 6;
    }

    public void startGame() {
        Scanner sc = new Scanner(System.in);
        String userAnswer;
        String clue;
        System.out.println("Я загадал существительное из 5 букв в единственном числе и " +
                "у тебя есть 6 попыток чтобы его угадать");

        WordleLogger.infoLog("Компьютер загадал слово - " + answer);

        do {
            System.out.println("\n" + "-".repeat(10));
            System.out.println("Введите ваш ответ");
            userAnswer = sc.nextLine().toLowerCase().replace("ё", "е").trim();

            //ЛОГИ
            WordleLogger.infoLog("Текущее количество попыток: " + steps + "\nПользователь ввёл слово: " + userAnswer);


            if (userAnswer.isEmpty() || userAnswer.length() != 5 || !dictionary.containWord(userAnswer)) {
                System.out.println("Слово состоит не из 5 букв, либо не из словаря. Введите снова!");
                WordleLogger.infoLog("Пользователь слово неверной длины, либо его нет в словаре");

            } else if (userAnswer.equals(answer)) {
                System.out.println("Ура, вы победили!");
                WordleLogger.infoLog("Пользователь угадал слово");
                return;

            } else {
                clue = dictionary.compareWords(answer, userAnswer);
                System.out.println(clue);
                steps--;
                System.out.println("У вас осталось " + steps + " попыток");
                WordleLogger.infoLog("Пользователь не угадал слово на данной попытке.\nСтрока подсказка: " + clue);
            }

        } while (steps > 0);
        System.out.println("Кажется у вас кончились попытки :(\nЗагаданное слово: " + answer);
        WordleLogger.infoLog("Игра закончилась проигрышем пользователя");
    }
}
