package ru.yandex.practicum;

import java.io.IOException;
import java.util.List;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {
    static void main(String[] args) {
        try {
            WordleDictionary wDictionary = new WordleDictionary("src/text.txt");
            System.out.println(wDictionary.getNormalizedDictionary());
        } catch (IOException | IllegalAccessException e) {
            System.out.println("Проблема со словарём! Поиграем в другой раз!");
        }
    }
}
