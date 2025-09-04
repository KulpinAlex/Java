package s21;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

  public static int scan_value() {
    boolean check_scan = false;
    int scan_result = 0;
    Scanner scanner = new Scanner(System.in);
    while (!check_scan) {
      try {
        scan_result = scanner.nextInt();
        check_scan = true;
      } catch (InputMismatchException e) {
        System.out.println("Couldn't parse a number. Please, try again");
        scanner.nextLine();
        continue;
      }
      if (scan_result < 0) {
        System.out.println("Incorrect time");
        check_scan = false;
      }
    }
    return scan_result;
  }

  public static int[] get_time(int time) {
    int sec = time % 60;
    int min = ((time - sec) / 60) % 60;
    int hour = (time - min - sec) / 3600;
    return new int[]{hour, min, sec};
  }

  private static void print_time(int[] arrayTime) {
    System.out.printf("%02d:%02d:%02d", arrayTime[0], arrayTime[1], arrayTime[2]);
  }

  public static void main(String[] args) {
    print_time(get_time(scan_value()));
  }
}