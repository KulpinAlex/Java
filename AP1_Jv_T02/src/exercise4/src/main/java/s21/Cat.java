package s21;

public class Cat extends Animal {

  public Cat(String name, int age) {
    super(name, age);
  }

  @Override
  public String toString() {
    return "Cat name = " + super.getName() + ", age = " + super.getAge();
  }
}
