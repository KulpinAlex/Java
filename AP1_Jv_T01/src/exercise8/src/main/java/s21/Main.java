package s21;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    int scan_result = 0;
    int scan_last = 0;
    int pos = 0;
    Scanner scanner = new Scanner(System.in);
    boolean check_scan = false;
    while (true) {
      try {
        scan_result = scanner.nextInt();
        if (check_scan && scan_result < scan_last) {
          System.out.println(
              "The sequence is not ordered from the ordinal number of the number " + pos);
          break;
        }
        pos++;
        check_scan = true;
        scan_last = scan_result;
      } catch (InputMismatchException e) {
        if (check_scan) {
          System.out.println("The sequence is ordered in ascending order");
        }
        break;
      }
    }
    if (!check_scan) {
      System.out.println("Input error");
    }
  }
}