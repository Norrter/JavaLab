import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner;

    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        int choice = -1;

        do {
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("║           ГЛАВНОЕ МЕНЮ           ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║ 1. Операции с массивами          ║");
            System.out.println("║ 2. Анализ текста                 ║");
            System.out.println("║ 3. Разворот слов                 ║");
            System.out.println("║ 0. Выход                         ║");
            System.out.println("╚══════════════════════════════════╝");
            System.out.print("Выберите задание: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        processArrayOperations();
                        break;
                    case 2:
                        processTextAnalysis();
                        break;
                    case 3:
                        processWordReversal();
                        break;
                    case 0:
                        System.out.println("Выход из программы...");
                        break;
                    default:
                        System.out.println("Неверный выбор! Попробуйте снова.");
                }
            } catch (Throwable ex) {
                System.out.println("Ошибка: вы ввели не число! Пожалуйста, введите цифру от 0 до 3.");
                scanner.nextLine();
                choice = -1;
            }
        } while (choice != 0);
    }

    private void processArrayOperations() throws IOException {
        boolean success = false;

        while (!success) {
            try {
                int constructorChoice;
                Operation operation;

                System.out.println("\n--- ОПЕРАЦИИ С МАССИВАМИ ---");

                System.out.println("Выберите конструктор:");
                System.out.println("1 - Конструктор по умолчанию");
                System.out.println("2 - Конструктор с параметрами");
                System.out.println("3 - Конструктор из файла");
                System.out.println("0 - Вернуться в главное меню");
                System.out.print("Ваш выбор: ");

                constructorChoice = scanner.nextInt();
                scanner.nextLine();

                if (constructorChoice == 0) {
                    System.out.println("Возврат в главное меню...");
                    return;
                }

                switch (constructorChoice) {
                    case 1:
                        operation = new Operation();
                        System.out.println("Создан объект через конструктор по умолчанию");
                        break;

                    case 2:
                        System.out.print("Введите операцию (+, -, *, /): ");
                        String opInput = scanner.nextLine();
                        if (opInput.length() != 1) {
                            throw new IllegalArgumentException("Операция должна быть одним символом");
                        }
                        char op = opInput.charAt(0);

                        System.out.print("Введите операнд: ");
                        int operand;
                        try {
                            operand = scanner.nextInt();
                        } catch (Throwable e) {
                            throw new IllegalArgumentException("Операнд должен быть числом");
                        }

                        System.out.print("Введите размер массива: ");
                        int size;
                        try {
                            size = scanner.nextInt();
                            if (size <= 0) {
                                throw new IllegalArgumentException("Размер массива должен быть положительным числом");
                            }
                        } catch (Throwable e) {
                            throw new IllegalArgumentException("Размер массива должен быть числом");
                        }
                        scanner.nextLine();

                        int[] arr = new int[size];
                        System.out.println("Введите элементы массива:");
                        for (int i = 0; i < size; i++) {
                            System.out.print("Элемент " + (i + 1) + ": ");
                            try {
                                arr[i] = scanner.nextInt();
                            } catch (Throwable e) {
                                throw new IllegalArgumentException("Элемент массива должен быть числом");
                            }
                        }
                        scanner.nextLine();

                        operation = new Operation(op, operand, arr);
                        System.out.println("Создан объект через конструктор с параметрами");
                        break;

                    case 3:
                        String path = "src/data/OperFile.txt";
                        File file = new File(path);
                        Scanner scanner = new Scanner(file);

                        if (!scanner.hasNextLine()) {
                            throw new IllegalArgumentException("Файл пустой");
                        }

                        String line = scanner.nextLine();
                        String[] preArr = line.split(" ");

                        List<Integer> numbersList = new ArrayList<>();

                        for (String str : preArr) {
                            try {
                                if (!str.trim().isEmpty()) {
                                    numbersList.add(Integer.parseInt(str.trim()));
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Ошибка: '" + str + "' не является числом, используется 0");
                                numbersList.add(0);
                            }
                        }

                        int[] array = new int[numbersList.size()];
                        for (int i = 0; i < numbersList.size(); i++) {
                            array[i] = numbersList.get(i);
                        }

                        line = scanner.nextLine();
                        if (line == null) {
                            throw new IllegalArgumentException("В файле не указана операция");
                        }
                        if (line.length() != 1) {
                            throw new IllegalArgumentException("Строка должна содержать ровно один символ");
                        }
                        char operation1 = line.charAt(0);

                        int operand1 = scanner.nextInt();

                        operation = new Operation(operation1, operand1, array);

                        scanner.close();
                        break;

                    default:
                        System.out.println("Неверный выбор, используется конструктор по умолчанию");
                        operation = new Operation();
                }

                System.out.println("\nЗаписать результаты в текстовый файл?(да/нет)");
                String chres = scanner.nextLine();
                chres = chres.toLowerCase();

                if (chres.equals("да")) {
                    operation.calculate();
                    FileWriter writer = null;
                    try {
                        for (int i = 0; i < 100; i++) {
                            String path = "src/results/resultOperation" + i + ".txt";
                            File file = new File(path);
                            if (!file.exists()) {
                                writer = new FileWriter(path);

                                writer.write("Операция: " + operation.getOperation() + "\n");
                                writer.write("Операнд: " + operation.getOperand() + "\n");
                                writer.write("Массив: [");
                                int[] inputArr = operation.getArr();
                                for (int j = 0; j < inputArr.length; j++) {
                                    writer.write(String.valueOf(inputArr[j]));
                                    if (j != inputArr.length - 1) {
                                        writer.write(", ");
                                    }
                                }
                                writer.write("]\n\n");


                                writer.write("Результат: [");
                                int[] resultArr = operation.getResArr();
                                for (int j = 0; j < resultArr.length; j++) {
                                    writer.write(String.valueOf(resultArr[j]));
                                    if (j != resultArr.length - 1) {
                                        writer.write(", ");
                                    }
                                }
                                writer.write("]");

                                System.out.println("Данные успешно записаны в файл");
                                break;
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Ошибка записи: " + e);
                    }

                    writer.close();

                }

                System.out.println("\n--- РЕЗУЛЬТАТ ---");
                operation.printResult();
                success = true;

            } catch (IOException ex) {
                System.out.println("Ошибка ввода: пожалуйста, вводите корректные числовые значения.");
                System.out.println("Пожалуйста, попробуйте снова.\n");
                scanner.nextLine();
            }
        }
    }

    private void processTextAnalysis() {
        boolean success = false;

        while (!success) {
            try {
                System.out.println("\n--- АНАЛИЗ ТЕКСТА ---");

                System.out.println("Выберите конструктор:");
                System.out.println("1 - Конструктор по умолчанию");
                System.out.println("2 - Конструктор с параметрами");
                System.out.println("3 - Конструктор из файла");
                System.out.println("0 - Вернуться в главное меню");
                System.out.print("Ваш выбор: ");

                int constructorChoice;
                try {
                    constructorChoice = scanner.nextInt();
                } catch (Throwable e) {
                    throw new IllegalArgumentException("Выбор конструктора должен быть числом");
                }
                scanner.nextLine();

                if (constructorChoice == 0) {
                    System.out.println("Возврат в главное меню...");
                    return;
                }

                TextAnalyzer analyzer = null;

                switch (constructorChoice) {
                    case 1:
                        analyzer = new TextAnalyzer();
                        System.out.println("Создан объект через конструктор по умолчанию");
                        break;

                    case 2:
                        System.out.print("Введите текст для анализа: ");
                        String text = scanner.nextLine();
                        if (text == null || text.trim().isEmpty()) {
                            throw new IllegalArgumentException("Текст не может быть пустым");
                        }
                        if (text.length() > 1000) {
                            throw new IllegalArgumentException("Текст слишком длинный (максимум 1000 символов)");
                        }
                        analyzer = new TextAnalyzer(text);
                        System.out.println("Создан объект через конструктор с параметрами");
                        break;

                    case 3:
                        File file = new File("src/data/TextFile.txt");
                        try {
                            Scanner scanner = new Scanner(file);
                            text = scanner.nextLine();
                            if (text == null) {
                                throw new IllegalArgumentException("файл пустой");
                            }
                            scanner.close();
                            analyzer = new TextAnalyzer(text);
                        } catch (FileNotFoundException e) {
                            System.out.println("Ошибка чтения из файла:" + e);
                        }
                        break;

                    default:
                        System.out.println("Неверный выбор, используется конструктор по умолчанию");
                        analyzer = new TextAnalyzer();
                }

                System.out.println("\nЗаписать результаты в текстовый файл?(да/нет)");
                analyzer.analyze();
                String chres = scanner.nextLine();
                chres = chres.toLowerCase();

                if (chres.equals("да")) {
                    FileWriter writer = null;
                    try {
                        for (int i = 0; i < 100; i++) {
                            String path = "src/results/resultAnalyze" + i + ".txt";
                            File file = new File(path);
                            if (!file.exists()) {
                                writer = new FileWriter(path);
                                writer.write("Текст: " + analyzer.text);
                                writer.write("\nКол-во гласных букв: " + analyzer.vowelCount);
                                System.out.print("Данные успешно записаны");
                                break;
                            }
                        }
                    } catch (IOException e) {
                        System.out.print("Ошибка записи: " + e);
                    }
                    writer.close();
                }


                System.out.println("\n--- РЕЗУЛЬТАТ АНАЛИЗА ---");
                analyzer.printAnalysis();
                success = true;

            } catch (Throwable ex) {
                System.out.println("Ошибка ввода: пожалуйста, вводите корректные значения.");
                System.out.println("Пожалуйста, попробуйте снова.\n");
                scanner.nextLine();
            }
        }
    }

    private void processWordReversal() {
        boolean success = false;

        while (!success) {
            try {
                System.out.println("\n--- РАЗВОРОТ СЛОВ ---");

                System.out.println("Выберите конструктор:");
                System.out.println("1 - Конструктор по умолчанию");
                System.out.println("2 - Конструктор с параметрами");
                System.out.println("3 - Конструктор из файла");
                System.out.println("0 - Вернуться в главное меню");
                System.out.print("Ваш выбор: ");

                int constructorChoice;
                try {
                    constructorChoice = scanner.nextInt();
                } catch (Throwable e) {
                    throw new IllegalArgumentException("Выбор конструктора должен быть числом");
                }
                scanner.nextLine();

                if (constructorChoice == 0) {
                    System.out.println("Возврат в главное меню...");
                    return;
                }

                TextReverser reverser = null;

                switch (constructorChoice) {
                    case 1:
                        reverser = new TextReverser();
                        System.out.println("Создан объект через конструктор по умолчанию");
                        break;

                    case 2:
                        System.out.print("Введите текст для разворота: ");
                        String text = scanner.nextLine();
                        if (text == null || text.trim().isEmpty()) {
                            throw new IllegalArgumentException("Текст не может быть пустым");
                        }
                        if (text.length() > 1000) {
                            throw new IllegalArgumentException("Текст слишком длинный (максимум 1000 символов)");
                        }
                        reverser = new TextReverser(text);
                        System.out.println("Создан объект через конструктор с параметрами");
                        break;

                    case 3:
                        File file = new File("src/data/TextFile.txt");
                        try {
                            Scanner scanner = new Scanner(file);
                            text = scanner.nextLine();
                            if (text == null) {
                                throw new IllegalArgumentException("файл пустой");
                            }
                            reverser = new TextReverser(text);

                            scanner.close();
                        } catch (FileNotFoundException e) {
                            System.out.println("Ошибка чтения из файла:" + e);
                        }
                        break;

                    default:
                        System.out.println("Неверный выбор, используется конструктор по умолчанию");
                        reverser = new TextReverser();
                }

                System.out.println("\nЗаписать результаты в текстовый файл?(да/нет)");
                String chres = scanner.nextLine();
                chres = chres.toLowerCase();

                if (chres.equals("да")) {
                    reverser.reverseWords();
                    FileWriter writer = null;
                    try {
                        for (int i = 0; i < 100; i++) {
                            String path = "src/results/resultReverse" + i + ".txt";
                            File file = new File(path);
                            if (!file.exists()) {
                                writer = new FileWriter(path);
                                writer.write("Исходный текст: " + reverser.text);
                                writer.write("\nРазвернутый текст: " + reverser.reversedText);
                                System.out.print("Данные успешно записаны");
                                break;
                            }
                        }
                    } catch (IOException e) {
                        System.out.print("Ошибка записи: " + e);
                    }
                    writer.close();
                }

                System.out.println("\n--- РЕЗУЛЬТАТ РАЗВОРОТА ---");
                reverser.printResult();
                success = true; // Успешно завершили операцию


            } catch (Throwable ex) {
                System.out.println("Ошибка ввода: пожалуйста, вводите корректные значения.");
                System.out.println("Пожалуйста, попробуйте снова.\n");
                scanner.nextLine();
            }
        }
    }
}