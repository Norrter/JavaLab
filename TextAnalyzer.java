import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextAnalyzer {

    public String text;
    public char[] vowels = new char[]{'а','о','у','и','э','ы','я','ю','е','ё'};
    public int vowelCount = 0;;

    // конструктор по умолчанию
    public TextAnalyzer() {
        this.text = "Пример текста для анализа";
    }

    public TextAnalyzer(String text) {
        this.text = text;
    }


    // Геттеры
    public String getText() {
        return text;
    }

    public char[] getVowels() {
        return vowels;
    }

    public int getVowelCount() {
        return vowelCount;
    }

    // Сеттеры
    public void setText(String text) {
        this.text = text;
    }

    // Основной метод анализа
    public int analyze() {
        vowelCount = 0;

        if (text == null || text.isEmpty()) {
            System.out.println("Ошибка: текст пустой или null");
            return 0;
        }

        String lowerText = text.toLowerCase();


        for (int i = 0; i < lowerText.length(); i++) {
            for (int j = 0; j < vowels.length; j++) {
                if (lowerText.charAt(i) == vowels[j]) {
                    vowelCount++;
                    break;
                }
            }
        }

        return vowelCount;
    }

    public void printAnalysis() {
        analyze();
        System.out.println("Текст: \"" + text + "\"");
        System.out.println("Количество гласных: " + vowelCount);
    }
}