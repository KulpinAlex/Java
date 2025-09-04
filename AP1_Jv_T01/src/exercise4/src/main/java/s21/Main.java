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

  public static void main(String[] args) {
    int[] n = scan_value(1);
    if (n[0] <= 0) {
      System.out.println("Input error. Size <= 0");
    } else {
      int sum = 0;
      int number = 0;
      int[] val = scan_value(n[0]);
      for (int i = 1; i < val.length; i++) {
        if (val[i] < 0) {
          sum += val[i];
          number++;
        }
      }
      if (number == 0) {
        System.out.println("There are no negative elements");
      } else {
        System.out.println(sum / number);
      }
    }
  }
}