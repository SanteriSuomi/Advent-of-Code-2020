import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Day15 {
    public static void main(String[] args) {
        int[] input = new int[] { 0, 5, 4, 1, 10, 14, 7 };
        Day15 d = new Day15();
        System.out.println(d.compute(input, 2020)); // Part 1
        System.out.println(d.compute(input, 30000000)); // Part 2
    }

    public int compute(int[] input, int n) {
        Map<Integer, List<Integer>> memory = new HashMap<>();
        int last = 0;
        int turn = 1;
        while (turn <= n) {
            if (turn <= input.length) {
                last = input[turn - 1];
                putNumber(memory, input[turn - 1], turn);
            } else {
                List<Integer> turns = memory.get(last);
                if (turns == null) continue;
                if (turns.size() == 1) {
                    last = 0;
                    putNumber(memory, 0, turn);
                } else if (!turns.isEmpty()) {
                    last = (lastTurn(memory, last, 1) - lastTurn(memory, last, 2));
                    putNumber(memory, last, turn);
                }
            }
            turn++;
        }
        return last;
    }

    private void putNumber(Map<Integer, List<Integer>> memory, int number, int turn) {
        List<Integer> list = memory.get(number);
        if (list != null) {
            list.add(turn);
        } else {
            list = new ArrayList<>();
            list.add(turn);
            memory.put(number, list);
        }
    }

    private int lastTurn(Map<Integer, List<Integer>> memory, int number, int amount) {
        List<Integer> list = memory.get(number);
        return list.get(list.size() - amount);
    }
}