package s21;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int countAnimals = scanIntValue(sc);
    while (countAnimals <= 0) {
      System.out.println("Incorrect input. Count pets <= 0");
      countAnimals = scanIntValue(sc);
    }
    List<Animal> pets = new ArrayList<>();
    List<String> type_names = Arrays.asList("dog", "cat", "hamster", "guinea");
    for (int i = 0; i < countAnimals; i++) {
      String type_animal = sc.next();
      if (!type_names.contains(type_animal)) {
        System.out.println("Incorrect input. Unsupported pet type");
      } else {
        Animal pet = scanPet(sc, type_animal);
        if (pet != null && pet.getAge() <= 0) {
          System.out.println("Incorrect input. Age <= 0");
        } else {
          pets.add(pet);
        }
      }
    }
    for (Animal animal : pets) {
      if (animal.getClass() == GuineaPig.class || animal.getClass() == Hamster.class) {
        System.out.println(animal);
      }
    }

    for (Animal animal : pets) {
      if (animal.getClass() == Dog.class || animal.getClass() == Cat.class) {
        System.out.println(animal);
      }
    }

    sc.close();
  }

  private static Animal scanPet(Scanner sc, String type) {
    String name = sc.next();
    int age = scanIntValue(sc);
    return switch (type) {
      case "cat" -> new Cat(name, age);
      case "hamster" -> new Hamster(name, age);
      case "guinea" -> new GuineaPig(name, age);
      case "dog" -> new Dog(name, age);
      default -> null;
    };
  }

  private static int scanIntValue(Scanner sc) {
    while (!sc.hasNextInt()) {
      System.out.println("Couldn't parse a number. Please, try again");
      sc.next();
    }
    return sc.nextInt();
  }
}