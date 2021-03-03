public class Main {
  Main m = new Main();
  public static void main(String[] args) {
    func1();
    func2();
  }

  public static void func1() {
    System.out.println("Метод 1 начат...");
    final int size = 10000;
    float[] arr = new float[size];

    for (int i = 0; i < arr.length; i++) {
      arr[i] = 1;
    }
    long a = System.currentTimeMillis();
    for (int i = 0; i < arr.length; i++) {
      arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
    }
    System.out.println((System.currentTimeMillis() - a) + "/мс - время выполнения метода 1 ");
    System.out.println("Метод 1 завершен!" + "\n");
  }

  public static void func2() {
      System.out.println("Метод 2 начат...");
      final int size = 10000000;
      final int h = size / 2;
      float[] arr = new float[size];
      float[] a1 = new float[h];
      float[] a2 = new float[h];

      for (int i = 0; i < arr.length; i++) {
        arr[i] = 1;
      }

      long b = System.currentTimeMillis();
      System.arraycopy(arr, 0, a1, 0, h);
      System.arraycopy(arr, h, a2, 0, h);
      System.out.println(System.currentTimeMillis() - b + "/мс - время разделение массива");

      Thread thread1 = new Thread(() -> {
        long c = System.currentTimeMillis();
        for (int i = 0; i < a1.length; i++) {
          a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println(System.currentTimeMillis() - c + "/мс  - время расчета первого массива");
      });

      Thread thread2 = new Thread(() -> {
        long d = System.currentTimeMillis();
        for (int i = 0; i < a2.length; i++) {
          a2[i] = (float) (a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println(System.currentTimeMillis() - d + "/мс - время расчета второго массива");
      });

      Thread splice = new Thread(()-> {
          long e = System.currentTimeMillis();
          System.arraycopy(a1, 0, arr, 0, h);
          System.arraycopy(a2, 0, arr, h, h);
          System.out.println(System.currentTimeMillis() - e + "/мс - время склейки массивов");
        });

      thread1.start();
      thread2.start();

      try {
      thread1.join();
      thread2.join();
    } catch (InterruptedException exception) {
      exception.printStackTrace();
    }

      splice.start();

  }
}


