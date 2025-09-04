package s21;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int countAnimals = Stream.generate(() -> scanValue(sc)).peek(val -> {
      if (val <= 0) {
        System.out.println("Incorrect input. Count pets <= 0");
      }
    }).filter(val -> val >= 1).findFirst().get();
    List<Animal> pets = new ArrayList<>();
    IntStream.range(0, countAnimals).forEachOrdered(i -> {
      String type_animal = sc.next();
      if (!type_animal.equals("dog") && !type_animal.equals("cat")) {
        System.out.println("Incorrect input. Unsupported pet type");
      } else {
        Animal pet = scanPet(sc, type_animal);
        if (pet.getAge() <= 0) {
          System.out.println("Incorrect input. Age <= 0");
        } else {
          pets.add(pet);
        }
      }
    });

    long startTime = System.currentTimeMillis();
    pets.forEach(pet -> new Thread(() -> {
      long startTask = System.currentTimeMillis();
      pet.goToWalk();
      System.out.printf(pet + ", start time = %.2f, end time = %.2f\n",
          (startTask - startTime) / 1000.0, (System.currentTimeMillis() - startTime) / 1000.0);
    }).start());
  }

  private static Animal scanPet(Scanner sc, String type) {
    String name = sc.next();
    int age = scanValue(sc);
    if (type.equals("cat")) {
      return new Cat(name, age);
    } else {
      return new Dog(name, age);
    }
  }

  private static int scanValue(Scanner sc) {
    return Stream.generate(() -> {
      if (sc.hasNextInt()) {
        return sc.nextInt();
      } else {
        System.out.println("Couldn't parse a number. Please, try again");
        sc.next();
        return null;
      }
    }).filter(val -> val != null).findFirst().get();
  }
}