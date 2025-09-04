package s21;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

  public static int[] scan_value(int number) {
    int[] scan_result = new int[number];
    Scanner scanner = new Scanner(System.in);
    for (int i = 0; i < number; i++) {
      boolean check_scan = false;
      while (!check_scan) {
        try {
          scan_result[i] = scanner.nextInt();
          check_scan = true;
        } catch (InputMismatchException e) {
          System.out.println("Couldn't parse a number. Please, try again");
          scanner.nextLine();
        }
      }
    }
    return scan_result;
  }

  public static int[] check_values(int[] arrValues) {
    int n = 0;
    for (int arrValue : arrValues) {
      if (checkNumber(arrValue)) {
        n++;
      }
    }
    int[] result = new int[n];
    if (n > 0) {
      int i = 0;
      for (int arrValue : arrValues) {
        if (checkNumber(arrValue)) {
          result[i] = arrValue;
          i++;
        }
      }
    }
    return result;
  }

  public static boolean checkNumber(int value) {
    value = value < 0 ? value * -1 : value;
    boolean check_result = false;
    if (value < 10) {
      check_result = true;
    } else {
      int lastNumber = value % 10;
      int firstNumber = 0;
      while (value > 0) {
        firstNumber = value % 10;
        value /= 10;
      }
      if (firstNumber == lastNumber) {
        check_result = true;
      }
    }
    return check_result;
  }

  public static void main(String[] args) {
    int[] n = scan_value(1);
    if (n[0] <= 0) {
      System.out.println("Input error. Size <= 0");
    } else {
      int[] arrValues = scan_value(n[0]);
      int[] printValues = check_values(arrValues);
      if (printValues.length > 0) {
        for (int printValue : printValues) {
          System.out.print(printValue + " ");
        }
      } else {
        System.out.println("There are no such elements");
      }
    }
  }

}
