
import java.util.ArrayList;
import java.util.List;


public class TextReverser {

    public String text;
    public List<String> words;
    public String reversedText;

    public TextReverser() {
        this.text = "Пример текста для разворота слов";
        this.words = new ArrayList<>();
        this.reversedText = "";
    }

    public TextReverser(String text) {
        this.text = text;
        this.words = new ArrayList<>();
        this.reversedText = "";
    }


    public String getText() {
        return text;
    }

    public List<String> getWords() {
        return words;
    }

    public String getReversedText() {
        return reversedText;
    }

    public void setText(String text) {
        this.text = text;
    }

    // Основной метод разворота слов
    public String reverseWords() {
        words.clear();
        int start = -1;

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != ' ' && start == -1) {
                start = i;
            } else if ((text.charAt(i) == ' ' || i == text.length() - 1) && start != -1) {
                int end = (text.charAt(i) == ' ') ? i : i + 1;
                words.add(text.substring(start, end));
                start = -1;
            }
        }

        // Формируем результирующую строку
        StringBuilder result = new StringBuilder();
        for (int i = words.size() - 1; i >= 0; i--) {
            result.append(words.get(i));
            if (i > 0) {
                result.append(" ");
            }
        }

        reversedText = result.toString();
        return reversedText;
    }

    public void printResult() {
        reverseWords();
        System.out.println("Исходный текст: \"" + text + "\"");
        System.out.println("Результат: \"" + reversedText + "\"");
    }
}