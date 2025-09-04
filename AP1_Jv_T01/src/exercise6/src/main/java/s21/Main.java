package s21;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

  public static double[] scan_array(int number) {
    double[] scan_result = new double[number];
    Scanner scanner = new Scanner(System.in);
    for (int i = 0; i < number; i++) {
      boolean check_scan = false;
      while (!check_scan) {
        try {
          scan_result[i] = scanner.nextDouble();
          check_scan = true;
        } catch (InputMismatchException e) {
          System.out.println("Couldn't parse a number. Please, try again");
          scanner.nextLine();
        }
      }
    }
    return scan_result;
  }

  public static int scan_value() {
    int scan_result = 0;
    Scanner scanner = new Scanner(System.in);
      boolean check_scan = false;
      while (!check_scan) {
        try {
          scan_result = scanner.nextInt();
          check_scan = true;
        } catch (InputMismatchException e) {
          System.out.println("Couldn't parse a number. Please, try again");
          scanner.nextLine();
        }
      }
    return scan_result;
  }

  public static void sortValues(double[] arr) {
    int n = arr.length;
    for (int i = 0; i < n - 1; i++) {
      for (int j = i + 1; j < n; j++) {
        if (arr[i] > arr[j]) {
          double temp = arr[i];
          arr[i] = arr[j];
          arr[j] = temp;
        }
      }
    }
  }

  public static void main(String[] args) {
    int n = scan_value();
    if (n <= 0) {
      System.out.println("Input error. Size <= 0");
    } else {
      double[] arrValues = scan_array(n);
      sortValues(arrValues);
      for (double value : arrValues) {
        System.out.print(value + " ");
      }
    }
  }
}