package s21;

public class GuineaPig extends Animal implements Herbivore {

  public GuineaPig(String name, int age) {
    super(name, age);
  }

  @Override
  public String chill() {
    return "I can chill for 12 hour";
  }

  @Override
  public String toString() {
    return String.format("GuineaPig name = %s, age = %d. %s", super.getName(), super.getAge(),
        chill());
  }
}
