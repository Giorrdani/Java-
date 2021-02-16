
public class Main {

    public static void main(String[] args) throws InterruptedException {
      System.out.println("Задание 1 и 2" + "\n");

      Cat cat = new Cat(120, 120);
      cat.jumping();
      cat.running();

      Human human = new Human(2000, 150);
      human.jumping();
      human.running();

      Robot robot = new Robot(10000, 250);
      robot.jumping();
      robot.running();
    }
}