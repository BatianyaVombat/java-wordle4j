package ru.yandex.practicum;

import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;


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
    private static final WordleDictionary wDictionary = new WordleDictionary("src/text.txt");

    public static void main(String[] args) {
        try {
            List<String> outWords = wDictionary.getCleanDictionary();

            for(String word : outWords){
                System.out.println(word);
            }
        } catch (IOException e) {
            System.out.println("Файл со словарём пустой!");
        }
    }
}
