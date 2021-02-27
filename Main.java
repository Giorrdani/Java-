import java.lang.reflect.Array;
import java.util.*;

public class Main {
  static Integer[] list = new Integer[]{1, 0, 2, 1, 3, 5, 4, 8};
  static List<Integer> list2 = new ArrayList<>(Arrays.asList(7,2,4,6,3,8));
  static List<String> str = new ArrayList<>(Arrays.asList("api", "orange", "edem", "cop", "apo", "view"));

  public static void main(String[] args) {
    System.out.println(srch.search(9, list));
    System.out.println(rvs.reverse("Work is good"));
    System.out.println(max.maximum(list));
    System.out.println(avr.average(list2));
    System.out.println(srchStr.search(str));
  }
  //Задание 1 сделано 
 
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

