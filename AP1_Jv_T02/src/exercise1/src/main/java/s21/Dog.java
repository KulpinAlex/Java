package s21;

public class Dog extends Animal {

  public Dog(String name, int age) {
    super(name, age);
  }

  @Override
  public String toString() {
    return "Dog name = " + super.getName() + ", age = " + super.getAge();
  }
}
