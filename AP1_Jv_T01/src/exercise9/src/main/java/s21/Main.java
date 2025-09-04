package s21;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

  public static boolean StringFilter(String str, String filter) {
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == filter.charAt(0)) {
        boolean check = i + 1 < str.length();
        for (int j = 1; j < filter.length() && i + j < str.length(); j++) {
          if (str.charAt(i + j) != filter.charAt(j)) {
            check = false;
            break;
          }
          if (i + j == str.length() - 1 && j < filter.length() - 1) {
            check = false;
          }
        }
        if (check) {
          return true;
        }
      }
    }
    return false;
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int number = scanner.nextInt();
    List<String> list = new ArrayList<>();
    scanner.nextLine();
    for (int i = 0; i < number; i++) {
      list.add(scanner.nextLine());
    }
    String filter = scanner.nextLine();
    for (String s : list) {
      if (StringFilter(s, filter)) {
        System.out.printf(s + ", ");
      }
    }
  }
}