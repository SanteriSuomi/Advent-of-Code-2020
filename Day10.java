import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day10 {
    public static void main(String[] args) {
        List<Integer> input = ReadInput.asInteger("day10input.txt");
        Day10 d = new Day10();
        System.out.println(d.compute1(input));
        // System.out.println(d.compute2Rec(input));
        System.out.println(d.compute2Ite(input));
    }

    public int compute1(List<Integer> input) {
        Collections.sort(input);
        int numberOf1Diff = 0;
        int numberOf3Diff = 0;
        int currDiff = 0;
        int currNumber = 0;
        int i = 0;
        while (i <= input.size() - 1) {
            if (currNumber == 16) {
                System.out.println();
            }
            for (int j = 1; j <= 3; j++) {
                int search = search(input, currNumber + j, i, input.size());
                if (search != -1) {
                    currDiff = search - currNumber;
                    currNumber += currDiff;
                    break;
                }
            }
            if (currDiff == 1) numberOf1Diff++;
            if (currDiff == 3) numberOf3Diff++;
            i++;
        }
        return numberOf1Diff * (numberOf3Diff + 1);
    }

    private int search(List<Integer> l, int n, int low, int high) {
        if (low >= high) return -1;
        int mid = (low + high) / 2;
        if (l.get(mid) == n) return l.get(mid);
        if (l.get(mid) > n) return search(l, n, low, mid);
        else return search(l, n, mid + 1, high);
    }

    /**
     * One recursive solution, but does not work for very large inputs.
     */
    public int compute2Rec(List<Integer> input) {
        Collections.sort(input);
        Set<Integer> set = new HashSet<>();
        count(input, set, -1, 0, "0");
        return set.size();
    }

    private void count(List<Integer> input, Set<Integer> set, int index, int number, String s) {
        if (number == input.get(input.size() - 1)) {
            s += Integer.toString(number + 3);
            set.add(s.hashCode());
            return;
        }
        int n;
        int diff;
        index++;
        if (index < input.size()) {
            diff = input.get(index) - number;
            if (diff <= 3) {
                n = number + diff;
                count(input, set, index, n, s + Integer.toString(n));
            }
        }
        index++;
        if (index < input.size()) {
            diff = input.get(index) - number;
            if (diff <= 3) {
                n = number + diff;
                count(input, set, index, n, s + Integer.toString(n));
            }
        }
        index++;
        if (index < input.size()) {
            diff = input.get(index) - number;
            if (diff <= 3) {
                n = number + diff;
                count(input, set, index, n, s + Integer.toString(n));
            }
        }
    }

    /**
     * Couldn't get it to work, but curiously enough it produces a correct output for someone else
     */
    public long compute2Ite(List<Integer> input) {
        input.add(input.get(input.size() - 1) + 3);
        Collections.sort(input);
        Map<Integer, Long> map = new HashMap<>();
        map.put(0, 1L);
        int i = 0;
        int j = 0;
        for (i = 0; i < input.size(); i++) {
            for (j = i + 1; j < input.size(); j++) {
                if (input.get(j) > (input.get(i) + 3)) break;
                if (map.get(j) != null) {
                    map.put(j, map.get(j) + map.get(i));
                } else {
                    map.put(j, 0 + map.get(i));
                }
                if (j == 99) {
                    System.out.println();
                }
            }
        }
        return map.get(input.size() - 1);
    }
}