package s21;

import java.util.concurrent.TimeUnit;

public class Dog extends Animal {

  public Dog(String name, int age) {
    super(name, age);
  }

  @Override
  public double goToWalk() {
    try {
      TimeUnit.MILLISECONDS.sleep((long) ((getAge() * 0.5) * 1000.0));
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    return getAge() * 0.5;
  }

  @Override
  public String toString() {
    return "Dog name = " + super.getName() + ", age = " + super.getAge();
  }
}
