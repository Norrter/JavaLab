import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Operation {

    public char operation;
    public int operand;
    public int[] arr, resArr;

    public Operation() {
        this.operand = 2;
        this.operation = '+';
        this.arr = new int[]{1, 2, 3, 4, 5};
    }

    public Operation(char operation, int operand, int[] arr) {
        this.operand = operand;
        this.operation = operation;
        this.arr = arr;
    }

    public char getOperation() {
        return operation;
    }

    public int getOperand() {
        return operand;
    }

    public int[] getArr() {
        return arr;
    }

    public int[] getResArr() {
        return resArr;
    }

    // Сеттеры
    public void setOperation(char operation) {
        this.operation = operation;
    }

    public void setOperand(int operand) {
        this.operand = operand;
    }

    public void setOArray(int[] array) {
        this.arr = array;
    }

    public int[] calculate() {
        resArr = new int[arr.length];
        switch (operation) {
            case '+':
                for (int i = 0; i < arr.length; i++) {
                    resArr[i] = arr[i] + operand;
                }
                break;
            case '-':
                for (int i = 0; i < arr.length; i++) {
                    resArr[i] = arr[i] - operand;
                }
                break;
            case '*':
                for (int i = 0; i < arr.length; i++) {
                    resArr[i] = arr[i] * operand;
                }
                break;
            case '/':
                if (operand == 0) {
                    System.out.println("Ошибка: деление на ноль!");
                    return null;
                }
                for (int i = 0; i < arr.length; i++) {
                    resArr[i] = arr[i] / operand;
                }
                break;

            default:
                System.out.println("Ошибка: операция '" + operation + "' не поддерживается");
        }
        return resArr;
    }

    public void printResult() {
        int[] result = calculate();
        if (result != null) {
            System.out.println("Операция: " + operation);
            System.out.println("Операнд: " + operand);
            System.out.print("Входные данные: " + java.util.Arrays.toString(arr));
            System.out.print("\n" + "Результат: " + java.util.Arrays.toString(resArr));
        } else {
            System.out.println("Ошибка при вычислении");
        }
    }
}