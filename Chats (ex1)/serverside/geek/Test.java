package serverside.geek;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        int [] a = new int[]{1,5,8,1,8,4,10,3,7};
        arr(a);
    }
    public static int [] arr (int [] a) {
        List<Integer> list = new ArrayList<>();
            for (int s : a){
                list.add(s);
            }
                if (list.stream().noneMatch(f->f == (4))){
                    throw new RuntimeException();
                }
        int i = list.lastIndexOf(4) + 1;
        int [] arrReturn = new int[a.length - i];
        list = list.subList(i,list.size());
            for (int j=0; j<list.size(); j++){
                arrReturn[j] = list.get(j);
            }
        return arrReturn;
    }
}