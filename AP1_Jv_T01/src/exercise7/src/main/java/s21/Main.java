package s21;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

  public static double[] scanFile(String path) throws FileNotFoundException {
    int scan_result = 0;
    Scanner scanner = new Scanner(new File(path));
    boolean check_scan = false;
    while (!check_scan && (scanner.hasNext())) {
      try {
        scan_result = scanner.nextInt();
        check_scan = true;
      } catch (InputMismatchException e) {
        System.out.println("Couldn't parse a number. Please, try again");
        scanner.nextLine();
      }
    }

    double[] arr = new double[0];
    int check_scan_result = 0;
    if (scan_result > 0) {
      arr = new double[scan_result];
      for (int i = 0; i < scan_result && scanner.hasNext(); i++) {
        check_scan = false;
        while (!check_scan) {
          try {
            arr[i] = scanner.nextDouble();
            check_scan = true;
            check_scan_result++;
          } catch (InputMismatchException e) {
            scanner.next();
          }
        }
      }
      if (check_scan_result != scan_result) {
        System.out.println("Input error. Insufficient number of elements");
        arr = new double[0];
      } else {
        System.out.println(scan_result);
        for (double d : arr) {
          System.out.print(d + " ");
        }
        System.out.println();
      }
    } else if (!check_scan) {
      System.out.println("Input error. Insufficient number of elements");
    } else {
      System.out.println("Input error. Size <= 0");
    }
    scanner.close();
    return arr;
  }

  public static void saveMinMaxValue(double[] arr) throws IOException {
    if (arr.length != 0) {
      double max_value = arr[0];
      double min_value = arr[0];
      for (double d : arr) {
        if (d > max_value) {
          max_value = d;
        }
        if (d < min_value) {
          min_value = d;
        }
      }
      File test = new File("src/main/java/s21/result.txt");
      Files.writeString(test.toPath(), min_value + " " + max_value);
      System.out.println("Saving min and max values in file");
    }
  }

  public static void main(String[] args) {
    String path = "src/main/java/s21/file1.txt";
    try {
      saveMinMaxValue(scanFile(path));
    } catch (FileNotFoundException e) {
      System.out.println("Input error. File isn't exist");
    } catch (IOException e) {
      System.out.println("Output error. Access denied");
    }
  }
}