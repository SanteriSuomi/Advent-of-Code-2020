import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day2 {
    public static void main(String[] args) {
        List<String> input = ReadInput.asString("day2input.txt");
        Day2 d = new Day2();
        System.out.println(d.computePart1(input));
        System.out.println(d.computePart2(input));
    }

    public int computePart1(List<String> input) {
        Map<Character, Integer> map = new HashMap<>();
        int total = 0;
        for (String s : input) {
            Token t = new Token(s);
            for (int i = 0; i < t.password.length(); i++) {
                add(t.password.charAt(i), map);
            }
            int amount = map.get(t.letter) != null ? map.get(t.letter) : -1;
            if (amount >= t.min && amount <= t.max) {
                total++;
            }
            map.clear();
        }
        return total;
    }

    private void add(char c, Map<Character, Integer> m) {
        if (m.containsKey(c)) {
            m.put(c, m.get(c) + 1);
        } else {
            m.put(c, 1);
        }
    }

    public int computePart2(List<String> input) {
        int total = 0;
        for (String s : input) {
            Token t = new Token(s);
            boolean index1HasChar = t.password.charAt(t.min - 1) == t.letter;
            boolean index2HasChar = t.password.charAt(t.max - 1) == t.letter;
            if ((!index1HasChar && index2HasChar) || (index1HasChar && !index2HasChar)) {
                total++;
            }
        }
        return total;
    }
    
    class Token {
        public int min;
        public int max;
        public char letter;
        public String password;

        public Token(String nonParsed) {
            String[] split = nonParsed.split(" ");
            String[] minAndMaxSplit = split[0].split("-");
            min = Integer.parseInt(minAndMaxSplit[0]);
            max = Integer.parseInt(minAndMaxSplit[1]);
            letter = split[1].charAt(0);
            password = split[2];
        }
    }
}