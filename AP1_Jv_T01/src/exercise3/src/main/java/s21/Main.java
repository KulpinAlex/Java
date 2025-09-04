package s21;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

  public static long fibonacci(long n) {
    if (n <= 1) {
      return n;
    }
    return fibonacci(n - 1) + fibonacci(n - 2);
  }

  public static long scan_value() {
    boolean check_scan = false;
    long scan_result = 0;
    Scanner scanner = new Scanner(System.in);
    while (!check_scan) {
      try {
        scan_result = scanner.nextLong();
        check_scan = true;
      } catch (InputMismatchException e) {
        System.out.println("Couldn't parse a number. Please, try again");
        scanner.nextLine();
        continue;
      }
      if (scan_result < 0) {
        System.out.println("Couldn't parse a number. Please, try again");
        check_scan = false;
      }
    }
    return scan_result;
  }

  public static void find_fibonacchi() {
    long n = scan_value();
    if (n > 92) {
      System.out.println("Too large n");
      return;
    }
    System.out.println(fibonacci(n));
  }

  public static void main(String[] args) {
    find_fibonacchi();
  }
}