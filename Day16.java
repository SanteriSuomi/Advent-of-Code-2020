import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class Day16 {
    public static void main(String[] args) {
        List<String> input = ReadInput.asString("day16input.txt");
        Day16 d = new Day16();
        System.out.println(d.compute1(input));
    }

    public int compute1(List<String> input) {
        Set<Integer> set = new HashSet<>();
        StringBuilder str = new StringBuilder();
        Pattern inputBeginPattern = Pattern.compile("nearby tickets:");
        boolean inputBegin = false;
        int ans = 0;
        for (String line : input) {
            if (!inputBegin) {
                inputBegin = inputBeginPattern.matcher(line).matches();
                if (inputBegin) {
                    continue;
                }
            }
            if (inputBegin) {
                ans = checkInput(set, str, ans, line);
            } else {
                parse(set, str, line);
            }
        }
        return ans;
    }

    private int checkInput(Set<Integer> set, StringBuilder str, int ans, String line) {
        for (int i = 0; i <= line.length(); i++) {
            if (i == line.length() || line.charAt(i) == ',') {
                int res = Integer.parseInt(str.toString());
                if (!set.contains(res)) {
                    ans += res;
                }
                str.setLength(0);
            } else {
                str.append(line.charAt(i));
            }
        }
        str.setLength(0);
        return ans;
    }

    private void parse(Set<Integer> set, StringBuilder str, String line) {
        int start = 0;
        int end = 0;
        int i = 0;
        for (; i < line.length(); i++) {
            if (Character.isDigit(line.charAt(i))) {
                str.append(line.charAt(i));
            } else if (line.charAt(i) == '-') {
                start = Integer.parseInt(str.toString());
                str.setLength(0);
                i++;
                for (; i < line.length() && Character.isDigit(line.charAt(i)); i++) {
                    str.append(line.charAt(i));
                }
                end = Integer.parseInt(str.toString());
                str.setLength(0);
                for (int j = start; j <= end; j++) {
                    set.add(j);
                }
            } else if (line.charAt(i) == ',') {
                set.add(Integer.parseInt(str.toString()));
                str.setLength(0);
            }
        }
        if (!str.isEmpty()) {
            set.add(Integer.parseInt(str.toString()));
            str.setLength(0);
        }
    }
}