import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day9 {
    public static void main(String[] args) {
        List<String> input = ReadInput.asString("day9input.txt");
        Day9 d = new Day9();
        Set<Long> set = new HashSet<>();
        List<Long> values = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            long v = Long.parseLong(input.get(i));
            if (i < 25) {
                set.add(v);
            }
            values.add(v);
        }
        long n = d.compute1(values, set);
        System.out.println(n);
        System.out.println(d.compute2(values, n));
    }

    public long compute1(List<Long> values, Set<Long> set) {
        int left = 0;
        int right = 25;
        for (; right < values.size(); left++, right++) {
            long val = values.get(right);
            boolean hasSum = false;
            for (int j = left; j < right - 1; j++) {
                long diff = val - values.get(j);
                if (set.contains(diff)) {
                    hasSum = true;
                    break;
                }
            }
            if (!hasSum) {
                return val;
            }
            set.remove(values.get(left));
            set.add(values.get(right));
        }
        return 0;
    }

    // TODO - improve, from O(n^2) to O(n * log n) using binary search tree?
    public long compute2(List<Long> values, long sum) {
        long smallest;
        long biggest;
        for (int i = 0; i < values.size(); i++) {
            smallest = values.get(i);
            biggest = values.get(i);
            long currSum = values.get(i);
            for (int j = i + 1; j < values.size(); j++) {
                currSum += values.get(j);
                if (values.get(j) < smallest) {
                    smallest = values.get(j);
                } else if (values.get(j) > biggest) {
                    biggest = values.get(j);
                }
                if (currSum == sum) {
                    return smallest + biggest;
                } else if (currSum > sum) {
                    break;
                }
            }
        }
        return 0;
    }
}