import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day1 {
    public static void main(String[] args) {
        List<Integer> input = ReadInput.asInteger("day1input.txt");
        Day1 d = new Day1();
        System.out.println(d.computePart1(input, 2020));
        System.out.println(d.computePart2(input, 2020));
    }

    public int computePart1(List<Integer> data, int n) {
        Set<Integer> saved = new HashSet<>();
        for (int i = 0; i < data.size(); i++) {
            saved.add(data.get(i));
        }
        for (int i = 0; i < data.size(); i++) {
            int diff = n - data.get(i);
            if (saved.contains(diff)) {
                return diff * data.get(i);
            }
        }
        return -1;
    }

    public int computePart2(List<Integer> data, int n) {
        for (Integer int1 : data) {
            if (int1 > n) continue;
            for (Integer int2 : data) {
                int int12 = int1 + int2;
                if (int12 > n) continue;
                for (Integer int3 : data) {
                    if ((int12 + int3) == n) {
                        return int1 * int2 * int3;
                    }
                }
            }
        }
        return -1;
    }
}