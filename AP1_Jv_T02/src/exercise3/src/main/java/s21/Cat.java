package s21;

public class Cat extends Animal implements Omnivore {

  public Cat(String name, int age) {
    super(name, age);
  }

  @Override
  public String hunt() {
    return "I can hunt for mice";
  }

  @Override
  public String toString() {
    return String.format("Cat name = %s, age = %d. %s", super.getName(), super.getAge(), hunt());
  }
}