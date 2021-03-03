import java.lang.reflect.Array;
import java.util.*;

public class Main {
<<<<<<< HEAD

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

=======
  static Integer[] list = new Integer[]{1, 0, 2, 1, 3, 5, 4, 8};
  static List<Integer> list2 = new ArrayList<>(Arrays.asList(7,2,4,6,3,8));
  static List<String> str = new ArrayList<>(Arrays.asList("api", "orange", "edem", "cop", "apo", "view"));

  public static void main(String[] args) {
    System.out.println(srch.search(9, list));
    System.out.println(rvs.reverse("Work is good"));
    System.out.println(max.maximum(list));
    System.out.println(avr.average(list2));
    System.out.println(srchStr.search(str));
>>>>>>> parent of 15ec3a9... clean main
  }

  //Задание 2
  static MySearch srch = (Integer x, Integer[] y) -> {
    for (int a : y) {
      if (a == x) {
        return Arrays.asList(y).indexOf(a);
      }
    }
    return -1;
  };

  //Задание 3
  static MyRevers rvs = (String x) -> new StringBuilder(x).reverse().toString();

  //Задание 4
    static MyMax max = (Integer[] x) -> {
    int z = 0;
    for (int a : x) {
      if (a>z){
        z=a;
      }
    }return z;
  };

    //Задание 5
    static MyAverage avr = (List<Integer> x)-> {
      int aver = 0;
      for (int a : x){
        aver+=a;
      }
      aver/= x.size() - 1;
      return Double.valueOf(aver);
  };


  //Задание 6
    static MySearchString srchStr = (List<String> x)->{
      List<String> str = new ArrayList<>();
      for (String a:x){
        char [] c = a.toCharArray();
        if (c[0]=='a'){
          if (c.length == 3){
            String z = new String(c);
            str.add(z);
          }
        }
      }
      return str;
  };
}


