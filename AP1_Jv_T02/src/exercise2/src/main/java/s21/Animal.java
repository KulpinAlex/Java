package s21;

public abstract class Animal {

  private String name;
  private int age;
  private double weight;

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public double getWeight() {
    return weight;
  }

  public abstract double getFeedInfoKg();

  public Animal(String name, int age, double weight) {
    this.name = name;
    this.age = age;
    this.weight = weight;
  }
}
