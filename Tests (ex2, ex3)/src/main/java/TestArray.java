import java.util.ArrayList;
import java.util.List;

public class TestArray {
    public static void main(String[] args) {
    }
    public int [] arr (int [] a) {
        if (!(a == null)) {
            List<Integer> list = new ArrayList<>();
            for (int s : a) {
                list.add(s);
            }
            if (list.stream().noneMatch(f -> f == (4)))
                throw new RuntimeException();
            int i = list.lastIndexOf(4) + 1;
            int[] arrReturn = new int[a.length - i];
            list = list.subList(i, list.size());
            for (int j = 0; j < list.size(); j++) {
                arrReturn[j] = list.get(j);
            }
            return arrReturn;
        }
        return null;
    }
    public boolean arr2 (int [] a){
        boolean one = false;
        boolean four = false;

        for (int z: a) {
            if (z == 1){
                one = true;
            }if (z == 4){
                four = true;
            }
            if (one && four){
                return true;
            }
        }
        return false;
    }

}