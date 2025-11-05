import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Menu menu = new Menu(scanner);
        menu.start();

        scanner.close();
        System.out.println("Программа завершена.");
    }
}