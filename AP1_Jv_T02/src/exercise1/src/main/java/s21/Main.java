package s21;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int countAnimals = scanValue(sc);
    while (countAnimals <= 0) {
      System.out.println("Incorrect input. Count pets <= 0");
      countAnimals = scanValue(sc);
    }
    List<Animal> pets = new ArrayList<>();
    for (int i = 0; i < countAnimals; i++) {
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
    }
    for (Animal animal : pets) {
      System.out.println(animal);
    }
    sc.close();
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
    while (!sc.hasNextInt()) {
      System.out.println("Couldn't parse a number. Please, try again");
      sc.next();
    }
    return sc.nextInt();
  }
}