package datastructure.search;

/**
 * @author huang
 * @version 1.0
 * @date 2019/02/14 14:45
 **/

public class TestSearch {
    public static void main(String[] args) {
        int[] numbers = new int[]{1, 2, 32, 44, 44, 44, 55, 456, 900};
        int result = BSearch.bSearch4(numbers, numbers.length, 900);
        System.out.println(result);
    }
}
