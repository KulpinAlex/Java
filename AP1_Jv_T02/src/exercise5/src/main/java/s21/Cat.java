package s21;

import java.util.concurrent.TimeUnit;

public class Cat extends Animal {

  public Cat(String name, int age) {
    super(name, age);
  }

  @Override
  public double goToWalk() {
    try {
      TimeUnit.MILLISECONDS.sleep((long) ((getAge() * 0.25) * 1000.0));
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    return getAge() * 0.25;
  }

  @Override
  public String toString() {
    return "Cat name = " + super.getName() + ", age = " + super.getAge();
  }
}
