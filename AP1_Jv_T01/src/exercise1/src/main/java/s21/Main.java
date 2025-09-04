package s21;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

  public static boolean check_array(double[] array) {
    double check_value = array[0];
    for (int i = 1; i < array.length; i++) {
      if (check_value != array[i]) {
        return true;
      }
    }
    return false;
  }

  public static double Perimeter(double[] array_x, double[] array_y) {
    double perimeter = 0;
    for (int i = 0; i < array_x.length; i++) {
      perimeter += Math.sqrt(Math.pow(array_x[i] - array_x[(i + 2) % 3], 2) + Math.pow(
          array_y[i] - array_y[(i + 2) % 3], 2));
    }
    return perimeter;
  }

  public static void scan_value(double[] value, int i) {
    boolean check_scan = false;
    Scanner scanner = new Scanner(System.in);
    while (!check_scan) {
      try {
        value[i] = scanner.nextDouble();
        check_scan = true;
      } catch (InputMismatchException e) {
        System.out.println("Couldn't parse a number. Please, try again");
        scanner.nextLine();
      }
    }
  }

  public static void main(String[] args) {
    boolean check = false;
    double[] x_array = new double[3];
    double[] y_array = new double[3];
    while (!check) {
      for (int i = 0; i < 3; i++) {
        scan_value(x_array, i);
        scan_value(y_array, i);
      }
      if (check_array(x_array) && check_array(y_array)) {
        check = true;
        System.out.printf("Perimeter: %.3f", Perimeter(x_array, y_array));
      } else {
        System.out.println("It isn't triangle");
      }
    }
  }
}