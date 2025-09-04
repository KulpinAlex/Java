package s21;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

  public static int scan_value(Scanner scanner) {
    int scan_result = 0;
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


  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int number = scan_value(scanner);
    List<User> users = new ArrayList<>();
    for (int i = 0; i < number; i++) {
      scanner.nextLine();
      String name = scanner.nextLine();
      int age = scan_value(scanner);
      if (age <= 0) {
        System.out.println("Incorrect input. Age <= 0");
        i--;
        continue;
      }
      users.add(new User(name, age));
    }
    String result = users.stream()
        .filter(user -> user.getAge() >= 18)
        .map(User::getName)
        .collect(Collectors.joining(", "));
    System.out.println(result);
  }
}
